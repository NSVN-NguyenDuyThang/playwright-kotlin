package shou.page.web.ccg001

import shou.common.web.BasePage
import shou.common.web.CommonUI
import java.util.*


class Ccg001Component() : BasePage() {

    /**
     * Hiển thị component tìm kiếm nhân viên bằng cách click vào button ccg001-btn-search-drawer
     */
    fun openSearchEmployeeComponent() {
        clickToElement(SEARCH_DRAWER)
    }

    /**
     * Chọn thông tin nhân viên hiện tại đang login ở tab クイック検索
     */
    fun selectCurrentLoginEmployee() {
        clickToElement(CURRENT_LOGIN_EMPLOYEE)
    }

    /**
     * Chọn tất cả nhân viên ở tab クイック検索
     */
    fun selectAllEmployees() {
        clickToElement(ALL_EMPLOYEE)
    }

    /**
     * Chọn tất cả nhân viên cùng workplace ở tab クイック検索
     */
    fun selectBySameWorkplace() {
        clickToElement(SAME_WORKPLACE)
    }

    /**
     * Chọn thông tin nhân viên
     * @param employeeCode
     */
    fun selectTargetEmployee(employeeCode: String?) {
        searchEmployeeMatchingCode(employeeCode)
        selectEmployee(listOf(employeeCode))
    }

    /**
     * Tìm kiếm nhân viên matching
     * @param code
     */
    fun searchEmployeeMatchingCode(code: String?) {
        clickToElement(String.format(TAB_BY_NAME, getTextResource("CCG001_103")))
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "ccg001-input-code"), code)
        clickToElement(String.format(SEARCH_BTN_BY, "code"))
    }

    /**
     * Chọn nhân viên trong bảng (sau khi tìm kiếm []
     * @param employeeCodeList danh sách mã nhân viên cần chọn
     */
    fun selectEmployee(employeeCodeList: List<String?>) {
        if ("on" == getElementAttribute(CHECK_ALL, "data-chk")) {
            clickToElement(CHECK_ALL)
        }
        for (code in employeeCodeList) {
            clickToElement(String.format(ROW_ITEM_BY_CODE, code))
        }
        clickToElement(DECISION_BTN)
    }

    companion object {
        const val SEARCH_DRAWER = "xpath=//div[@id='ccg001-btn-search-drawer']"
        const val CURRENT_LOGIN_EMPLOYEE = "xpath=//div[@id='ccg001-btn-only-me']/button"
        const val ALL_EMPLOYEE = "xpath=//div[@id='ccg001-btn-search-all']/button"
        const val SAME_WORKPLACE = "xpath=//div[@id='ccg001-btn-same-workplace']/button"
        const val TAB_BY_NAME = "xpath=//div[@id='tab-panel']//span[text()='%s']"
        const val SEARCH_BTN_BY = "xpath=//button[@id='ccg001-tab3-search-by-%s']"
        const val CHECK_ALL =
            "xpath=//div[@id='ccg001-tab-content-3']//span[@name='hchk' and @data-role='checkbox' and (@data-chk='on' or @data-chk='off')]"
        const val ROW_ITEM_BY_CODE =
            "xpath=//div[@id='tab3kcp005']//tr[@data-id='%s']//span[@data-chk='on' or @data-chk='off']"
        const val DECISION_BTN = "xpath=//div[@id='ccg001-tab-content-3']//button[@id='ccg001-btn-KCP005-apply']"
    }
}