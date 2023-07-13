package shou.page.web.cas013

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.iggrid.IgGrid
import shou.utils.model.ItemValue


class Cas013Page() : BasePage() {

    private val personalChoiceDlg: FrameLocator
        get() = getFrame(FRAME_PERSONAL)
    fun clickButtonNew() {
        clickToButton(TEXTRESORCE_BTN_NEW)
    }

    fun selectInformationOnPersonalChoiceDlg(data : DataRegister) {
        inputEmployee(data.employeeId!!)
        clickButtonSearch()
        selectRowInPersonalGrid(data.employeeId!!.value!!)
        clickButtonDecision()
    }

    private fun inputEmployee(value: ItemValue) {
        fillToElement(personalChoiceDlg.locator(String.format(XPATH_INPUT_EMPLOYEE, getTextResource(TEXTRESORCE_BTN_SEARCH))), value.value, getTextResource(TEXTRESORCE_BTN_SEARCH))
    }

    private fun clickButtonSearch() {
        clickToButton(personalChoiceDlg, TEXTRESORCE_BTN_SEARCH)
    }

    private fun clickButtonDecision() {
        clickToButton(personalChoiceDlg, TEXTRESORCE_BTN_DECISION)
    }

    @Step("{0}")
    fun selectDropdownSystem(value: ItemValue) {
        IgCombo(page, String.format(XPATH_COMBO_SYSTEM, getTextResource(value.titleTextRsId))).selectByTitle(value.value!!)
        takeScreenshot(value.toString())
    }

    @Step("{0}")
    fun selectDropdownRoll(value: ItemValue) {
        IgCombo(page, String.format(XPATH_COMBO_ROLL, getTextResource(value.titleTextRsId))).selectByTitle(value.value!!)
        takeScreenshot(value.toString())
    }

    @Step("検索済のリストで社員 {0} を選択")
    fun selectRowInPersonalGrid(code: String) {
        IgGrid(page, XPATH_GRID_PERSON, personalChoiceDlg).selectRowByCellName("コード", code)
        takeScreenshot(String.format("検索済のリストで社員 %s を選択", code))
    }

    fun inputPeriod(start: ItemValue, end: ItemValue) {
        fillToElement(XPATH_INPUt_START, start.value!!, getTextResource(start.titleTextRsId))
        fillToElement(XPATH_INPUt_END, end.value!!, getTextResource(start.titleTextRsId))
    }

    fun checkCodeGridRoleIsExist(code: String): Boolean {
        return page.locator(String.format(XPATH_SELECT_GRID_ROLE, code)).all().isNotEmpty()
    }

    @Step("社員 {0} を選択")
    fun selectRowGridRole(code: String?) {
        clickToElement(String.format(XPATH_SELECT_GRID_ROLE, code))
        takeScreenshot("社員 $code を選択")
    }


    fun clickButtonSave() {
        clickToButton(TEXTRESORCE_ID_BUTTON_SAVE)
    }

    fun getMessageResult(): String? {
        return getAndCloseMsgInfo()
    }

    fun clickButtonDelete() {
        clickToButton(TEXTRESORCE_ID_BUTTON_DELETE)
        messageConfirmYes(TEXTRESORCE_ID_BUTTON_CONFIRM_YES)
    }

    companion object {
        const val TEXTRESORCE_BTN_NEW = "CAS013_3"
        const val FRAME_PERSONAL = "個人選択"
        const val XPATH_INPUT_EMPLOYEE = "xpath=//button[text()='%s']/preceding-sibling::div//input"
        const val TEXTRESORCE_BTN_SEARCH = "CAS013_39"
        const val TEXTRESORCE_BTN_DECISION = "CAS013_24"
        const val XPATH_COMBO_SYSTEM =
            "xpath=//span[text() = '%s']/parent::div//following-sibling::div[contains(@class,'ui-igcombo-wrapper')]"
        const val XPATH_COMBO_ROLL =
            "xpath=//span[text() = '%s']/parent::div//following-sibling::div[contains(@class,'ui-igcombo-wrapper')]"
        const val XPATH_GRID_PERSON = "//div[@id='kcp005']//div[contains(@class,'ui-iggrid nts-gridlist')]"
        const val XPATH_INPUt_START = "xpath=//div[@id='daterangepicker']//div[contains(@class, 'ntsStartDate')]//input"
        const val XPATH_INPUt_END = "xpath=//div[@id='daterangepicker']//div[contains(@class, 'ntsEndDate')]//input"
        const val XPATH_SELECT_GRID_ROLE = "//td[@role='gridcell' and contains(text(),'%s')]"
        const val TEXTRESORCE_ID_BUTTON_SAVE = "CAS013_4"
        const val TEXTRESORCE_ID_BUTTON_DELETE = "CAS013_5"
        const val TEXTRESORCE_ID_BUTTON_CONFIRM_YES = "Msg_18"
    }
}