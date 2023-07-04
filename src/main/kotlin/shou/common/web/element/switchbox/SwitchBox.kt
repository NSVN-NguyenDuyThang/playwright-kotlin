package shou.common.web.element.switchbox

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage

/**
 * @property switchBoxWrapper locator đến thẻ div chứa class switchbox-wrappers
 */
class SwitchBox(override var page: Page, private val switchBoxWrapper: String) : BasePage() {

    private var frame : FrameLocator? = null
    private var wrapperLocator: Locator? = null
        get() = frame?.locator(switchBoxWrapper) ?: page.locator(switchBoxWrapper)
    constructor(page: Page, switchBoxWrapper: String, frameLocator: FrameLocator) : this(page, switchBoxWrapper) {
        this.frame = frameLocator
    }
     fun select(text: String) {
        clickToElement(wrapperLocator?.locator(String.format(OPTION, text)))
    }

    fun getSelectedBtn(): String? {
        return wrapperLocator?.locator(SELECTED_OPTION)?.innerText()
    }

    companion object {
        private const val OPTION = "//label[.//span[text()='%s']]"
        private const val SELECTED_OPTION = "//label[./input[@checked='checked']]/span"
    }
}