package shou.page.web.cas009

import com.microsoft.playwright.Locator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.checkbox.Checkbox
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.radiobutton.RadioButton
import shou.page.web.cas005.Cas005Page
import java.util.function.Consumer

class Cas009Page() : BasePage() {
    fun clickButtonNew() {
        clickToButton(TEXTRESORCE_ID_SCREEN)
    }

    fun checkCodeGridIsExist(code: String): Boolean? {
        return page.locator(String.format(XPATH_SELECT_ROW_GRID, code)).all().size > 0
    }

    fun inputCode(code: String?) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "roleCode"), code, getTextResource(TEXTRESORCE_ID_ROLE_CODE))
    }

    fun inputName(name: String?) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "roleName"), name, getTextResource(TEXTRESORCE_ID_ROLE_NAME))
    }

    fun selectCheckBoxInTable(input: List<AdvancedSetting>) {
        input.forEach { it ->
            val checkbox = Checkbox(page, String.format(CHECKBOX, it.settingName))
            val item = it
            item.used?.let {
                when(it) {
                    true -> addStep(item.settingName + "を選択", Runnable { checkbox.check("") })
                    false -> addStep(item.settingName + "を選択しない", Runnable { checkbox.uncheck("") })
                }
            }
        }
    }

    @Step("「コード」で - {0}を選択")
    fun selectRowEdit(code: String) {
        clickToElement(String.format(XPATH_SELECT_ROW_GRID, code))
        takeScreenshot("「コード」で - %sを選択$code")
    }

    fun clickButtonSave() {
        clickToButton(TEXTRESORCE_ID_BUTTON_SAVE)
    }

    fun getMessageResult(): String {
        return getAndCloseMsgInfo()
    }

    fun clickButtonDelete() {
        clickToButton(TEXTRESORCE_ID_BUTTON_DELETE)
        messageConfirmYes(TEXTRESORCE_ID_BUTTON_CONFIRM_YES)
    }

    @Step("タップ「一般」")
    fun clickTabGeneral() {
        RadioButton(page, Cas005Page.ROLE_TYPE).check(getTextResource(TEXTRESORCE_ID_TAB_GENERAL))
    }

    @Step("「社員参照範囲」で - {0}を選択")
    fun selectComboBox(name: String?) {
        name?.let {
            IgCombo(page, COMBO_BOX).selectByTitle(name)
        }
    }

    companion object {
        const val TEXTRESORCE_ID_SCREEN = "CAS009_2"
        const val TEXTRESORCE_ID_ROLE_CODE = "CAS009_6"
        const val TEXTRESORCE_ID_ROLE_NAME = "CAS009_7"
        const val CHECKBOX = "//tr[./td[@aria-describedby='permision_grid_functionName' and text()='%s']]/td/div[@class='ntsControl ntsCheckBox']"
        const val XPATH_SELECT_ROW_GRID = "xpath=//div[@id='ccg025-list_container']//td[contains(text(),'%s')]"
        const val TEXTRESORCE_ID_BUTTON_SAVE = "CAS009_3"
        const val TEXTRESORCE_ID_BUTTON_DELETE = "CAS009_4"
        const val TEXTRESORCE_ID_BUTTON_CONFIRM_YES = "Msg_18"
        const val TEXTRESORCE_ID_TAB_GENERAL = "Enum_RoleAtr_General"
        const val COMBO_BOX = "//div[@class='ui-igcombo-wrapper ntsControl']"
    }
}