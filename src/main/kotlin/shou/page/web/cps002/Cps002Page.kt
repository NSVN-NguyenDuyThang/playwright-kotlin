package shou.page.web.cps002

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.iggrid.IgGrid
import shou.common.web.element.radiobutton.RadioButton


class Cps002Page() : BasePage() {

    private val workplaceDlg: FrameLocator
        get() = getFrame(getTextResource("Com_Workplace") + getTextResource("CPS001_98"))

    private val kdl003Dlg: FrameLocator
        get() = getFrame(getTextResource("KDL003_1"))

    private val kdl002Dlg: FrameLocator
        get() = getFrame(getTextResource("KDL002_1"))

    @Step("個人情報の新規登録")
    fun settingMaster(setting: EmployeeSetting): String? {
        var result: String? = ""
        settingMasterStep1(setting)
        val validateEmployee: EnumValidateEmployee = checkMsgDisplayedStep()
        if (validateEmployee.value === EnumValidateEmployee.EMPLOYEE_BASIC.value) {
            settingMasterStep3(setting)
        }
        when (validateEmployee) {
            EnumValidateEmployee.EMPLOYEE_BASIC -> result = getMsgDisplayed()
            EnumValidateEmployee.EMPLOYEE_ERROR -> result = "Error!!!"
            EnumValidateEmployee.EMPLOYEE_EXITED -> result = getTextResource("CPS002_93")
        }
        return result
    }

