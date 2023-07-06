package shou.page.web.kmk007

import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.checkbox.Checkbox
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.switchbox.SwitchBox

class Kmk007Page() : BasePage() {
    @Step("新規登録前にデータを削除する")
    fun deleteWorktypeIfExisted(codes: List<String?>) {
        val workTypeItemDelete: MutableList<String> = ArrayList()
        page.locator(WORKTYPE_LIST).all().map { it.getAttribute("data-id") }.filter { codes.contains(it) }
            .forEach {
                clickToElement(String.format(WORKTYPE_ITEM, it))
                clickToButton("KMK007_5")
                messageConfirmYes("Msg_18")
                closeMsgInfo(getMessageResource("Msg_16"))
                page.waitForTimeout(1000.0)
            }
    }

    @Step("勤務種類の単位選択")
    fun chooseWorkTypeUnit(workTypeUnit: String) {
        val option = getTextResource(workTypeUnit)
        SwitchBox(page, SWITCH_BUTTON).select(option)
        takeScreenshot("Setting $option")
    }

    @Step("１日の勤務分類選択")
    fun chooseWorktypeOneDay(worktypeCls: String) {
        IgCombo(page, WORKTYPE_ONE_DAY_COMBOBOX).selectByTitle(worktypeCls)
        page.waitForTimeout(1000.0)
        takeScreenshot("Choose worktype $worktypeCls")
    }

    @Step("午前と午後の勤務分類の選択")
    fun chooseWorkTypeHalfDay(workTypeMorningCls: String, workTypeAfternoonCls: String) {
        IgCombo(page, WORKTYPE_MORNING_COMBOBOX).selectByTitle(workTypeMorningCls)
        IgCombo(page, WORKTYPE_AFTERNOON_COMBOBOX).selectByTitle(workTypeAfternoonCls)
        page.waitForTimeout(1000.0)
        takeScreenshot("午前と午後の勤務分類の選択")
    }

    @Step("休日分類の選択")
    fun chooseHolidayClsInWorkTypeOneDay(holidayCls: String) {
        IgCombo(page, HOLIDAY_CLS_IN_WORKTYPE_ONEDAY).selectByTitle(holidayCls)
    }

    @Step("午前の休日分類の選択")
    fun chooseHolidayClsInWorkTypeMorning(holidayClsMorning: String) {
        IgCombo(page, HOLIDAY_CLS_IN_MORNING_WORKTYPE).selectByTitle(holidayClsMorning)
    }

    @Step("午後の休日分類の選択")
    fun chooseHolidayClsInWorkTypeAfternoon(holidayClsAfternoon: String) {
        IgCombo(page, HOLIDAY_CLS_IN_AFTERNOON_WORKTYPE).selectByTitle(holidayClsAfternoon)
    }

    @Step("１日の勤務種類のチェックボックスをクリック")
    fun chooseOptionInWorkTypeOneDay(options: List<String?>) {
        val checkboxElement = Checkbox(page, ONEDAY_OPTION_CHECKBOX)
        for (option in options) {
            checkboxElement.check(getTextResource(option))
        }
        takeScreenshot("１日の勤務種類のチェックボックスをクリック")
    }

    @Step("午前の勤務種類のチェックボックスをクリック")
    fun chooseOptionInWorkTypeMorning(options: List<String>) {
        val checkboxElement = Checkbox(page, MORNING_OPTION_CHECKBOX)
        for (option in options) {
            checkboxElement.check(getTextResource(option))
        }
        takeScreenshot("午前の勤務種類のチェックボックスをクリック")
    }

    @Step("午後の勤務種類のチェックボックスをクリック")
    fun chooseOptionInWorkTypeAfternoon(options: List<String>) {
        val checkboxElement = Checkbox(page, AFTERNOON_OPTION_CHECKBOX)
        for (option in options) {
            checkboxElement.check(getTextResource(option))
        }
        takeScreenshot("午後の勤務種類のチェックボックスをクリック")
    }

    @Step("勤務種類を登録")
    fun registerWorktype(workType: WorkType): String? {
        clickToButton("KMK007_1") //create new btn
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "input-workTypeCode"), workType.code, "コード")
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "input-workTypeName"), workType.name, "名称")
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "abbreviation-name-input"), workType.shortName, "略名")
        when (workType.workTypeUnit) {
            WorkTypeUnit.OneDay -> {
                chooseWorkTypeUnit(workType.workTypeUnit.textRsID)
                chooseWorktypeOneDay(workType.workTypeCls?.workTypeName!!)
                if (workType.holidayCls != null) {
                    chooseHolidayClsInWorkTypeOneDay(workType.holidayCls)
                }
                if (workType.optionsListOneDay != null) {
                    chooseOptionInWorkTypeOneDay(workType.optionsListOneDay.items)
                }
            }
            else -> {
                chooseWorkTypeUnit(workType.workTypeUnit!!.textRsID)
                chooseWorkTypeHalfDay(workType.workTypeMorningCls!!.workTypeName, workType.workTypeAfternoonCls!!.workTypeName)
                if (workType.holidayClsMorning != null) {
                    chooseHolidayClsInWorkTypeMorning(workType.holidayClsMorning)
                }
                if (workType.holidayClsAfternoon != null) {
                    chooseHolidayClsInWorkTypeAfternoon(workType.holidayClsAfternoon)
                }
                if (workType.optionsListMorning != null) {
                    chooseOptionInWorkTypeMorning(workType.optionsListMorning.items)
                }
                if (workType.optionsListAfternoon != null) {
                    chooseOptionInWorkTypeAfternoon(workType.optionsListAfternoon.items)
                }
            }
        }
        clickToButton("KMK007_2") //register btn
        return getAndCloseMsgInfo()
    }

    companion object {
        const val WORKTYPE_ONE_DAY_COMBOBOX = "//div[@id='duty-type-set-oneday']/div[contains(@class,'ui-igcombo-wrapper')]"
        const val WORKTYPE_MORNING_COMBOBOX = "xpath=//div[@id='morning']/div[contains(@class,'ui-igcombo-wrapper')]"
        const val WORKTYPE_AFTERNOON_COMBOBOX = "//div[@id='afternoon']/div[contains(@class,'ui-igcombo-wrapper')]"
        const val SWITCH_BUTTON = "//div[contains(@class,'switchbox-wrappers')]"
        const val WORKTYPE_ITEM = "//tr[@data-id='%s']"
        const val WORKTYPE_LIST = "//table[@id='single-list']//tr[@data-id]"
        const val HOLIDAY_CLS_IN_WORKTYPE_ONEDAY = "//div[contains(@data-bind,'.oneDay')]//div[contains(@class,'ui-igcombo-wrapper')]"
        const val HOLIDAY_CLS_IN_MORNING_WORKTYPE = "//div[contains(@data-bind,'.morning')]//div[contains(@class,'ui-igcombo-wrapper')]"
        const val HOLIDAY_CLS_IN_AFTERNOON_WORKTYPE = "//div[contains(@data-bind,'.afternoon')]//div[contains(@class,'ui-igcombo-wrapper')]"
        const val ONEDAY_OPTION_CHECKBOX = "//div[contains(@data-bind,'.oneDay')]//div[contains(@class,'checkbox-wrapper')]"
        const val MORNING_OPTION_CHECKBOX = "//div[@id='morning']//div[contains(@class,'checkbox-wrapper')]"
        const val AFTERNOON_OPTION_CHECKBOX = "//div[@id='afternoon']//div[contains(@class,'checkbox-wrapper')]"
    }
}