package shou.common.element.datetimepicker

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.BasePageMobile

class DateTimePicker(page: Page, input: Locator) : BasePageMobile() {
    private val input: Locator

    init {
        setPage(page)
        this.input = input
    }

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
