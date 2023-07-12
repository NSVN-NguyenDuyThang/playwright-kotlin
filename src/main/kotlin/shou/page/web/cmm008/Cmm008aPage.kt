package shou.page.web.cmm008

import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.page.web.kcp001.Kcp001Component

class Cmm008aPage() : BasePage() {
    /**
     * Đăng ký employment <br></br>
     *  * Nếu employment đã tồn tại (dựa vào code) thì sẽ chỉnh sửa các trường dữ liệu cho phép
     *  * Nếu employment chưa tồn tại thì sẽ thực hiện tạo mới
     * @param employment [Employment]
     * @param isExisted cờ thông báo employment đó đã tồn tại chưa
     * @return msgId (Msg_15)
     */
    @Step("雇用を登録する  「{0}」")
    fun registerEmployment(employment: Employment, isExisted: Boolean): String? {
        when (isExisted) {
            true -> Kcp001Component(page, EMPLOYMENT_COMPONENT).selectEmployment(employment.code)
            false -> clickToButton("CMM008_1") //create new btn
        }
        inputEmploymentInformation(employment, isExisted)
        clickToButton("CMM008_2") //register bn
        return getAndCloseMsgInfo()
    }

    /**
     * Lấy code của các employment đã tồn tại
     * @param empList
     * @return danh sách chứa mã code đã tồn tại
     */
    fun getExistedEmploymentCodeList(empList: List<String>): List<String> {
        return Kcp001Component(page, EMPLOYMENT_COMPONENT).getExistedEmploymentCodeList(empList)
    }

    @Step("情報を入力する")
    fun inputEmploymentInformation(employment: Employment, isExisted: Boolean) {
        if (!isExisted) {
            fillToElement(String.format(CommonUI.INPUT_BY_ID, "empCode"), employment.code,"コード")
        }
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "empName"), employment.name,"名称")
        if (employment.externalCode != null) {
            fillToElement(String.format(CommonUI.INPUT_BY_ID, "extCode"), employment.externalCode,"外部コード")
        }
        if (employment.memo != null) {
            fillToElement(String.format(CommonUI.TEXTAREA_BY_ID, "memo"), employment.memo,"メモ")
        }
    }

    @Step("Delete employment 「{0}」 if existing")
    fun deleteEmploymentBefore(empCode: String): String? {
        val kcp001Component = Kcp001Component(page, EMPLOYMENT_COMPONENT)
        if (!kcp001Component.isEmploymentExisted(empCode)) {
            return null
        }
        kcp001Component.selectEmployment(empCode)
        clickToButton("CMM008_4")
        messageConfirmYes("Msg_18")
        return getAndCloseMsgInfo()
    }

    companion object {
        private const val EMPLOYMENT_COMPONENT = "//div[@id='emp-component']"
    }
}