    fun settingMasterStep1(setting: EmployeeSetting) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "hireDate"), setting.joiningDate, getTextResource("CPS002_103"))
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "employeeCode"), setting.employeeCode, getTextResource("CPS002_104"))
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "employeeName"), setting.employeeName, getTextResource("CPS002_105"))
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "cardNumber"), setting.cardNo,  getTextResource("CPS002_18"))
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "loginId"), setting.loginId, getTextResource("CPS002_108"))
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "password"), setting.password, getTextResource("CPS002_109"))
        RadioButton(page, RADIO_GROUP).check(getTextResource(setting.defaultInformation))
        clickToElement("Click to complete button", BUTTON_COMPLETE)
    }


    fun settingMasterStep3(setting: EmployeeSetting) {
        fillToElement(String.format(INPUT_STRING, "IS00004"), setting.personalName, getElementInnerText(page.locator(String.format(LABEL, "IS00004"))))
        fillToElement(String.format(INPUT_STRING, "IS00009"), setting.displayName, getElementInnerText(page.locator(String.format(LABEL, "IS00009"))))
        fillToElement(String.format(INPUT_DATE, "IS00017"), setting.birthDay, getElementInnerText(page.locator(String.format(LABEL_DATE_COMBO, "IS00017"))))
        selectCombobox(setting.sex, "IS00018")
        selectCombobox(setting.classificationCD, "IS00028")
        selectCombobox(setting.employmentCD, "IS00068")
        selectCombobox(setting.jobTitleCD, "IS00079")
        selectWorkPlace(setting.workPlaceCD)
        selectCombobox(setting.employmentCategory, "IS00252")
        fillToElement(String.format(INPUT_STRING, "IS00253"), setting.contract, getElementInnerText(page.locator(String.format(LABEL, "IS00253"))))
        selectRadioChild(setting.workDuringVacation, "IS00248")
        selectRadio(setting.attendenceAndLeaving, "IS00258")
        selectCombobox(setting.classification, "IS00259")
        selectRadioChild(getTextResource(setting.scheduleManagement), "IS00121")
        selectComboboxChild(setting.scheduleCreate, "IS00123")
        selectComboboxChild(setting.businessReference, "IS00126")
        val labelWeekday = getElementInnerText(page.locator(String.format(LABEL_BUTTON_SELECT, "IS00130")))
        selectWorkTypeAndWorkHour(setting.workTypeWeekday, setting.workHoursWeekday, "IS00130", labelWeekday)
        val labelHolidayType = getElementInnerText(page.locator(String.format(LABEL_BUTTON_SELECT, "IS00128")))
        selectHolidayType(setting.holidayType, labelHolidayType)
        val labelHoliday = getElementInnerText(page.locator(String.format(LABEL_BUTTON_SELECT, "IS00139")))
        selectWorkTypeAndWorkHour(setting.workTypeLeave, setting.workHoursHoliday, "IS00139", labelHoliday)
        fillToElement(String.format(INPUT_DATE, "IS00279"), setting.criteriaGrantingAnnualLeave, getElementInnerText(page.locator(String.format(LABEL_DATE_2, "IS00279"))))
        selectComboboxChild(setting.annualLeaveGrantTable!!.split("　").get(1), "IS00280")
        clickToButton("CPS002_53")
    }

    private fun selectWorkPlace(workPlaceCD: String?) {
        clickToElement("ボタン ${getTextResource("CPS001_98")} をクリックする ", String.format(BUTTON_SELECT, "IS00084"))
        selectWork(workPlaceCD!!, getTextResource("Com_Workplace"))
        clickToButton(workplaceDlg,"CDL008_3")
    }

    private fun selectWorkTypeAndWorkHour(workType: String?, workHour: String?, dynamicValues: String, label: String) {
        clickToElement("ボタン ${getTextResource("CPS001_98")} をクリックする ", String.format(BUTTON_SELECT, dynamicValues))
        val gridElementWorkType = IgGrid(page, GRID_WORK_TYPE, kdl003Dlg)
        selectCode(gridElementWorkType, workType!!, "KDL003_5", label)
        val gridElementWorkHour = IgGrid(page, GRID_WORK_HOUR, kdl003Dlg)
        selectCode(gridElementWorkHour, workHour!!, "KDL003_5", label)
        clickToButton(kdl003Dlg, "KDL003_22")
    }


    private fun selectHolidayType(holiday: String?, label: String) {
        clickToElement("ボタン ${getTextResource("CPS001_98")} をクリックする ", String.format(BUTTON_SELECT, "IS00128"))
        val gridElementWorkHour = IgGrid(page, GRID_WORK_HOLIDAY, kdl002Dlg)
        gridElementWorkHour.selectRowByCellName(getTextResource("KDL002_3"), holiday!!)
        selectCode(gridElementWorkHour, holiday, "KDL002_3", label)
        clickToButton(kdl002Dlg,"KDL002_6")
    }


    private fun selectCombobox(value: String?, dynamicValues: String) {
        val label = getElementInnerText(page.locator(String.format(LABEL_DATE_COMBO, dynamicValues)))
        addStep("「 $label 」の行に「$value」を選択", Runnable { IgCombo(page, String.format(COMBOBOX, dynamicValues)).selectByStartTitle(value!!) })
    }

    private fun selectComboboxChild(value: String?, dynamicValues: String) {
        val label = getElementInnerText(page.locator(String.format(LABEL_DATE_2, dynamicValues)))
        addStep("「 $label 」の行に「$value」を選択", Runnable { IgCombo(page, String.format(COMBOBOX, dynamicValues)).selectByStartTitle(value!!) })
    }

    @Step("「{1}」の行に「{0}」を選択")
    private fun printStep(value: String, label: String) {
        takeScreenshot("「 $label 」の行に「$value」を選択")
    }


    private fun selectRadio(value: String?, dynamicValues: String) {
        var label = if (dynamicValues == "IS00248" || dynamicValues == "IS00121") {
            getElementInnerText(page.locator(String.format(LABEL_DATE_2, dynamicValues)))
        } else {
            getElementInnerText(page.locator(String.format(LABEL_DATE_COMBO, dynamicValues)))
        }
        addStep("「 $label 」の行に「$value」を選択", Runnable { RadioButton(page, String.format(COMBOBOX, dynamicValues)).check(value!!) })
    }

    private fun selectRadioChild(value: String?, dynamicValues: String) {
        val label = getElementInnerText(page.locator(String.format(LABEL_RADIO, dynamicValues)))
        addStep("「 $label 」の行に「$value」を選択", Runnable { RadioButton(page, String.format(COMBOBOX, dynamicValues)).check(value!!) })
    }

    @Step("「{1}」の行に「{0}」を選択")
    private fun selectWork(workPlaceCD: String, label: String) {
        fillToElement(workplaceDlg.locator(SEARCH_BOX), workPlaceCD)
        pressKeyToElement(workplaceDlg.locator(SEARCH_BOX), "Enter")
        takeScreenshot("「 $workPlaceCD 」の行に「$label」を選択")
    }


    @Step("「{3}」の行に「{1}」を選択")
    private fun selectCode(igGridElement: IgGrid, code: String, textResource: String, label: String) {
        igGridElement.selectRowByCellName(getTextResource(textResource), code)
        takeScreenshot("「 $label 」の行に「$code」を選択")
    }

    fun checkMsgDisplayedStep(): EnumValidateEmployee {
        if (isErrorDialogDisplayed()) {
            val msgIds: List<String> = getMsgIdAndCloseErrorDialog()
            return if (msgIds.contains("Msg_345")) {
                EnumValidateEmployee.EMPLOYEE_EXITED
            } else EnumValidateEmployee.EMPLOYEE_ERROR
        }
        return EnumValidateEmployee.EMPLOYEE_BASIC
    }

    fun getMsgDisplayed(): String? {
        val frame = getFrame(getTextResource("CPS002_8"))
        val contend = getElementInnerText(frame.locator(LABEL_REGISTER))
        clickToButton(frame,"CPS002_94")
        return contend
    }

    fun contendActual(): String? {
        return getTextResource("CPS002_93")
    }

    companion object {
        const val RADIO_GROUP = "#init_select"
        const val INPUT_STRING = "//input[@data-code='%s']"
        const val INPUT_DATE = "//div[@data-code='%s']/input"
        const val BUTTON_COMPLETE = "//button[@id='step1_next_btn']"
        const val LABEL = "//input[@data-code='%s']/..//preceding-sibling::div/span"
        const val LABEL_DATE_COMBO = "//div[@data-code='%s']//preceding-sibling::div/span"
        const val LABEL_DATE_2 = "//div[@data-code='%s']//preceding-sibling::span"
        const val LABEL_RADIO = "//div[@data-code='%s']/../../preceding-sibling::div/span"
        const val COMBOBOX = "//div[@data-code='%s']"
        const val BUTTON_SELECT = "//button[@data-code='%s']"
        const val LABEL_BUTTON_SELECT = "//button[@data-code='%s']//preceding-sibling::span"
        const val SEARCH_BOX = "//input[@class='ntsSearchBox nts-editor ntsSearchBox_Component']" //SEARCH
        const val GRID_WORK_TYPE = "//div[@id='list-worktype_container']"
        const val GRID_WORK_HOUR = "//div[@id='day-list-tbl_container']"
        const val GRID_WORK_HOLIDAY = "//div[@id='multi-list_container']"
        const val LABEL_REGISTER = "//label[@id='lbl_Notification']"
    }
}