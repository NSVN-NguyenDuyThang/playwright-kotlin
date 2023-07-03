package shou.common.element.switchbtn

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.BasePageMobile
import shou.common.CommonUI

class SwitchBtnElement(page: Page, switchGroupBtn: Locator) : BasePageMobile() {
    /**
     * thẻ chứa class [btn-group btn-group-toggle]
     */
    private val switchGroupBtn: Locator

    init {
        setPage(page)
        this.switchGroupBtn = switchGroupBtn
    }

    fun clickToBtnByValue(value: String?) {
        clickToElement(switchGroupBtn, String.format(CommonUI.SWITCH_BTN_OPTION_BY_VALUE, value))
    }

    fun clickToBtnByText(text: String?) {
        clickToElement(switchGroupBtn, String.format(CommonUI.SWITCH_BTN_OPTION_BY_TEXT, text))
    }
}
