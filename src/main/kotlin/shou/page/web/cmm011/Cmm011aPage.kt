package shou.page.web.cmm011

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.PlaywrightException
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.datepicker.DatePicker
import shou.common.web.element.igtree.IgTree
import shou.common.web.element.radiobutton.RadioButton
import shou.utils.DataFaker
import java.text.ParseException
import java.text.SimpleDateFormat
class Cmm011aPage() : BasePage() {

    private var addHistoryDlg: FrameLocator? = null
        get() = getFrame(getTextResource("CMM011_202")) //'職場構成' dialog

    private var addWorkplaceDlg: FrameLocator? = null
        get() = getFrame(getTextResource("CMM011_203"))
    /**
     * Khởi động màn hình <br>
     * <li>Nếu chưa tồn tại khoảng lịch sử nào hoặc tất cả khoảng lịch sử trở lên có thời gian bắt đầu sau system date (hiện dialog Thêm lịch sử) -> return {@link StartScreenStatus.HISTORY_DIALOG_DISPLAY}</li>
     * <li>Ngược lại: Xử lý đóng msg, dialog nếu khoảng lịch sử không có workplace nào -> return {@link StartScreenStatus.MSG_373_DIALOG}</li>
     * @return
     */
    internal fun handleStartScreen(): StartScreenStatus {
        return try {
            waitForElementVisible(String.format(CommonUI.IFRAME, getTextResource("CMM011_202")), 10000.0)
            StartScreenStatus.HISTORY_DIALOG_DISPLAY
            } catch (e : PlaywrightException) {
                println("Have at least 1 History")
                try {
                    waitForElementVisible(String.format(CommonUI.MSG_ID, "Msg_373"), 5000.0)
                    closeMsgInfo("Msg_373")
                    val addWorkplaceDlg = getFrame(getTextResource("CMM011_203")) // '職場追加'
                    clickToButton(addWorkplaceDlg, "CMM011_128") //キャンセル
                } catch (e : PlaywrightException) {
                    println("Do not display message 373")
                }
                StartScreenStatus.MSG_373_DIALOG
            }
    }
    /**
     * Thêm khoảng lịch sử mới <br>
     * <li>0. Nếu screenStatus = {@link StartScreenStatus.MSG_373_DIALOG}, thì cần nhấp chọn button config lịch sử</li>
     * <li>1. Xử lý thêm khoảng lịch sử</li>
     * <li>2. Thêm workplace đầu tiên</li>
     * @param date khoảng lịch sử <i>yyyy/MM/dd</i>
     * @param firstWorkplace workplace đầu tiên sau khi tạo khoảng lịch sử mới
     * @param screenStatus
     * @return danh sánh msgId <br> <li>1. Msg_373</li> <li>2. Msg_15</li>
     */
    @Step("職場履歴期間の追加 「{0} - 9999/12/31」")
    internal fun addHistory(date: String, firstWorkplace: Workplace, screenStatus: StartScreenStatus): List<String> {
        if (screenStatus == StartScreenStatus.MSG_373_DIALOG) {
            clickToButton("CMM011_107")
        }
        return arrayListOf(handleAddHistoryOnCmm011bDialog(date, screenStatus), addFirstWorkplace(firstWorkplace))
    }
    /**
     * Xử lý thêm lịch sử trên dialog Cmm011a
     * @param date khoảng lịch sử
     * @param screenStatus
     * @return msgId (Msg_373)
     */
    @Step("履歴期間の追加を実行する「{0} - 9999/12/31」")
    private fun handleAddHistoryOnCmm011bDialog(date: String, screenStatus: StartScreenStatus) : String {
        if (screenStatus == StartScreenStatus.MSG_373_DIALOG
            || (screenStatus == StartScreenStatus.HISTORY_DIALOG_DISPLAY && addHistoryDlg!!.locator(PERIOD_DATE_ROWS).count() > 0)) {
            removeHistoryAfterTargetHistory(date)
            clickToButton(addHistoryDlg!!, "CMM011_117")
        }
        inputHistoryDate(date)
        clickToButton(addHistoryDlg!!, "CMM011_127")
        return getAndCloseMsgInfo()
    }
    /**
     * Xóa các khoảng lịch sử sau khoảng lịch sử cần tạo <br>
     * <li>1. Tìm các khoảng lịch sử sau khoảng lịch sử cần tạo</li>
     * <li>2. Xóa các khoảng lịch sử đó</li>
     * @param targetHistory
     */
    @Step(" 「{1}」より大きい履歴を削除")
    private fun removeHistoryAfterTargetHistory(targetHistory: String) {
        val historyRows: Locator = addHistoryDlg!!.locator(PERIOD_DATE_ROWS)
        var historyValues = mutableListOf<String>()
        page.waitForTimeout(1000.0)
        for (idx in 0 until historyRows.count()) {
            historyValues.add(historyRows.nth(idx).innerText())
        }
        historyValues = historyValues.filter { equalOrAfterTargetHistory(targetHistory, it.substring(0..9)) }.toMutableList()
        for (idx in 0 until historyValues.size) {
            if ((addHistoryDlg!!.locator(PERIOD_DATE_ROWS).count() == 1) && (idx == historyValues.size - 1)) {
                editHistory(DataFaker.addDay("yyyy/MM/dd", targetHistory, -1))
                clickToButton("CMM011_107")
                page.waitForTimeout(1000.0)
                break
            }
            val row = "${historyValues[idx].substring(0..12)}9999/12/31"
            removeHistory(row)
        }
    }
    /**
     * Kiểm tra @param history có bằng hoặc sau @param targetHistory không?
     * @param targetHistory
     * @param history
     * @return
     */
    private fun equalOrAfterTargetHistory(targetHistory: String, history: String): Boolean {
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        try {
            return !formatter.parse(history).before(formatter.parse(targetHistory))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }
    /**
     * Xóa khoảng lịch sử
     * @param hst
     */
    @Step("履歴を削除 「{1}」")
    private fun removeHistory(history: String) {
        clickToHistoryRow(addHistoryDlg!!, history)
        clickToButton(addHistoryDlg!!, "CMM011_119")
        messageConfirmYes("Msg_18")
    }
    /**
     * Click vào dòng chứa khoảng lịch sử
     * @param hst
     */
    @Step("履歴を選択 「{1}」")
    private fun clickToHistoryRow(frame: FrameLocator, history: String) {
        clickToElement(frame.locator(String.format(PERIOD_DATE_BY_TEXT, history)))
    }
    /**
     * Chỉnh sửa khoảng lịch sử
     * @param newHst
     */
    @Step("歴史の改変  「{1}」")
    private fun editHistory(newHst: String) {
        clickToButton(addHistoryDlg!!, "CMM011_118")
        inputHistoryDate(newHst)
        clickToButton(addHistoryDlg!!, "CMM011_127")
    }
    @Step("履歴の開始日を入力 「{1}」")
    private fun inputHistoryDate(hstDate: String) {
        DatePicker(page, String.format(CommonUI.INPUT_BY_ID, "B3_5"), addHistoryDlg).input(hstDate)
        takeScreenshot("履歴の開始日を入力 「$hstDate」")
    }
    /**
     * Thêm workplace đầu tiên (sau khi thêm khoảng lịch sử mới)
     * @param workplace
     * @return msgId (Msg_15)
     */
    @Step("先頭の職場を追加 「{0}」")
    private fun addFirstWorkplace(workplace: Workplace): String {
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "code")), workplace.code, "コード")
        clickToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "name")))
        handleIfDuplicateCode()
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "name")), workplace.name, "名称")
        pressKeyToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "name")), "Enter")
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "displayName")), workplace.displayName, "表示名")
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "genericName")), workplace.genericName, "総称")
        clickToButton(addWorkplaceDlg!!, "CMM011_102")
        return getAndCloseMsgInfo()
    }
    private fun handleIfDuplicateCode() {
        try {
            waitForElementVisible(String.format(CommonUI.IFRAME, getTextResource("CMM011_101")), 10000.0)
            val duplicateDlg : FrameLocator = getFrame(getTextResource("CMM011_101"))
            RadioButton(page, ACTIONS_RADIO, duplicateDlg).check(getTextResource(ActionWhenDuplicateCode.REGISTER_AS_A_DIFFERENT_WORKPLACE.textRsId))
            clickToElement(duplicateDlg.locator(String.format(CommonUI.BUTTON, getTextResource("CMM011_127"))))
        } catch (e : PlaywrightException) {
            println("Do not handle duplicate code");
        }
    }
    /**
     * Thêm workplace
     * @param workplace
     * @return msgId sau khi đăng ký (Msg_15)
     */
    @Step("職場の追加 「{0}」")
    internal fun addWorkplace(workplace: Workplace): String {
        selectWorkplace(workplace.baseOnWorkplaceCode!!)
        clickToButton("CMM011_204")
        inputWorkplaceData(workplace)
        clickToButton(addWorkplaceDlg!!, "CMM011_102")
        return getAndCloseMsgInfo()
    }
    /**
     * Chọn workplace
     * @param code
     */
    @Step("職場を選択 「{0}」")
    private fun selectWorkplace(code: String) {
        IgTree(page, WORKPLACE_ITEMS).selectByCode(code)
        takeScreenshot("職場を選択 「$code」")
    }
    /**
     * Nhập thông tin workplace
     * @param workplace
     * @return msgId (Msg_15)
     */
    @Step("職場のデータを入力 「{0}」")
    private fun inputWorkplaceData(workplace: Workplace) {
        checkToPlaceOfCreation(getTextResource(workplace.placeOfCreation?.textRsId))
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "code")), workplace.code, "コード")
        clickToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "name")))
        handleIfDuplicateCode()
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "name")), workplace.name, "名称")
        pressKeyToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "name")), "Enter")
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "displayName")), workplace.displayName, "表示名")
        fillToElement(addWorkplaceDlg!!.locator(String.format(CommonUI.INPUT_BY_VALUE_NAME, "genericName")), workplace.genericName, "総称")
    }
    /**
     * Chọn vị trí tạo workplace so với [Workplace.baseOnWorkplaceCode]
     * @param text
     */
    @Step("作成位置を選択 「{0}」")
    private fun checkToPlaceOfCreation(text: String) {
        RadioButton(page, PLACE_OF_CREATION, addWorkplaceDlg!!).check(text)
    }

    companion object {
        //CMM011A
        private const val WORKPLACE_ITEMS = "//div[@id='A4_1']//ul"
        //CMM011B
        private const val PERIOD_DATE_ROWS = "//table[@id='B1_4_grid']//tr[@data-id]/td"
        private const val PERIOD_DATE_BY_TEXT = "//table[@id='B1_4_grid']//tr[@data-id]/td[text()='%s']"
        //CMM011C
        private const val ACTIONS_RADIO = "//div[@id='C2_1']"
        //CMM011D
        private const val PLACE_OF_CREATION = "#D2_1"
    }
}