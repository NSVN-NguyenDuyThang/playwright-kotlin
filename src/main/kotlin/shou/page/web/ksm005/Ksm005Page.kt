package shou.page.web.ksm005

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.iggrid.IgGrid

class Ksm005Page() : BasePage() {

    private val batchSettingDlg: FrameLocator
        get() = getFrame(getTextResource("KSM005_10"))

    private val kdl003Dlg: FrameLocator
        get() = getFrame(getTextResource("KDL003_1"))

    private val kdl002aDlg: FrameLocator
        get() = getFrame(getTextResource("KDL002_1"))
    fun getMsgDisplayed(): String {
        return getAndCloseMsgInfo()
    }

    fun clickButtonCreateNew() {
        clickToButton("KSM005_8")
    }

    fun inputCodeName(patternCode: String, patternName: String) {
        fillToElement(XPATH_INPUT_PATTERN_CODE, patternCode, "コード")
        fillToElement(XPATH_INPUT_PATTERN_NAME, patternName, "名称")
    }

    fun clickButtonRegister() {
        clickToButton("KSM005_9")
    }

    @Step("コード {0} が存在しているかどうか確認して、存在の場合は削除します。")
    fun checkExistsPatternCode(patternCode: String): Boolean {
        val igGridElement = IgGrid(page, XPATH_TABLE)
        val checkExists: Boolean? = igGridElement.isExistsRow(getTextResource("KSM005_13"), patternCode)
        if (checkExists == true) {
            igGridElement.selectRowByCellName(getTextResource("KSM005_13"), patternCode)
            clickToButton("KSM005_12")
            messageConfirmYes("Msg_18")
            closeMsgInfo(getMessageResource("Msg_16"))
        }
        takeScreenshot("コード $patternCode が存在しているかどうか確認して、存在の場合は削除します。")
        return checkExists!!
    }

    fun clickButtonOpenDialogBatchSetting() {
        clickToButton("KSM005_10")
    }

    fun clickButtonOpenDialogKLD003A() {
        clickToElement("Open KDL003 Dialog", batchSettingDlg.locator(String.format(XPATH_BUTTON_KDL003A, getTextResource("KSM005_23"))))
    }

    @Step("稼働日: コード {0} 勤務種類 と コード {1} 就業時間帯 の行に を選択")
    fun selectWorkTypeAndWordTimeAndClickSubmitKDL003(workTypeCode: String, workTimeCode: String) {
        val igGridListWorktype = IgGrid(page, XPATH_ID_GRID_KDL003_WORK_TYPE, kdl003Dlg)
        val nameColumnCode = getTextResource("KDL003_5") //コード
        igGridListWorktype.selectRowByCellName(nameColumnCode, workTypeCode)
        page.waitForTimeout(1000.0)
        val igGriddayList = IgGrid(page, XPATH_ID_GRID_KDL003_WORK_TIME, kdl003Dlg)
        igGriddayList.selectRowByCellName(nameColumnCode, workTimeCode)
        takeScreenshot("稼働日: コード $workTypeCode 勤務種類 と コード $workTimeCode 就業時間帯 の行に を選択")

        //click button submit
        clickToButton(kdl003Dlg, TEXT_RESOURCE_BUTTON_SUBMIT_KDL003)
    }

    fun clickOpenDialogHolidayKDL002A() {
        clickToElement("Open Holiday KDL002A", kdl003Dlg.locator(XPATH_ID_BUTTON_OPEN_DIALOG_LEGAL_HOLIDAY_KDL002A))
    }

    @Step("{0} : コード {1} 勤務種類 の行に を選択")
    fun selectWorkTypeClickSubmitKDL002A(stepName: String, workTypeCode: String) {
        //select row iggrid
        val igGridWorkType = IgGrid(page, XPATH_ID_GRID_KDL002_WORK_TYPE)
        val nameColumnCode = getTextResource("KDL002_3") //コード
        igGridWorkType.selectRowByCellName(nameColumnCode, workTypeCode)
        takeScreenshot("$stepName : コード $workTypeCode 勤務種類 の行に を選択")

        //click button submit on dialog
        clickToElement(kdl002aDlg.locator(XPATH_BUTTON_SUBMIT_KDL002))
    }

    fun clickOpenDialogNonHolidayKDL002A() {
        clickToElement("Open Non Holiday KDL002A", kdl003Dlg.locator(XPATH_ID_BUTTON_OPEN_DIALOG_NON_LEGAL_HOLIDAY_KDL002A))
    }

    fun clickButtonExecuteBatchSetting() {
        clickToElement("Click execute btn", batchSettingDlg.locator(String.format(XPATH_BUTTON_EXCUTE_BATCH_SETTING, "KSM005_34")))
        closeMsgInfo("Msg_15")
    }

    companion object {
        const val XPATH_INPUT_PATTERN_CODE = "xpath=//input[@id='inp_monthlyPatternCode']"
        const val XPATH_INPUT_PATTERN_NAME = "xpath=//input[@id='inp_monthlyPatternName']"
        const val XPATH_TABLE = "xpath=//table[@id='lstMonthlyPattern']"
        const val XPATH_BUTTON_KDL003A = "xpath=//div[@id='dv-workday-area']//button[contains(text(), '%s')]"
        const val XPATH_ID_GRID_KDL003_WORK_TYPE = "//div[@id='list-worktype_container']"
        const val XPATH_ID_GRID_KDL003_WORK_TIME = "//div[@id='day-list-tbl_container']"
        const val TEXT_RESOURCE_BUTTON_SUBMIT_KDL003 = "KDL003_22"
        const val XPATH_ID_BUTTON_OPEN_DIALOG_LEGAL_HOLIDAY_KDL002A = "xpath=//button[@tabindex='7']"
        const val XPATH_ID_GRID_KDL002_WORK_TYPE = "//div[@id='multi-list_container']"
        const val XPATH_BUTTON_SUBMIT_KDL002 = "//button[@id='btnSetting']"
        const val XPATH_ID_BUTTON_OPEN_DIALOG_NON_LEGAL_HOLIDAY_KDL002A = "xpath=//button[@tabindex='8']"
        const val XPATH_BUTTON_EXCUTE_BATCH_SETTING =
            "xpath=//div[@id='functions-area-bottom']//button[contains(text(), '%s')]"
    }
}