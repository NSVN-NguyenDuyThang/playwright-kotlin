package shou.common.web.element.radiobutton

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage
/**
 * @property radioWrapperGroup locator đến thẻ chứa class checkbox-group radio-wrappers nts-input<br>
 * Thẻ này chứa nhiều thẻ có chứa class radio-wrapper nts-input <br>
 * 	< div class='checkbox-group radio-wrappers nts-input'>
 * 		<label class='with-text radio-wrapper nts-input'></label><br>
 * 		....<br>
 * < /div>
 */
class RadioButton(override var page: Page, private val radioWrapperGroup: String) : BasePage() {
    private var frame: FrameLocator? = null
    private var wrapperLocator: Locator? = null
        get() = (frame?.locator(radioWrapperGroup) ?: page.locator(radioWrapperGroup))

    constructor(page: Page, radioWrapperGroup: String, frame: FrameLocator) : this(page, radioWrapperGroup) {
        this.frame = frame
    }
    fun check(label: String) {
        val input : Locator? = wrapperLocator?.locator(String.format(INPUT, label))
        highlightElement(input)
        if (input?.isChecked == false) {
            wrapperLocator?.locator(String.format(LABEL, label))?.click()
        }
    }

    fun uncheck(label: String) {
        val input : Locator? = wrapperLocator?.locator(String.format(INPUT, label))
        highlightElement(input)
        if (input?.isChecked == true) {
            wrapperLocator?.locator(String.format(LABEL, label))?.click()
        }
    }

    companion object {
        private const val LABEL = "//span[text()='%s']"
        private const val INPUT = "//span[text()='%s']/preceding-sibling::input"
    }
}