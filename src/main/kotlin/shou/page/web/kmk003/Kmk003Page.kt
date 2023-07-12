package shou.page.web.kmk003

import com.microsoft.playwright.Locator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.checkbox.Checkbox
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.switchbox.SwitchBox
import shou.utils.model.Period
import java.lang.IllegalArgumentException

class Kmk003Page() : BasePage() {

    private var switchBoxHalfDay: SwitchBox? = null
        get() = SwitchBox(page, String.format(SWITCH_BUTTON, getTextResource("KMK003_319")))
    private var switchBoxFixedBreaktime: SwitchBox? = null
        get() = SwitchBox(page, String.format(SWITCH_BUTTON, getTextResource("KMK003_141")))
    private var switchBoxCalculateAuto: SwitchBox? = null
        get() = SwitchBox(page, String.format(SWITCH_BUTTON, getTextResource("KMK003_185")))
    @Step("新規登録前にデータを削除する")
    fun deleteWorkTimeIfExisted(codes: List<String>) {
        page.locator(WORKTIME_LIST).all().map { it.getAttribute("data-id") }.filter { codes.contains(it) }
            .forEach {
                clickToElement(String.format(WORKTIME_ITEM, it))
                clickToButton("KMK007_5")
                messageConfirmYes("Msg_18")
                closeMsgInfo(getMessageResource("Msg_16"))
                page.waitForTimeout(1000.0)
            }
    }

    @Step("ボタン「行挿入」をクリック")
    private fun clickToAddRowButton(textRsIdOfButton: String) {
        clickToElement(String.format(ADD_ROW_BUTTON, getTextResource(textRsIdOfButton)))
    }

