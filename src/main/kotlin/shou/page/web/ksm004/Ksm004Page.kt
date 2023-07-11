package shou.page.web.ksm004

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.datepicker.DatePicker
import shou.common.web.element.iggrid.IgGrid
import shou.common.web.element.radiobutton.RadioButton
import shou.common.web.element.treegrid.TreeGrid

class Ksm004Page() : BasePage() {

    private val ksm004dDlg: FrameLocator
        get() = getFrame(getTextResource(TEXT_RESOURCES_BUTTON_KSM004D))
    fun getMsgDisplayed(): String {
        return getAndCloseMsgInfo()
    }

    //tab company
    fun selectTabCompany() {
        clickToElement("Click company tab", String.format(XPATH_TAB, getTextResource(TEXT_RESOURCES_TAB_COMPANY)))
    }

    @Step("年月 で「{0}」を選択")
    fun inputDateTabCompany(date: String) {
        DatePicker(page, XPATH_INPUT_DATE_PICKER_TAB_COMPANY).input(date)
        pressKeyToElement(page.locator(XPATH_CLICK_OUT_SIDE_ENTER_TAB_COMPANY), "Enter")
        takeScreenshot("年月 で「$date」を選択")
    }

    @Step("Open KSM004D Dialog")
    fun clickOpenDialogKSM004D() {
        clickToElement(String.format(XPATH_ID_OPEN_DIALOG_KSM004D_TAB_COMPANY, getTextResource(TEXT_RESOURCES_BUTTON_KSM004D)))
    }

    @Step("テキスト {0} と {1} を 設定期間")
    fun inputDataStartDateAndEndDateDialogKSM004D(startDate: String, endDate: String) {
        DatePicker(page, XPATH_INPUT_START_DATE_DIALOG_KSM004D, ksm004dDlg).input(startDate)
        DatePicker(page, XPATH_INPUT_END_DATE_DIALOG_KSM004D, ksm004dDlg).input(endDate)
        pressKeyToElement(ksm004dDlg.locator(XPATH_CLICK_OUT_SIDE_ENTER), "Enter")
        takeScreenshot("テキスト $startDate  と $endDate を 設定期間")
    }

