package shou.common.web.element.datepicker

import com.microsoft.playwright.Page
import shou.common.web.BasePage
/**
 * @property datePickerInput locator tới thẻ input có class ntsDatepicker nts-input
 */
class DatePicker(override var page: Page, private val datePickerInput: String) : BasePage() {
    /**
     * Nhập giá trị thời gian vào thẻ input </br>
     * <li>Chọn ngày: YYYY/MM/dd</li>
     * <li>Chọn tháng-năm: YYYY/MM</li>
     * <li>Chọn năm: YYYY</li>
     * @param value
     */
    fun input(value: String) {
        clickToElement(datePickerInput)
        fillToElement(datePickerInput, value)
        hide()
    }

    private fun hide() {
        val classVal: String = getElementAttribute(CONTAINER, "class")
        setAttribute(page.locator(CONTAINER), "class", "$classVal datepicker-hide")
    }

    companion object {
        private const val CONTAINER = "//div[@class='datepicker-container datepicker-dropdown']"
    }
}