package shou.common.web.element.iggrid

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage
import java.util.*
import java.util.stream.Collectors

class IgGrid(override var page: Page, private val gridWrapper: String) : BasePage() {
    private var frame : FrameLocator? = null
    private val grid: Locator
        get() = frame?.locator(gridWrapper) ?: page.locator(gridWrapper)

    constructor(page: Page, gridWrapper: String, frameLocator: FrameLocator) : this(page, gridWrapper) {
        this.frame = frameLocator
    }

    fun getRow(cellName: String, value: String): IgRow {
        val elements: Locator? = finds(cellName, listOf(value))
        elements?.let {
            return IgRow(page, elements)
        }
        throw NoSuchElementException("Row with cell name '$cellName' and cell value '$value' not found.")
    }

    private fun finds(cellName: String, values: List<String>): Locator {
        val conditions = values.stream()
            .map { "(normalize-space() = '$it')" }
            .collect(Collectors.joining(" or "))
        val selector: String = String.format(ROW, conditions, cellName)
        return grid.locator(selector)
    }

    fun selectRowByCellName(cellName: String, value: String) {
        getRow(cellName, value).click()
    }

    fun checkAll() {
        val checkboxElement: Locator = grid.locator(CHECKBOX)
        val checkboxStatus: String = checkboxElement.getAttribute("data-chk")
        if ("on" != checkboxStatus) {
            clickToElement(checkboxElement)
        } else {
            doubleClickToElement(checkboxElement)
        }
    }

    fun indexColumn(columnName: String): Int {
        val headerCells: Locator = grid.locator(FIND_TD)
        var columnIndex = -1
        for (i in headerCells.all().indices) {
            if (headerCells.nth(i).innerText().equals(columnName)) {
                columnIndex = i + 1 // XPath index starts from 1
                break
            }
        }
        return columnIndex
    }

    fun uncheckAll() {
        val checkboxElement: Locator = grid.locator(CHECKBOX)
        val checkboxStatus: String = checkboxElement.getAttribute("data-chk")
        if ("on" == checkboxStatus) {
            clickToElement(checkboxElement)
        } else {
            doubleClickToElement(checkboxElement)
        }
    }

    /*
    * Không hiển thị chi tiết từng step*/
    fun checkToRowsByCellName(cellName: String, cellValues: List<String>) {
        finds(cellName, cellValues).all().map { IgRow(page, it) }.map { it.check() }
    }

    /*
     * Không hiển thị chi tiết từng step*/
    fun uncheckRowsByCellName(cellName: String, cellValues: List<String>){
        finds(cellName, cellValues).all().map { IgRow(page, it) }.map { it.uncheck() }
    }

    fun isExistsRow(cellName: String, value: String): Boolean? {
        return !finds(cellName, listOf(value)).all().isEmpty()
    }

    companion object {
        const val ROW = "//tr[@data-id]//td[%s]/ancestor::tr[@data-id]//td[count(//th[text()='%s']/preceding-sibling::th)+1]/ancestor::tr[@data-id]"
        const val CHECKBOX = "//th[contains(@class, 'ui-iggrid-header')]//span[@data-role='checkbox']"
        const val FIND_TD = "//thead//tr//th[@aria-label]"
    }
}