    @Step("作成位置を選択")
    fun selectRadioKSM004D(setting: CollectiveSetting) {
        val radioElementMonday = RadioButton(page,String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_38")), ksm004dDlg)
        val radioElementTuesday = RadioButton(page, String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_39")), ksm004dDlg)
        val radioElementWednesday = RadioButton(page, String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_40")), ksm004dDlg)
        val radioElementThursday = RadioButton(page, String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_41")), ksm004dDlg)
        val radioElementFriday = RadioButton(page, String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_42")), ksm004dDlg)
        val radioElementSaturday = RadioButton(page, String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_43")), ksm004dDlg)
        val radioElementSunday = RadioButton(page, String.format(XPATH_RADIO_BUTTON_DIALOG_KSM004D, getTextResource("KSM004_44")), ksm004dDlg)
        radioElementMonday.check(setting.monday!!.value!!)
        radioElementTuesday.check(setting.tuesday!!.value!!)
        radioElementWednesday.check(setting.wednesday!!.value!!)
        radioElementThursday.check(setting.thursday!!.value!!)
        radioElementFriday.check(setting.friday!!.value!!)
        radioElementSaturday.check(setting.saturday!!.value!!)
        radioElementSunday.check(setting.sunday!!.value!!)
        takeScreenshot("作成位置を選択")
    }

    fun clickButtonSubmitDialogKDL004D() {
        clickToButton(ksm004dDlg, "KSM004_34")
        closeMsgInfo("Msg_15")
    }

    fun clickButtonSaveCompany(): String {
        clickToElement("Click to register btn", String.format(XPATH_BUTTON_REGISTER_COMPANY, getTextResource("KSM004_1")))
        return getAndCloseMsgInfo()
    }

    //tab workplace
    @Step("Click workplace tab")
    fun selectTabWorkplace() {
        clickToElement(String.format(XPATH_TAB, getTextResource(TEXT_RESOURCES_TAB_WORK_PLACE)))
    }

    @Step("年月 で「{0}」を選択")
    fun inputDateTabWorkplace(date: String) {
        DatePicker(page, XPATH_INPUT_DATE_PICKER_TAB_WORK_PLACE).input(date)
        pressKeyToElement(page.locator(XPATH_CLICK_OUT_SIDE_ENTER_TAB_WORKPLACE), "Enter")
        takeScreenshot("年月 で「$date」を選択")
    }

    @Step("コード の行に 「{0}」 を選択")
    fun selectRowWorkplaceCode(workplaceCode: String) {
        TreeGrid(page, TREE_GRID_WORK_PLACE).selectByCode(workplaceCode)
        takeScreenshot("コード の行に $workplaceCode を選択")
    }

    fun clickOpenDialogKSM004DTabWorkplace() {
        clickToElement("Open KSM004D Dialog", String.format(XPATH_ID_OPEN_DIALOG_KSM004D_TAB_WORK_PLACE, getTextResource(TEXT_RESOURCES_BUTTON_KSM004D)))
    }

    fun clickButtonSaveWorkplace(): String {
        clickToElement("Click to register button", String.format(XPATH_BUTTON_REGISTER_WORK_PLACE, getTextResource("KSM004_1")))
        return getAndCloseMsgInfo()
    }

    //tab Classification
    @Step("Click to Classification tab")
    fun selectTabClass() {
        clickToElement(String.format(XPATH_TAB, getTextResource(TEXT_RESOURCES_TAB_CLASS)))
    }

    @Step("年月 で「{0}」を選択")
    fun inputDateTabClassification(date: String) {
        DatePicker(page, XPATH_INPUT_DATE_PICKER_TAB_CLASS).input(date)
        pressKeyToElement(page.locator(XPATH_CLICK_OUT_SIDE_ENTER_TAB_CLASS), "Enter")
        takeScreenshot("年月 で「$date」を選択")
    }

    @Step("コード の行に 「{0}」 を選択")
    fun selectRowGridClassification(classCode: String) {
        IgGrid(page, XPATH_ID_GRID_CLASSIFICATION).selectRowByCellName("コード", classCode)
        takeScreenshot("コード の行に $classCode を選択")
    }

    fun clickOpenDialogKSM004DTabClass() {
        clickToElement("Open KSM004D Dialog", String.format(XPATH_ID_OPEN_DIALOG_KSM004D_TAB_CLASS, getTextResource(TEXT_RESOURCES_BUTTON_KSM004D)))
    }

    fun clickButtonSaveClassification(): String {
        clickToElement("Click to register button", String.format(XPATH_BUTTON_REGISTER_WORK_CLASS, getTextResource("KSM004_1")))
        return getAndCloseMsgInfo()
    }

    companion object {
        const val TEXT_RESOURCES_BUTTON_KSM004D = "KSM004_57"
        const val TEXT_RESOURCES_TAB_COMPANY = "Com_Company"
        const val TEXT_RESOURCES_TAB_WORK_PLACE = "Com_Workplace"
        const val TEXT_RESOURCES_TAB_CLASS = "Com_Class"
        const val XPATH_TAB = "xpath=//td[@id='sidebar-area']//a[@role='tab-navigator' and text()='%s']"
        const val XPATH_ID_OPEN_DIALOG_KSM004D_TAB_COMPANY =
            "xpath=//div[@id='tabpanel-1']//button[contains(text(), '%s')]"
        const val XPATH_ID_OPEN_DIALOG_KSM004D_TAB_WORK_PLACE =
            "xpath=//div[@id='tabpanel-2']//button[contains(text(), '%s')]"
        const val XPATH_ID_OPEN_DIALOG_KSM004D_TAB_CLASS =
            "xpath=//div[@id='tabpanel-3']//button[contains(text(), '%s')]"
        const val XPATH_INPUT_START_DATE_DIALOG_KSM004D =
            "xpath=//div[@class='ntsDateRangePicker_Container']//div[contains(@class, 'ntsStartDate ntsControl')]//input"
        const val XPATH_INPUT_END_DATE_DIALOG_KSM004D =
            "xpath=//div[@class='ntsDateRangePicker_Container']//div[contains(@class, 'ntsEndDate ntsControl')]//input"
        const val XPATH_RADIO_BUTTON_DIALOG_KSM004D =
            "xpath=//label[contains(text(),'%s')]/parent::td/following-sibling::td//div"
        const val XPATH_BUTTON_REGISTER_COMPANY = "xpath=//div[@id='tabpanel-1']//button[contains(text(), '%s')]"
        const val XPATH_BUTTON_REGISTER_WORK_PLACE = "xpath=//div[@id='tabpanel-2']//button[contains(text(), '%s')]"
        const val XPATH_BUTTON_REGISTER_WORK_CLASS = "xpath=//div[@id='tabpanel-3']//button[contains(text(), '%s')]"
        const val XPATH_CLICK_OUT_SIDE_ENTER =
            "//div[@class='ntsDateRangePicker_Container']//div[contains(@class, 'ntsEndDate ntsControl')]//input"
        const val XPATH_ID_GRID_CLASSIFICATION = "//div[@id='classification-list-setting']"
        const val TREE_GRID_WORK_PLACE = "#single-tree-grid-tree-grid_container"
        const val XPATH_INPUT_DATE_PICKER_TAB_COMPANY = "xpath=//input[@id='yearMonthPicker1']"
        const val XPATH_INPUT_DATE_PICKER_TAB_WORK_PLACE = "xpath=//input[@id='yearMonthPicker2']"
        const val XPATH_INPUT_DATE_PICKER_TAB_CLASS = "xpath=//input[@id='yearMonthPicker3']"
        const val XPATH_CLICK_OUT_SIDE_ENTER_TAB_COMPANY = "//input[@id='yearMonthPicker1']"
        const val XPATH_CLICK_OUT_SIDE_ENTER_TAB_WORKPLACE = "//input[@id='yearMonthPicker2']"
        const val XPATH_CLICK_OUT_SIDE_ENTER_TAB_CLASS = "//input[@id='yearMonthPicker3']"
    }
}