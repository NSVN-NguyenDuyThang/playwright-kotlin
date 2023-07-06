package shou.page.web.kcp003

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage

class Kcp003Component(override var page: Page, private val wrapperContainer: String) : BasePage() {
    private var frame : FrameLocator? = null
    private var wrapperLocator: Locator? = null
        get() = (frame?.locator(wrapperContainer)) ?: page.locator(wrapperContainer)

    constructor(page: Page, wrapperContainer: String, frame: FrameLocator) : this(page, wrapperContainer) {
        this.frame = frame
    }

    /**
     * Tìm các position trong [pstList] đã tồn tại
     * @param pstList
     * @return danh sách position code đã tồn tại
     */
    fun getExistedPosition(pstList: List<String>): List<String> {
        return wrapperLocator?.locator(POSITION_ITEM_LIST)!!.all()
            .map { it.innerText() }
            .filter { pstList.contains(it) }
    }

    fun selectPosition(code: String) = clickToElement(wrapperLocator?.locator(String.format(POSITION_ITEM, code)))

    companion object {
        private const val POSITION_ITEM = "//tr[@data-id]//td[contains(@aria-describedby, 'code') and text()='%s']"
        private const val POSITION_ITEM_LIST = "//tr[@data-id]//td[contains(@aria-describedby, 'code')]"
    }
}