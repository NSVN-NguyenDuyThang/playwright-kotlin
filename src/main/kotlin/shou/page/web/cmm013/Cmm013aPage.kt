package shou.page.web.cmm013

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.PlaywrightException
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.checkbox.Checkbox
import shou.page.web.kcp003.Kcp003Component

class Cmm013aPage() : BasePage() {

    private var restoreDlg : FrameLocator? = null
        get() = getVisibleFrame() // CMM013I dialog (dialog này không có title -> switch vào dialog đang được hiển thị
    /**
     * Đăng ký position (job title) <br></br>
     *  * Nếu position đã tồn tại, chỉnh sửa các thông tin trên position đó (không thể sửa đỏi [code]
     *  * Nếu position chưa tồn tại, thêm mới position
     * @param position [Position]
     * @param isExisted cờ trạng thái position này đã tồn tại chưa (dựa theo code)
     * @return msgId (Msg_15)
     */
    @Step("位置の登録 「{0}」")
    fun registerPosition(position: Position, isExisted: Boolean): String {
        when (isExisted) {
            true -> addStep("Select position 「${position.code}}」", Runnable { Kcp003Component(page, POSITION_TABLE_CONTAINER).selectPosition(position.code) })
            else -> clickToButton("CMM013_1") // create new button
        }
        inputPositionInformation(position, isExisted)
        clickToButton("CMM013_2") // register btn
        return getAndCloseMsgInfo()
    }

    @Step("位置を入力  「{0}」")
    private fun inputPositionInformation(position: Position, isExisted: Boolean) {
        if (!isExisted) {
            fillToElement(String.format(CommonUI.INPUT_BY_ID, "job-title-code"), position.code, "コード")
        }
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "job-title-name"), position.name, "名称")
        apply36Agreement(position.apply36Agreement)
        if (position.orderCode != null) {
            selectDisplayOrder(position.orderCode)
        }
    }

    @Step("36協定チェックの対象外: 「{0}」")
    private fun apply36Agreement(isChecked: Boolean) {
        val apply36Agreement = Checkbox(page, APPLY_36AGREEMENT_CHECKBOX)
        when (isChecked) {
            true -> apply36Agreement.check(getTextResource("CMM013_52"))
            false -> apply36Agreement.uncheck(getTextResource("CMM013_52"))
        }
    }

    /**
     * Chọn cách hiển thị<br></br>
     *  * Nếu orderCode = "", chọn option [選択なし]
     * @param orderCode
     */
    @Step("序列の選択: 「{0}」")
    private fun selectDisplayOrder(orderCode: String) {
        clickToButton("CMM013_15")
        val displayOrderDlg = getFrame(getTextResource("CMM013_55"))
        addStep("Select code 「$orderCode」", Runnable { clickToElement(displayOrderDlg.locator(String.format(SELECT_ORDER_ITEM, orderCode)))})
        clickToButton(displayOrderDlg,"CMM013_25") // Decision btn
    }

    /**
     * Tìm các position trong [pstList] đã tồn tại
     * @param pstList
     * @return danh sách position code đã tồn tại
     */
    fun getExistedPosition(pstList: List<String>): List<String> {
        restorePositionIfExisted(pstList)
        page.waitForTimeout(1000.0)
        return Kcp003Component(page, POSITION_TABLE_CONTAINER).getExistedPosition(pstList)
    }

    /**
     * Restore các position trong [pstList] đã bị ẩn đi trước đó
     * @param pstList
     */
    @Step("Restore position if needed")
    private fun restorePositionIfExisted(pstList: List<String>) {
        clickToButton(getTextResource("CMM013_89")) // restore btn
        try {
            waitForElementVisible(String.format(CommonUI.MSG_ID, "Msg_3517"), 5000.0)
            closeMsgInfo("Msg_3517")
        } catch (e: PlaywrightException) {
            println("Do not display message Msg_3517")
        }
        Kcp003Component(page, POSITION_TABLE_CONTAINER_I, restoreDlg!!).getExistedPosition(pstList)
                .forEach {
                    addStep("Select position 「$it」", Runnable { Kcp003Component(page, POSITION_TABLE_CONTAINER_I, restoreDlg!!).selectPosition(it)})
                    clickToButton(restoreDlg!!, getTextResource("CMM013_87")) //execution btn
                    closeMsgInfo("Msg_3515")
                }
        clickToButton(restoreDlg!!, getTextResource("CMM013_88")) // close btn
    }

    companion object {
        //CMM013A
        private const val POSITION_TABLE_CONTAINER = "//div[@id='job-title-items-list']"
        private const val APPLY_36AGREEMENT_CHECKBOX = "//div[contains(@class, 'checkbox-wrapper')]"
        //CMM013C
        private const val SELECT_ORDER_ITEM = "//table[contains(@data-bind,'sequenceCode')]//tr[@data-id='%s']"
        //CMM013I
        private const val POSITION_TABLE_CONTAINER_I = "//div[@id='single-list_container']"
    }
}