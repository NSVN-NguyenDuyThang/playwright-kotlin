package shou.common.mobile.element.switchbtn

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.mobile.BasePageMobile
import shou.common.mobile.CommonUI
/**
 * thẻ chứa class [btn-group btn-group-toggle]
 */
class SwitchBtnElement(override var page: Page, private val switchGroupBtn: Locator) : BasePageMobile() {

    fun clickToBtnByValue(value: String?) {
        clickToElement(switchGroupBtn, String.format(CommonUI.SWITCH_BTN_OPTION_BY_VALUE, value))
    }

    fun clickToBtnByText(text: String?) {
        clickToElement(switchGroupBtn, String.format(CommonUI.SWITCH_BTN_OPTION_BY_TEXT, text))
    }
}
