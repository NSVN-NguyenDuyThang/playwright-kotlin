package shou.page.web.cas005

import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.iggrid.IgGrid
import shou.common.web.element.radiobutton.RadioButton
import shou.common.web.element.switchbox.SwitchBox
import shou.utils.model.ItemValue

class Cas005Page() : BasePage() {

    fun clickButtonNew() {
        clickToButton(TEXTRESORCE_BTN_NEW)
    }

    fun checkCodeGridIsExist(code: String): Boolean? {
        return IgGrid(page, GRID).isExistsRow(getTextResource(TEXTRESORCE_GRID_CODE), code)
    }

    fun inputCode(code: ItemValue) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "roleCode"), getTextResource(code.value))
    }

    fun inputName(name: ItemValue) {
        name.value?.let {
            fillToElement(String.format(CommonUI.INPUT_BY_ID, "roleName"), getTextResource(name.value))
        }
    }

    @Step("{0}")
    fun selectComboMenuSetting(optionCode: ItemValue) {
        IgCombo(page, String.format(XPATH_MENU_SETTING, getTextResource(optionCode.titleTextRsId))).selectByCode(optionCode.value!!)
        takeScreenshot(optionCode.toString())
    }

    @Step("{0}")
    fun selectRowEdit(code: ItemValue) {
        IgGrid(page, GRID).selectRowByCellName(TEXTRESORCE_GRID_CODE, code.value!!)
        takeScreenshot(code.toString())
    }


    fun clickButtonSave() {
        clickToButton(TEXTRESORCE_ID_BUTTON_SAVE)
    }

    fun getMessageResult(): String {
        return getAndCloseMsgInfo()
    }

    fun clickButtonDelete(){
        clickToButton(TEXTRESORCE_ID_BUTTON_DELETE)
        messageConfirmYes(TEXTRESORCE_ID_BUTTON_CONFIRM_YES)
    }

    fun clickTab(textResourceId: String) {
        val textResource = getTextResource(textResourceId)
        addStep("Click to $textResource tab", Runnable {
            RadioButton(page, ROLE_TYPE).check(textResource)
        })
    }

    @Step("{0}")
    fun selectComboEmployeeRefRange(name: ItemValue) {
        IgCombo(page, String.format(XPATH_COMBO_EMPLOYEEREFRANGE, getTextResource(name.titleTextRsId))).selectByTitle(name.value!!)
        takeScreenshot(name.toString())
    }

    @Step("{0}")
    fun selectApprovalAuthority(name: ItemValue) {
        SwitchBox(page, String.format(XPATH_APPROVAL_AUTHORITY, getTextResource(name.titleTextRsId))).select(name.value!!)
        takeScreenshot(name.toString())
    }

    companion object {
        const val TEXTRESORCE_BTN_NEW = "CAS005_2"
        const val GRID = "//div[@id='ccg025-list_container']"
        const val TEXTRESORCE_GRID_CODE = "CCG025_6"
        const val XPATH_MENU_SETTING = "//span[text()='%s']/parent::div/following-sibling::div"
        const val TEXTRESORCE_ID_BUTTON_SAVE = "CAS005_3"
        const val TEXTRESORCE_ID_BUTTON_DELETE = "CAS005_4"
        const val TEXTRESORCE_ID_BUTTON_CONFIRM_YES = "Msg_18"
        const val ROLE_TYPE = "#ccg025-switch"
        const val XPATH_COMBO_EMPLOYEEREFRANGE = "//span[text()='%s']/parent::div/following-sibling::div"
        const val XPATH_APPROVAL_AUTHORITY = "//span[text()='%s']/parent::div/following-sibling::div"
    }

}