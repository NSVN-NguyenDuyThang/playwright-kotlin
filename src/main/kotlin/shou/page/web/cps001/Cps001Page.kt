package shou.page.web.cps001

import io.qameta.allure.Step
import shou.browser.PageFactory
import shou.common.web.BasePage
import shou.common.web.element.igcombo.IgCombo
import shou.common.web.element.iggrid.IgGrid
import shou.page.web.ccg001.Ccg001Component
import java.util.*

class Cps001Page() : BasePage() {

    fun checkExistEmployee(employees: List<Employee>): Employee? {
        val ccg001Component: Ccg001Component = PageFactory.createInstance(page, Ccg001Component::class.java)
        ccg001Component.openSearchEmployeeComponent()
        ccg001Component.selectAllEmployees()
        val igGridElement = IgGrid(page, TABLE)
        val check = employees.map {igGridElement.isExistsRow(getTextResource("CCG029_A1_22"), it.code)!!}.all { true }
        val employeeSelect: Employee = employees.first { it.select == "1" }
        if (check) {
            igGridElement.selectRowByCellName(getTextResource("CCG029_A1_22"), employeeSelect.code)
            return employeeSelect
        }
        return null
    }

    fun settingCategory(categorySetting: CategorySetting): String {
        val employee: Employee? = checkExistEmployee(categorySetting.employees!!)
        employee?.let {
            selectEmployee(employee.code)
            selectCategory(categorySetting.name)
            if (page.locator(String.format(INPUT_STRING, "IS00366")).isEnabled) {
                typeToElement(String.format(INPUT_STRING, "IS00366"), categorySetting.settingList?.get(0)?.value, categorySetting.settingList?.get(0)?.title)
            }
            if (page.locator(String.format(INPUT_STRING, "IS00369")).isEnabled) {
                typeToElement(String.format(INPUT_STRING, "IS00369"), categorySetting.settingList?.get(1)?.value, categorySetting.settingList?.get(1)?.title)
            }
            clickToButton("CPS001_4")
            return getAndCloseMsgInfo()
        }
        return "従業員が存在しない"
    }


    @Step("社員一覧 で社員 {0} を選択")
    private fun selectEmployee(code: String) {
        takeScreenshot("社員一覧 で社員 " + code + "を選択")
    }

    @Step("カテゴリ「{0}」を選択")
    private fun selectCategory(name: String) {
        IgCombo(page, COMBOBOX).selectByTitle(name)
        takeScreenshot("カテゴリ「$name」を選択")
    }

    companion object{
        const val COMBOBOX = "//div[@id='lefttabs']//div[@class='ui-igcombo-wrapper ntsControl']"
        const val INPUT_STRING = "//input[@data-code='%s']"
        const val TABLE = "//div[@class='bg-green caret-right caret-background']/div"
    }

}