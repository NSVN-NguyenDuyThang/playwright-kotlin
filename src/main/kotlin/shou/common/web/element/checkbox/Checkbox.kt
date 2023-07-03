package shou.common.web.element.checkbox

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage

/**
 * @property checkboxWrapper locator đến thẻ chứa class <b>checkbox-wrapper</b> <i>(không phải checkbox-wrappers)</i> nts-input <br>
 *                              locator này có thể matching với nhiều checkbox. Không cần thông tin label
 *
 */
class Checkbox(override var page: Page, private val checkboxWrapper: String) : BasePage() {

    internal fun check(label: String) {
        val targetCheckbox: Locator = page.locator(checkboxWrapper).filter(Locator.FilterOptions().setHas(page.getByText(label))) ?: throw NoSuchElementException(String.format("Could not find check box with label [%s]", label))
        val checkboxLabel: Locator? = targetCheckbox.locator("//span[text()]")
        val checkboxInput: Locator? = targetCheckbox.locator("//input")
        highlightElement(targetCheckbox)
        if (checkboxInput?.isChecked == false) {
            checkboxLabel?.click()
        }
    }

    internal fun uncheck(label: String) {
        val targetCheckbox: Locator = page.locator(checkboxWrapper).filter(Locator.FilterOptions().setHas(page.getByText(label))) ?: throw NoSuchElementException(String.format("Could not find check box with label [%s]", label))
        val checkboxLabel: Locator? = targetCheckbox.locator("//span[text()]")
        val checkboxInput: Locator? = targetCheckbox.locator("//input")
        highlightElement(targetCheckbox)
        if (checkboxInput?.isChecked == true) {
            checkboxLabel?.click()
        }
    }

}