package shou.page.web.kmk012

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.datepicker.DatePicker
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.switchbox.SwitchBox
import shou.utils.DataFaker

class Kmk012Page() : BasePage() {
    private val assignClosureDlg : FrameLocator?
        get() = getFrame(getTextResource("KMK012_43"))
    @Step("締め｛0｝を選択")
    fun chooseClosure(closureNo: String) {
        clickToElement(String.format(CLOSURE_ITEM, closureNo))
        takeScreenshot("Choose closure $closureNo")
    }

    @Step("締めを使用しないに設定")
    fun useSetting(useSetting: UseSetting) {
        SwitchBox(page, SWITCH_BUTTON).select(getTextResource(useSetting.textRsId))
        takeScreenshot("締めを使用しないに設定")
    }

    @Step("処理年月を入力")
    fun inputClosureMonth(yearMonth: String) {
        DatePicker(page, CLOSURE_MONTH_TEXTBOX).input(yearMonth)
        takeScreenshot("Input closure year month $yearMonth")
    }

    @Step("締め{0}に名称を入力")
    fun inputClosureName(closureCode: String) {
        fillToElement(CLOSURE_NAME, closureCode)
        takeScreenshot("締め" + closureCode + "に名称を入力")
    }

    @Step("締め日{0}を入力")
    fun chooseClosingDayOfClosure(day: String) {
        IgCombo(page, String.format(CLOSING_DAY_COMBOBOX, getTextResource("KMK012_10"))).selectByTitle(day)
        page.waitForTimeout(1000.0)
        takeScreenshot("締め日" + day + "を入力")
    }

    @Step("締め{0}を登録")
    fun registerClosureData(closureData: ClosureData) {
        closureData.closureNo?.let { chooseClosure(it) }
        closureData.useSetting?.let { useSetting(it) }
        if (closureData.closureNo.equals("1")) {
            inputClosureMonth(DataFaker.getSystemYearMonth())
            closureData.name?.let { inputClosureName(it) }
            closureData.closingDate?.let { chooseClosingDayOfClosure(it) }
        }
        clickToButton("KMK012_1") // register btn
    }

    @Step("締め日の割付ダイアログを開く")
    fun openDialogAssignClosureForEmployment() {
        clickToButton("KMK012_43")
        takeScreenshot("締め日の割付ダイアログを開く")
    }

    @Step("雇用に締め日を割付")
    fun assignClosureForEmployment(employmentCode: String?, closureCode: String?) {
        clickToElement(assignClosureDlg?.locator(String.format(CLOSURE_NAME_COMBOBOX, employmentCode)))
        val rowIndex: String = (assignClosureDlg?.locator(String.format(CLOSURE_ITEM, employmentCode))?.getAttribute("data-row-idx")?.toInt()?.plus(1)).toString()
        clickToElement(assignClosureDlg?.locator(String.format(CLOSURE_ITEM_DROPDOWN, rowIndex, closureCode)))
        takeScreenshot("雇用に締め日を割付")
    }

    @Step("締め日の割付ダイアログを閉じる")
    fun closeDialogAssignClosureForEmployment() {
        clickToButton(assignClosureDlg!!,"KMK012_42")
    }

    fun clickToRegisterAssignButton() {
        clickToButton(assignClosureDlg!!, "KMK012_1") // register btn
    }


    fun verifyRegisterSuccess(): String {
        return getAndCloseMsgInfo()
    }

    companion object {
        const val CLOSURE_ITEM = "//tr[@data-id='%s']"
        const val SWITCH_BUTTON = "id=selUseClassification"
        const val CLOSURE_MONTH_TEXTBOX = "id=inpMonth"
        const val CLOSURE_NAME = "id=inpname"
        const val CLOSING_DAY_COMBOBOX = "//label[text()='%s']/ancestor::div[contains(@class,'label')]/following-sibling::div/div"
        const val CLOSURE_NAME_COMBOBOX = "//tr[@data-id='%s']//div[contains(@class,'ui-igcombo-wrapper')]"
        const val CLOSURE_ITEM_DROPDOWN = "//div[contains(@class,'ui-igcombo-dropdown')][%s]//li[@data-value='%s']"
    }

}