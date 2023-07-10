package shou.page.web.cas014

import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.igcombo.IgCombo

class Cas014Page() : BasePage() {
    fun clickButtonSave() {
        clickToButton(TEXTRESORCE_ID_BUTTON_SAVE)
    }

    fun getMessageResult(): String {
        return getAndCloseMsgInfo()
    }

    fun changeComboBoxInGrid(items: List<PositionItem>) {
        items.forEach{ item: PositionItem -> selectComboBox(item) }
    }

    @Step("{0}")
    private fun selectComboBox(item: PositionItem) {
        IgCombo(page, String.format(XPATH_COMBOBOX_GRID, item.code)).selectByCode(item.value)
        takeScreenshot(item.toString())
    }

    companion object {
        const val TEXTRESORCE_ID_BUTTON_SAVE = "CAS014_11"
        const val XPATH_COMBOBOX_GRID = "//table//tbody//tr//td[@data-bind='text: jobTitle.code' and text()='%s']//following-sibling::td[last()]/div"
    }
}