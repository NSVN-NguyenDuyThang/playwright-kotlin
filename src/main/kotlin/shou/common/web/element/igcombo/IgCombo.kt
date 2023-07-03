package shou.common.web.element.igcombo

import com.microsoft.playwright.Page
import shou.common.web.BasePage
import shou.utils.DataFaker

/**
 * @property comboBoxWrapper locator đến thẻ div chứa class ui-igcombo-wrapper (Click vào element này sẽ xổ ra dropdown)
 */
class IgCombo(override var page: Page, private val comboBoxWrapper: String) : BasePage() {
    /**
     * Chọn item trong combo box dựa theo code <br>
     * Sử dụng trong combo box chứa các item có thông tin code (có từ 2 thông tin trở lên, thường có code, name (title), ...)
     * @param code
     * @return name (title) của item được chọn
     */
    internal fun selectByCode(code: String): String {
        val id: String = clickToCombobox()
        clickToElement(BY_CODE, id, code)
        return page.innerText(String.format(TITLE, id, code))
    }

    /**
     * Chọn item trong combo box dựa theo code <br>
     * Sử dụng trong combo box chứa các item có thông tin code và name (nằm trong cùng 1 thẻ div)
     * @param code
     */
    internal fun selectByCodeTitle(code: String) {
        val id: String = clickToCombobox()
        clickToElement(BY_CODE, id, code)
    }

    /**
     * Chọn item trong combo box dựa theo name (title) <br>
     * Sử dụng trong các combo box chứa các item chỉ có thông tin về name (title)
     * @param title
     * @return name (title) của item được chọn
     */
    internal fun selectByTitle(title: String): String {
        val id: String = clickToCombobox()
        clickToElement(BY_TITLE, id, title)
        return title
    }

    /**
     * Chọn item trong combo box dựa theo name (title) <br>
     * Sử dụng trong các combo box chứa các item chỉ có thông tin về name (title)
     * @param pattern
     * @return name (title) của item được chọn
     */
    internal fun selectByStartTitle(pattern: String): String {
        val id: String = clickToCombobox()
        clickToElement(BY_START_TITLE, id, pattern)
        return pattern
    }
    /**
     * Lấy số lượng item trong combo box
     * @return
     */
    internal fun size(): Int {
        val id: String = clickToCombobox()
        val size: Int = page.querySelectorAll(String.format(ITEMS, id)).size
        clickToElement(comboBoxWrapper)
        return size
    }

    private fun clickToCombobox(): String {
        val igComboId: String = setAttribute(page.locator(comboBoxWrapper), "id", DataFaker.generateFakeAlphaNumeric(10))
        clickToElement(comboBoxWrapper)
        page.waitForTimeout(1000.0)
        return igComboId
    }

    companion object {
        private const val ITEMS: String = "//div[@dropdown-for='%s']//li[@data-value]"
        private const val TITLE: String = "//div[@dropdown-for='%s']//li[@data-value='%s']//div[@class='nts-combo-item']/div[2]"
        private const val BY_CODE: String = "//div[@dropdown-for='%s']//li[@data-value='%s']"
        private const val BY_TITLE: String = "//div[@dropdown-for='%s']//li[@data-value]//div[@class='nts-combo-item']/div[1][normalize-space()='%s']"
        private const val BY_START_TITLE: String = "//div[@dropdown-for='%s']//li[@data-value]//div[@class='nts-combo-item']/div[1][starts-with(text(),'%s')]"
    }

}