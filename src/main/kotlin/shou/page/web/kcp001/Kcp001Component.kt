package shou.page.web.kcp001

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import io.qameta.allure.Step
import shou.common.web.BasePage

class Kcp001Component(override var page: Page, private val wrapperContainer: String) : BasePage() {
    private var frame: FrameLocator? = null
    private var wrapperLocator: Locator? = null
        get() = frame?.locator(wrapperContainer) ?: page.locator(wrapperContainer)

    constructor(page: Page, wrapperContainer: String, frameLocator: FrameLocator) : this(page, wrapperContainer) {
        this.frame = frameLocator
    }

    fun isEmploymentExisted(empCode: String): Boolean {
        return wrapperLocator!!.locator(String.format(EMPLOYMENT_ITEM, empCode)).count() > 0
    }

    @Step("「コード＝{0}」の雇用情報を選択する")
    fun selectEmployment(code: String) {
        clickToElement(wrapperLocator!!.locator(String.format(EMPLOYMENT_ITEM, code)))
        takeScreenshot("「コード＝$code」の雇用情報を選択する")
    }

    /**
     * Tìm các employment trong [empList] đã tồn tại
     * @param empList
     * @return danh sách code đã tồn tại
     */
    fun getExistedEmploymentCodeList(empList: List<String>): List<String> {
        return wrapperLocator!!.locator(EMPLOYMENT_ITEM_LIST).all().map { it.getAttribute("data-id") }.filter { empList.contains(it) }
    }

    companion object {
        private const val EMPLOYMENT_ITEM = "//tr[@data-id='%s']"
        private const val EMPLOYMENT_ITEM_LIST = "//tr[@data-id]"
    }

}