package shou.page.web.cas001

import com.microsoft.playwright.Locator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.iggrid.IgGrid
import shou.common.web.element.radiobutton.RadioButton
import shou.utils.model.ItemValue

class Cas001Page() : BasePage() {
    fun clickButtonSave() {
        clickToButton("CAS001_3")
    }

    @Step("{0}")
    fun selectRowGridRole(item: ItemValue) {
        IgGrid(page, ROLE_GRID).selectRowByCellName("コード", item.value!!)
        takeScreenshot(item.toString())
    }

    @Step("{0}")
    fun selectRowGridCategory(item: ItemValue) {
        IgGrid(page, CATEGORY_GRID).selectRowByCellName("カテゴリ名", item.value!!)
        takeScreenshot(item.toString())
    }

    @Step("{0}")
    fun selectRadioPermission(item: ItemValue) {
        RadioButton(page, String.format(RADIO_PERMISION, getTextResource(item.titleTextRsId))).check(item.value!!)
        takeScreenshot(item.toString())
    }

    @Step("項目一括選択で全てチェックボックスにチェックを入れる")
    fun checkAllGridUsageAuthoritySetting(checkAll: Boolean) {
        val checkBoxAll: Locator = page.locator(CHECKALL_GRID)
        if (checkAll && !checkBoxAll.isChecked) {
            checkBoxAll.click()
        }
        takeScreenshot("項目一括選択で全てチェックボックスにチェックを入れる")
    }

    @Step("{0}")
    fun selectRadioGridUsageAuthoritySetting(item: ItemValue) {
        val radioElement = RadioButton(page, String.format(RADIO_AUTHORITY_SETTING, getTextResource(item.titleTextRsId)))
        radioElement.uncheck(item.value!!)
        radioElement.check(item.value!!)
        takeScreenshot(item.toString())
    }

    fun selectTab(textResourceId: String) {
        val textResource = getTextResource(textResourceId)
        clickToElement("タップ「$textResource」", String.format(SELECT_TAB, textResource))
    }

    fun getMessageResult(): String {
        val result: String = getAndCloseMsgInfo()
        page.waitForTimeout(2000.9)
        return result
    }

    val ROLE_GRID = "#ccg025-list_container"
    val CATEGORY_GRID = "#A2_008_container"
    val RADIO_PERMISION = "xpath=//span[normalize-space(text())='%s']/following-sibling::div"
    val CHECKALL_GRID = "//th[@id='item_role_table_body_isChecked']//label[@class='ntsCheckBox']//span"
    val RADIO_AUTHORITY_SETTING = "xpath=//span[text() = '%s']//div[contains(@class, 'switchbox-wrappers')]"
    val SELECT_TAB = "xpath=//span[text()='%s']"
}