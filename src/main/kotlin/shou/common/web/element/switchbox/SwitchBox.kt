package shou.common.web.element.switchbox

import com.microsoft.playwright.Page
import shou.common.web.BasePage

/**
 * @property switchBoxWrapper locator đến thẻ div chứa class switchbox-wrappers
 */
class SwitchBox(override var page: Page, private val switchBoxWrapper: String) : BasePage() {

    fun select(text: String) {
        clickToElement(page.locator(switchBoxWrapper).locator(String.format(OPTION, text)))
    }

    fun getSelectedBtn(): String {
        return page.locator(switchBoxWrapper).locator(SELECTED_OPTION).innerText()
    }

    companion object {
        private const val OPTION = "//label[.//span[text()='%s']]"
        private const val SELECTED_OPTION = "//label[./input[@checked='checked']]/span"
    }
}