    @Step("就業時間帯コード入力")
    private fun inputWorkTimeCode(workTimeCode: String) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "inp-worktimecode"), workTimeCode)
        takeScreenshot("就業時間帯コード入力")
    }

    @Step("就業時間帯名称の入力")
    private fun inputWorkTimeName(workTimeName: String) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "inp-worktimename"), workTimeName)
        takeScreenshot("就業時間帯名称の入力")
    }

    @Step("就業時間帯の略名の入力")
    private fun inputWorkTimeShortName(workTimeShortName: String?) {
        fillToElement(WORKTIME_SHORT_NAME_TEXTBOX, workTimeShortName)
        takeScreenshot("就業時間帯の略名の入力")
    }

    @Step("就業時間帯の勤務形態を選択")
    private fun chooseWorkCategory(workTimeType: String) {
        IgCombo(page, String.format(COMBOBOX, getTextResource("KMK003_7"))).selectByTitle(workTimeType)
        takeScreenshot("就業時間帯の勤務形態を選択")
    }

    @Step("就業時間帯の設定方法を選択")
    private fun chooseSettingMethod(workTime: WorkTime) {
        if (workTime.workTimeCategory !== WorktimeCategory.FLEX) {
            IgCombo(page, String.format(COMBOBOX, getTextResource("KMK003_8"))).selectByTitle(workTime.settingMethod!!.value)
            takeScreenshot("就業時間帯の設定方法を選択")
        }
    }

    @Step("モード{0}を選択")
    private fun chooseModeToInputData(mode: String) {
        val option = getTextResource(mode)
        SwitchBox(page, SWITCH_TAB_MODE_BUTTON).select(option)
        takeScreenshot("モード" + option + "を選択")
    }

    @Step("{0}タブをクリック")
    private fun clickToTab(tabName: String) {
        clickToElement(String.format(TAB_PANEL, tabName))
        takeScreenshot(getTextResource(tabName) + "タブをクリック")
    }

    private fun inputTimeRange(rowIndex: String, startTime: String, endTime: String) {
        val startTimeTextbox: Locator = page.locator(FIXED_TABLE).locator(String.format(START_TIME_TEXTBOX, rowIndex))
        val endTimeTextbox: Locator = page.locator(FIXED_TABLE).locator(String.format(END_TIME_TEXTBOX, rowIndex))
        fillToElement(startTimeTextbox, startTime)
        fillToElement(endTimeTextbox, endTime)
    }

    private fun inputElapsedTime(rowIndex: String, elapsedTime: String) {
        val elapsedTimeTextbox: Locator = page.locator(FIXED_TABLE).locator(String.format(ELAPSED_TIME_TEXTBOX, rowIndex, "KMK003_174"))
        fillToElement(elapsedTimeTextbox, elapsedTime)
    }

    private fun inputTimeRangeToFixedTable(periodTimes: List<Period>?, tab: TabPanelSelection) {
        if (periodTimes != null) {
            for (i in periodTimes.indices) {
                if (i == 0 && !(tab == TabPanelSelection.WORKING_HOUR || tab == TabPanelSelection.FLEXIBLE_TIME)) {
                    clickToAddRowButton("KMK003_52")
                }
                if (i > 0) {
                    clickToAddRowButton("KMK003_52")
                }
                val periodTime = periodTimes[i]
                inputTimeRange((i + 1).toString(), periodTime.start, periodTime.end!!)
            }
        }
    }

    private fun inputElapsedTimeToFixedTable(elapsedTimesList: List<String>?) {
        if (elapsedTimesList != null) {
            for (i in elapsedTimesList.indices) {
                clickToAddRowButton("KMK003_52")
                inputElapsedTime((i + 1).toString(), elapsedTimesList[i])
            }
        }
    }

    private fun chooseCalculateAutomationSwitchButton(automaticCalculate: Boolean?) {
        if (automaticCalculate != null) {
            when (automaticCalculate) {
                true -> switchBoxCalculateAuto?.select(getTextResource("KMK003_142"))
                false -> switchBoxCalculateAuto?.select(getTextResource("KMK003_143"))
            }
        }
    }

    private fun chooseFixedBreaktimeSwitchButton(fixedBreaktime: Boolean?) {
        var fixedBreaktime = fixedBreaktime
        if (fixedBreaktime != null) {
            when (fixedBreaktime) {
                true -> switchBoxFixedBreaktime?.select(getTextResource("KMK003_142"))
                false -> switchBoxFixedBreaktime?.select(getTextResource("KMK003_143"))
            }
        }
    }

    private fun chooseSettingForHalfDaySwitchButton(settingForHalfday: Boolean?) {
        if (settingForHalfday != null) {
            when (settingForHalfday) {
                true -> switchBoxHalfDay?.select(getTextResource("KMK003_321"))
                false -> switchBoxHalfDay?.select(getTextResource("KMK003_322"))
            }
        }
    }

    @Step("タブ所定で値を入力")
    private fun inputValueToTabPredetermined(workTime: WorkTime) {
        val predetermined = workTime.predeterminedTab
        clickToTab(getTextResource(TabPanelSelection.PREDETERMINED.textRsId))
        fillToElement(String.format(TIME_TEXTBOX, "KMK003_32"), predetermined!!.dayStartTime)
        typeToElement(String.format(TIME_TEXTBOX, "KMK003_33"), predetermined!!.rangeHoursPerDay)
        fillToElement(String.format(TIME_TEXTBOX,  "KMK003_163"), predetermined!!.workingHours?.start)
        fillToElement(String.format(TIME_TEXTBOX, "KMK003_164"), predetermined!!.workingHours?.end)
        when(workTime.workTimeCategory) {
            WorktimeCategory.FLEX -> {
                if (predetermined!!.usedCoreTimeZone!!) {
                    SwitchBox(page, String.format(SWITCH_BUTTON, getTextResource("KMK003_157"))).select(getTextResource("KMK003_158"))
                    fillToElement(String.format(CORE_TIME_TEXTBOX, getTextResource("KMK003_318"), "KMK003_163"), predetermined.coreTimeZone?.start)
                    fillToElement(String.format(CORE_TIME_TEXTBOX, getTextResource("KMK003_318"), "KMK003_164"), predetermined.coreTimeZone?.end)
                }
            }
            else -> {
                if (predetermined!!.useWorkingHours2!!) {
                    Checkbox(page, USE_WORK_NO_2_CHECKBOX).check(getTextResource("KMK003_37"))
                    typeToElement(String.format(CommonUI.INPUT_BY_ID, "shiftTwoStart"), predetermined.workingHours2?.start)
                    typeToElement(String.format(CommonUI.INPUT_BY_ID, "shiftTwoEnd"), predetermined.workingHours2?.end)
                }

            }
        }
        fillToElement(String.format(TIME_TEXTBOX, "KMK003_39"), predetermined.endTimeOfMorningShift)
        fillToElement(String.format(TIME_TEXTBOX, "KMK003_40"), predetermined.startTimeOfAfternoonShift)
        fillToElement(String.format(TIME_TEXTBOX, "KMK003_42"), predetermined.workingHour1Day)
        if (predetermined.workingHourMorning != null) {
            fillToElement(String.format(TIME_TEXTBOX, "KMK003_43"), predetermined.workingHourMorning)
        }
        if (predetermined.workingHourAfternoon != null) {
            fillToElement(String.format(TIME_TEXTBOX, "KMK003_44"), predetermined.workingHourAfternoon)
        }
        takeScreenshot("タブ「所定」でデータを入力")
    }

    @Step("タブ勤務時間で値を入力")
    private fun inputValueToTabWorkingHour(workTime: WorkTime) {
        clickToTab(getTextResource(TabPanelSelection.WORKING_HOUR.textRsId))
        val workingHourList: List<Period> = workTime.workingHoursTab!!.oneDayWorkingTimeList!!.items
        inputTimeRangeToFixedTable(workingHourList, TabPanelSelection.WORKING_HOUR)
        takeScreenshot("タブ「勤務時間」でデータを入力")
    }

    @Step("タブ残業時間帯で値を入力")
    private fun inputValueToTabOvertime(workTime: WorkTime) {
        clickToTab(getTextResource(TabPanelSelection.OVERTIME.textRsId))
        val overtimeSetting = workTime.overtimeTab
        chooseSettingForHalfDaySwitchButton(overtimeSetting!!.settingForHalfday)
        chooseCalculateAutomationSwitchButton(overtimeSetting.automaticCalculate)
        when(workTime.settingMethod) {
            SettingMethod.FLEX -> inputElapsedTimeToFixedTable(overtimeSetting.elapsedTimeList!!.items)
            else -> inputTimeRangeToFixedTable(overtimeSetting.overTimeList!!.items, TabPanelSelection.OVERTIME)
        }
        takeScreenshot("タブ「残業時間帯」でデータを入力")
    }

    @Step("タブ休憩時間帯で値を入力")
    private fun inputValueToTabBreaktime(workTime: WorkTime) {
        clickToTab(getTextResource(TabPanelSelection.BREAK_TIME.textRsId))
        val breakTimeSetting = workTime.breaktimeTab
        chooseSettingForHalfDaySwitchButton(breakTimeSetting!!.settingForHalfday)
        chooseFixedBreaktimeSwitchButton(breakTimeSetting.fixedBreaktime)
        val breakTimeList: List<Period> = breakTimeSetting.breakTimeList!!.items
        inputTimeRangeToFixedTable(breakTimeList, TabPanelSelection.BREAK_TIME)
        takeScreenshot("タブ「休憩時間帯」でデータを入力")
    }

    @Step("タブ休出時間帯で値を入力")
    private fun inputValueToTabHolidayWork(workTime: WorkTime) {
        clickToTab(getTextResource(TabPanelSelection.HOLIDAY_WORK.textRsId))
        val vacationHoursSetting = workTime.vacationHoursTab
        when (workTime.settingMethod) {
            SettingMethod.FLEX -> inputElapsedTimeToFixedTable(vacationHoursSetting!!.elapsedTimeList!!.items)
            else -> inputTimeRangeToFixedTable(vacationHoursSetting!!.oneDayWorkingTimeList!!.items, TabPanelSelection.HOLIDAY_WORK)
        }
        takeScreenshot("タブ「休出時間帯」でデータを入力")
    }

    @Step("タブ休出休憩で値を入力")
    private fun inputValueToTabHolidayWorkBreakTime(workTime: WorkTime) {
        clickToTab(getTextResource(TabPanelSelection.HOLIDAY_BREAK_TIME.textRsId))
        val vacationBreakSetting = workTime.vacationBreakTab
        if (workTime.workTimeCategory === WorktimeCategory.FLEX || workTime.settingMethod === SettingMethod.FIXED) {
            chooseFixedBreaktimeSwitchButton(vacationBreakSetting!!.fixedBreaktime)
        }
        val vacationBreaktimeList: List<Period> = vacationBreakSetting!!.vacationBreakTimeList!!.items
        inputTimeRangeToFixedTable(vacationBreaktimeList, TabPanelSelection.HOLIDAY_BREAK_TIME)
        takeScreenshot("タブ「休出休憩」でデータを入力")
    }

    @Step("タブ代休で値を入力")
    private fun inputValueToTabCompensatory(workTime: WorkTime) {
        val compensatory = workTime.compensatoryVacationTab
        if (compensatory != null) {
            clickToTab(getTextResource(TabPanelSelection.COMPENSATORY_HOLIDAY.textRsId))
            if (compensatory.checkToVacationWork) {
                Checkbox(page, USE_HOLIDAY_WORK_CHECKBOX).check(getTextResource("KMK003_105"))
                fillToElement(String.format(TIME_TEXTBOX, "KMK003_42"), compensatory.oneDayTime)
                fillToElement(String.format(TIME_TEXTBOX, "KMK003_107"), compensatory.halfDayTime)
            }
            takeScreenshot("タブ「代休」でデータを入力")
        }
    }

    @Step("タブフレキシブルタイムで値を入力")
    private fun inputValueToTabFlexTime(workTime: WorkTime) {
        clickToTab(getTextResource(TabPanelSelection.FLEXIBLE_TIME.textRsId))
        val flexTimeList: List<Period> = workTime.flextimeTab!!.oneDayWorkingTimeList!!.items
        inputTimeRangeToFixedTable(flexTimeList, TabPanelSelection.FLEXIBLE_TIME)
        takeScreenshot("タブ「フレキシブルタイム」でデータを入力")
    }

    @Step("就業時間帯を登録")
    fun registerWorktime(workTime: WorkTime): String? {
        clickToButton("KMK003_3")
        inputWorkTimeCode(workTime.code!!)
        inputWorkTimeName(workTime.name!!)
        inputWorkTimeShortName(workTime.subName)
        chooseWorkCategory(workTime.workTimeCategory!!.value)
        chooseSettingMethod(workTime)
        chooseModeToInputData("KMK003_191")
        inputValueToTabPredetermined(workTime)
        if (workTime.workTimeCategory == WorktimeCategory.FLEX) {
            inputValueToTabFlexTime(workTime)
        }
        if (workTime.workTimeCategory !== WorktimeCategory.FLEX && workTime.settingMethod !== SettingMethod.FLEX) {
            inputValueToTabWorkingHour(workTime)
        }
        inputValueToTabOvertime(workTime)
        inputValueToTabBreaktime(workTime)
        inputValueToTabHolidayWork(workTime)
        inputValueToTabHolidayWorkBreakTime(workTime)
        inputValueToTabCompensatory(workTime)
        clickToButton("KMK003_4")
        return getAndCloseMsgInfo()
    }

    companion object {
        const val WORKTIME_LIST = "//table[@id='single-list']//tr[@data-id]"
        const val WORKTIME_ITEM = "//tr[@data-id='%s']"
        const val TAB_PANEL = "//div[@id='tab-panel']//span[text()='%s']"
        const val WORKTIME_SHORT_NAME_TEXTBOX = "//input[contains(@class,'workTimeAbName')]"
        const val TIME_TEXTBOX = "//div[contains(@class,'tab-a') and not (contains(@class,'hidden'))]//input[contains(@data-bind,'#[%s]')]"
        const val START_TIME_TEXTBOX = "//tr[%s]//input[contains(@data-bind,'startTime')]"
        const val END_TIME_TEXTBOX = "//tr[%s]//input[contains(@data-bind,'endTime')]"
        const val ELAPSED_TIME_TEXTBOX = "//tr[%s]//input[contains(@data-bind,'#[%s]')]"
        const val USE_HOLIDAY_WORK_CHECKBOX = "//div[@class='tab-a11']//div[contains(@class,'checkbox-wrapper')]"
        const val CORE_TIME_TEXTBOX = "//span[text()='%s']/following-sibling::span//input[contains(@data-bind,'#[%s]')]"
        const val SWITCH_BUTTON = "//div[contains(@class,'tab-a') and not (contains(@class,'hidden'))]//div[contains(@class,'form-label') and ./span[normalize-space()='%s']]/following-sibling::div[contains(@class,'switchbox-wrappers')]"
        const val FIXED_TABLE = "//div[contains(@class,'tab-a') and not (contains(@class,'hidden'))]//div[@class='nts-fixed-table cf']"
        const val COMBOBOX = "//div[@class='setting_header_br']//div[contains(@class,'form-label') and ./span[normalize-space()='%s']]/following-sibling::div[1]"
        const val SWITCH_TAB_MODE_BUTTON = "id=switch-tab-mode"
        const val ADD_ROW_BUTTON = "//div[contains(@class,'tab-a') and not (contains(@class,'hidden'))]//button[text()='%s']"
        const val USE_WORK_NO_2_CHECKBOX = "//div[contains(@class,'tab-a') and not (contains(@class,'hidden'))]//div[contains(@class,'label_time_tab1')]"
    }

}