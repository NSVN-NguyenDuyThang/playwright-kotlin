package shou.common.web.element.checkbox

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage

/**
 * @property checkboxWrapper locator đến thẻ chứa class <b>checkbox-wrapper</b> <i>(không phải checkbox-wrappers)</i> nts-input <br>
 *                              locator này có thể matching với nhiều checkbox. Không cần thông tin label
 *
 */
class Checkbox(override var page: Page, private val checkboxWrapper: String) : BasePage() {

    private var frame : FrameLocator? = null
    private var wrapperLocator : Locator? = null
        get() = (frame?.locator(checkboxWrapper) ?: page.locator(checkboxWrapper))
    constructor(page: Page, checkboxWrapper: String, frame: FrameLocator) : this(page, checkboxWrapper) {
        this.frame = frame
    }
    internal fun check(label: String) {
        val targetCheckbox: Locator = wrapperLocator?.filter(Locator.FilterOptions().setHas(frame?.getByText(label) ?: page.getByText(label))) ?: throw NoSuchElementException(String.format("Could not find check box with label [%s]", label))
        val checkboxLabel: Locator? = targetCheckbox.locator(LABEL)
        val checkboxInput: Locator? = targetCheckbox.locator(INPUT)
        highlightElement(targetCheckbox)
        if (checkboxInput?.isChecked == false) {
            checkboxLabel?.click()
        }
    }

    internal fun uncheck(label: String) {
        val targetCheckbox: Locator = wrapperLocator?.filter(Locator.FilterOptions().setHas(frame?.getByText(label) ?: page.getByText(label))) ?: throw NoSuchElementException(String.format("Could not find check box with label [%s]", label))
        val checkboxLabel: Locator? = targetCheckbox.locator(LABEL)
        val checkboxInput: Locator? = targetCheckbox.locator(INPUT)
        highlightElement(targetCheckbox)
        if (checkboxInput?.isChecked == true) {
            checkboxLabel?.click()
        }
    }

    companion object {
        private const val LABEL = "//input/following-sibling::span"
        private const val INPUT = "//input"
    }


}