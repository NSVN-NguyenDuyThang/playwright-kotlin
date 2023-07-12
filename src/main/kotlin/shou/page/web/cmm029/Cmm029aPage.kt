package shou.page.web.cmm029

import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.radiobutton.RadioButton
import shou.common.web.element.switchbox.SwitchBox

class Cmm029aPage() : BasePage() {
    fun registerWorkSetting(workSettingList: WorkSettingList): String {
        inputWorkSetting(workSettingList)
        clickToButton("CMM029_2") // register btn
        return getAndCloseMsgInfo()
    }

    fun registerSetting(workingSettingList: WorkSettingList, otherSetting: OtherSetting) {
        addStep("情報を入力する", Runnable { inputWorkSetting(workingSettingList)
                                                inputOtherSetting(otherSetting) })
        clickToButton("CMM029_2")
        closeMsgInfo("Msg_15")
    }

    @Step("「作業設定」tab情報を入力します。")
    fun inputWorkSetting(workingSettingList: WorkSettingList) {
        clickToTab(getTextResource("CMM029_3"))
        for (item in workingSettingList.items) {
            val title = getTextResource(item.titleTextRsId)
            val switchBox = SwitchBox(page, String.format(WORK_SETTING_SWITCH_BOX, title))
            when(item.used) {
                true -> addStep("$title: Select 利用する", Runnable { switchBox.select("利用する") })
                false -> addStep("$title: Select 利用しない", Runnable { switchBox.select("利用しない") })
            }
        }
    }

    @Step("Tab その他の設定")
    fun inputOtherSetting(otherSetting: OtherSetting) {
        clickToTab(getTextResource("CMM029_6"))
        val option = getTextResource(otherSetting.selectOptionTextRsTd)
        addStep(String.format(CHECK_TO_RADIO_BUTTON, option), Runnable { RadioButton(page, OTHER_SETTING_RADIO).check(option) })
    }

    @Step("{0} tabをクリックする")
    private fun clickToTab(tabName: String) {
        clickToElement(String.format(TAB_SETTING, tabName))
    }

    companion object {
        private const val TAB_SETTING = "//div[contains(@class, 'tabs-list')]/label[./span[text()='%s']]"
        private const val WORK_SETTING_SWITCH_BOX = "//div[@class='tab-content-1']//div[@class='setting-label' and text()='%s']/following-sibling::div"
        private const val OTHER_SETTING_RADIO = "//div[@id='A4']"
    }
}