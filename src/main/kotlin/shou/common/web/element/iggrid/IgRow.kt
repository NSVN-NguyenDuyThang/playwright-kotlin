package shou.common.web.element.iggrid

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage

class IgRow(override var page: Page, var row: Locator) : BasePage(), ElementAction {

    fun getTextByCellName(indexCell: Int): String {
        return findCellElementByCellIndex(indexCell).innerText()
    }

    fun findCellElementByCellIndex(indexCell: Int): Locator {
        return row.locator(String.format(FIND_TD_BY_INDEX, indexCell))
    }

    fun findCellElementByValue(value: String): Locator {
        return row.locator(String.format(FIND_TD_BY_VALUE, value))
    }

    override fun click() {
       clickToElement(row)
    }

    override fun doubleClick() {
        doubleClickToElement(row)
    }

    fun check() {
        val checkboxElement: Locator = row.locator(CHECKBOX)
        val checkboxStatus: String = checkboxElement.getAttribute("data-chk")
        if ("on" != checkboxStatus) {
            clickToElement(checkboxElement)
        } else {
          doubleClickToElement(checkboxElement)
        }
    }

    fun uncheck() {
        val checkboxElement: Locator = row.locator(CHECKBOX)
        val checkboxStatus: String = checkboxElement.getAttribute("data-chk")
        if ("on" == checkboxStatus) {
            clickToElement(checkboxElement)
        } else {
            doubleClickToElement(checkboxElement)
        }
    }

    override fun fill(locator: Locator, key: String) {
        fillToElement(locator, key)
    }

    companion object {
        const val FIND_TD_BY_INDEX = "//td[%s]"
        const val FIND_TD_BY_VALUE = "//td[text()='%s']"
        const val CHECKBOX = "//span[@name='chk']"
    }
}