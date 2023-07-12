package shou.page.web.cas011

import com.microsoft.playwright.Locator
import io.qameta.allure.Step
import shou.browser.PageFactory
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.iggrid.IgGrid
import shou.page.web.cas005.ItemValue
import java.util.stream.Collectors


class Cas011Page() : BasePage() {

    fun clickButtonNew() {
        clickToButton(TEXTRESORCE_BTN_NEW)
    }

    fun inputCode(code: ItemValue) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "inpRoleSetCd"), code.value, getTextResource(code.titleTextRsId))
    }

    fun inputName(name: ItemValue?) {
        name?.let { fillToElement(String.format(CommonUI.INPUT_BY_ID, "inpRoleSetName"), it.value, getTextResource(it.titleTextRsId)) }
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


    fun selectComboBoxEmploymentRole(code: String) {
        selectComboBox(TEXTRESORCE_COMBOBOX_EMPLOYEE_ROLE, code)
    }

    fun selectComboBoxPersonInfRole(code: String) {
        selectComboBox(TEXTRESORCE_COMBOBOX_PERSON_ROLE, code)
    }

    @Step("「{0}」で - {1}を選択")
    private fun selectComboBox(name: String, code: String) {
        IgCombo(page, String.format(COMBOBOX, name)).selectByCode(code)
    }

    @Step("{0}")
    fun selectGrid1(code: List<ItemValue>) {
        IgGrid(page, GRID_SWAP1).checkToRowsByCellName(getTextResource(TEXTRESORCE_GRID_CODE),code.map { it.value!! })
        takeScreenshot(code.toString())
    }

    fun clickButtonMoveForward() {
        clickToElement("Click to ▶", BTN_FORWARD)
    }

    fun clickButtonMoveBack() {
        clickToElement("Clickt to ◀", BTN_BACK)
    }

    @Step("チェックコード「{0}」が存在する")
    fun isExistGridRole(code: String): Boolean {
        val existsRow: Boolean? = IgGrid(page, GRID_ROLE).isExistsRow(getTextResource(TEXTRESORCE_GRID_CODE), code)
        when(existsRow!!) {
            true -> takeScreenshot(String.format("コード「%s」は存在します」", code))
            false -> takeScreenshot(String.format("コード「%s」は存在しません", code))
        }
        return existsRow
    }

    @Step("{0}")
    fun selectRowByCodeGridRole(code: ItemValue) {
        IgGrid(page, GRID_ROLE).selectRowByCellName(getTextResource(TEXTRESORCE_GRID_CODE), code.value!!)
    }

    companion object {
        const val TEXTRESORCE_BTN_NEW = "CAS011_4"
        const val TEXTRESORCE_ID_BUTTON_SAVE = "CAS011_5"
        const val TEXTRESORCE_ID_BUTTON_DELETE = "CAS011_6"
        const val TEXTRESORCE_GRID_CODE = "CAS011_9"
        const val TEXTRESORCE_COMBOBOX_EMPLOYEE_ROLE = "CAS011_14"
        const val TEXTRESORCE_COMBOBOX_PERSON_ROLE = "CAS011_18"
        const val TEXTRESORCE_ID_BUTTON_CONFIRM_YES = "Msg_18"
        const val GRID_ROLE = "//div[@id='role_set_grid_container']"
        const val GRID_SWAP1 = "//div[@id='swap-list-grid1_container']"
        const val BTN_FORWARD = "//button[@class='move-button move-forward ntsSwap_Component']"
        const val BTN_BACK = "//button[@class='move-button move-back ntsSwap_Component']"
        const val COMBOBOX = "//span[contains(text(),'%s')]/parent::div/parent::td/following-sibling::td//div[contains(@class, 'ui-igcombo-wrapper ntsControl')]"
    }
}