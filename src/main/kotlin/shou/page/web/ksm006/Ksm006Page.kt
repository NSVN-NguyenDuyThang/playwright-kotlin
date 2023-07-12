package shou.page.web.ksm006

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.iggrid.IgGrid
import shou.common.web.element.treegrid.TreeGrid

class Ksm006Page() : BasePage() {
    private val kdl003: FrameLocator
        get() = getFrame(getTextResource(TEXT_RESOURCE_KDL003))
    private val kdl002: FrameLocator
        get() = getFrame(getTextResource(TEXT_RESOURCE_KDL002))

    //start tab company
    @Step("Select company tab")
    fun selectTabCompany() {
        clickToElement(XPATH_SELECT_TAB_COMPANY)
    }

    //start tab workplace
    fun clickTabWorkPlace() {
        clickToElement("Click to workplace tab", XPATH_SELECT_TAB_WORKPLACE)
    }

    //start tab Classification
    fun clickTabClassification() {
        clickToElement("Select classification tab", XPATH_SELECT_TAB_CLASS)
    }

    @Step("Open Kdl003 tab")
    fun clickOpenDialogWorkingDayKDL003() {
        clickToElement(XPATH_ID_BUTTON_OPEN_DIALOG_WORKING_DAY_KDL003)
    }

    @Step("Open kdl002 dialog")
    fun clickOpenDialogHolidayKDL002() {
        clickToElement(page.locator(XPATH_ID_BUTTON_OPEN_DIALOG_LEGAL_HOLIDAY_KDL002))
    }

    fun clickOpenDialogNonHolidayKDL002() {
        clickToElement("Click open kdl002 dialog", page.locator(XPATH_ID_BUTTON_OPEN_DIALOG_NON_LEGAL_HOLIDAY_KDL002))
    }

    fun clickButtonRegister(): String? {
        clickToElement("Click to register button", XPATH_ID_BUTTON_SAVE)
        return getAndCloseMsgInfo()
    }

    @Step("稼働日: コード {0} 勤務種類 と コード {1} 就業時間帯 の行に を選択")
    fun selectWorkTypeAndWordTimeAndClickSubmitKDL003(workTypeCode: String, workTimeCode: String) {
        val nameColumnCode = getTextResource("KDL003_5") //コード
        IgGrid(page, XPATH_ID_GRID_KDL003_WORK_TYPE, kdl003).selectRowByCellName(nameColumnCode, workTypeCode)
        IgGrid(page, XPATH_ID_GRID_KDL003_WORK_TIME, kdl003).selectRowByCellName(nameColumnCode, workTimeCode)
        takeScreenshot("稼働日: コード $workTypeCode 勤務種類 と コード $workTimeCode 就業時間帯 の行に を選択")
        //click button submit
        clickToButton(kdl003, "KDL003_22")
    }

    @Step("{0} : コード {0} 勤務種類 の行に を選択")
    fun selectWorkTypeClickSubmitKDL002(stepName: String, workTypeCode: String) {
        //select row iggrid
        val nameColumnCode = getTextResource("KDL002_3") //コード
        IgGrid(page, XPATH_ID_GRID_KDL002_WORK_TYPE, kdl002).selectRowByCellName(nameColumnCode, workTypeCode)
        takeScreenshot("$stepName : コード $workTypeCode 勤務種類 の行に を選択")
        //click button submit on dialog
        clickToElement("Click to submit", kdl002.locator(XPATH_BUTTON_SUBMIT_KDL002))
    }

    @Step("コード の行に 「{0}」 を選択")
    fun selectRowTreGridWorkplaceCode(workplaceCode: String) {
        TreeGrid(page, TREE_GRID_WORK_PLACE).selectByCode(workplaceCode)
        takeScreenshot("コード の行に $workplaceCode を選択")
    }


    @Step("コード の行に 「{0}」 を選択")
    fun selectRowGridClassification(classificationCode: String) {
        val nameColumnCode = getTextResource("KDL002_3") //コード
        IgGrid(page, XPATH_ID_GRID_CLASSIFICATION).selectRowByCellName(nameColumnCode, classificationCode)
        takeScreenshot("コード の行に $classificationCode を選択")
    }

    companion object {
        const val XPATH_ID_BUTTON_SAVE = "//div[@role='tabpanel' and contains(@id, 'Com_') and not(contains(@class, 'disappear'))]//button[contains(@id, 'register-btn')]"
        const val XPATH_SELECT_TAB_COMPANY = "#com-com"
        const val XPATH_SELECT_TAB_WORKPLACE = "#com-cls"
        const val XPATH_SELECT_TAB_CLASS = "#com-wpl"
        const val XPATH_ID_BUTTON_OPEN_DIALOG_WORKING_DAY_KDL003 = "//div[@role='tabpanel' and contains(@id, 'Com_') and not(contains(@class, 'disappear'))]//button[contains(@id, 'working-')]"
        const val XPATH_ID_BUTTON_OPEN_DIALOG_LEGAL_HOLIDAY_KDL002 = "//div[@role='tabpanel' and contains(@id, 'Com_') and not(contains(@class, 'disappear'))]//button[starts-with(@id, 'legal-holiday-')]"
        const val XPATH_ID_BUTTON_OPEN_DIALOG_NON_LEGAL_HOLIDAY_KDL002 = "//div[@role='tabpanel' and contains(@id, 'Com_') and not(contains(@class, 'disappear'))]//button[starts-with(@id, 'non-legal-holiday-')]"
        const val XPATH_ID_GRID_KDL003_WORK_TYPE = "#list-worktype_container"
        const val XPATH_ID_GRID_KDL003_WORK_TIME = "#day-list-tbl_container"
        const val TEXT_RESOURCE_KDL002 = "KDL002_1"
        const val TEXT_RESOURCE_KDL003 = "KDL003_1"
        const val TREE_GRID_WORK_PLACE = "//div[@id='single-tree-grid-tree-grid_container']"
        const val XPATH_ID_GRID_KDL002_WORK_TYPE = "#multi-list_container"
        const val XPATH_BUTTON_SUBMIT_KDL002 = "#btnSetting"
        const val XPATH_ID_GRID_CLASSIFICATION = "#classification-list-setting"
    }
}