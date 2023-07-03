package shou.page.mobile.ccg

import com.microsoft.playwright.options.LoadState
import shou.common.BasePageMobile

class Ccg007Page : BasePageMobile() {
    fun inputContract(contractCode: String?, contractPW: String?) {
        fillToElement("#contractCode >> input", contractCode)
        fillToElement("#password >> input", contractPW)
        clickToElement("//button[text()='認証']")
    }

    fun inputCompany(companyCode: String?, employeeCode: String?, employeePW: String?) {
        selectOptionByValue("#companyCode >> select", companyCode)
        fillToElement("#employeeCode >> input", employeeCode)
        fillToElement("#password >> input", employeePW)
        clickToElement("//button[text()=' ログイン']")
        page.waitForLoadState(LoadState.DOMCONTENTLOADED)
    }
}
