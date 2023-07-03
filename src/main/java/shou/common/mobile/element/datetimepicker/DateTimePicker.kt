package shou.common.mobile.element.datetimepicker

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.mobile.BasePageMobile

class DateTimePicker(override var page: Page, private val input: Locator) : BasePageMobile() {

    fun inputValue(value: String?) {
        removeReadonlyAttr()
        clickToElement(input)
        hiddenPicker()
        fillToElement(input, value)
    }

    private fun removeReadonlyAttr() {
        removeAttribute(input, "readonly")
    }

    private fun hiddenPicker() {
        val displayedPicker = page?.locator("//div[@class='modal show']")
        displayedPicker?.evaluate("element => element.setAttribute('class', 'modal')")
    }
}
