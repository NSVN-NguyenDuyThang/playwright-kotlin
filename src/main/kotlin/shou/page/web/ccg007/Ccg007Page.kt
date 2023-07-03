package shou.page.web.ccg007

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.PlaywrightException
import shou.common.web.BasePage
import shou.common.web.element.checkbox.Checkbox
import shou.common.web.element.igcombo.IgCombo

class Ccg007Page() : BasePage() {

    internal fun contractLogin(contractCD: String, contractPW: String) {
        val title : String = getTextResource("CCG007_51")
        val contractDlg : FrameLocator = getFrame(title) ?: throw PlaywrightException("No such frame with $title title")
        fillToElement(contractDlg.locator(CONTRACT_CD), contractCD)
        fillToElement(contractDlg.locator(CONTRACT_PW), contractPW)
        clickToElement(contractDlg.locator(LOGIN_BTN))
    }

    internal fun companyLogin(companyCD: String, employeeCD: String, employeePW: String) {
        IgCombo(page, COMPANY_COMBOBOX).selectByCode(companyCD)
        fillToElement(String.format(EMPLOYEE_INPUT, getTextResource("CCG007_8")), employeeCD)
        fillToElement(String.format(EMPLOYEE_INPUT, getTextResource("CCG007_2")), employeePW)
        Checkbox(page, KEEP_LOGIN_CHECKBOX).check(getTextResource("CCG007_5"))
        clickToElement(LOGIN_BTN)
        page.waitForTimeout(3000.0)
    }
    companion object {
        private const val CONTRACT_CD = "#contract-code-inp"
        private const val CONTRACT_PW = "#password-input"
        private const val LOGIN_BTN = "#login-btn"
        private const val COMPANY_COMBOBOX = "//div[@class='login-form']//div[@class='ui-igcombo-wrapper ntsControl']"
        private const val EMPLOYEE_INPUT = "//td[text()='%s']/following-sibling::td//input"
        private const val KEEP_LOGIN_CHECKBOX = "//div[@class='login-form']//label[contains(@class, 'checkbox-wrapper')]"
    }

}