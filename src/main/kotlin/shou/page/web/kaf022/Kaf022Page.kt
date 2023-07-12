package shou.page.web.kaf022

import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.element.radiobutton.RadioButton

class Kaf022Page() : BasePage() {

    fun settingApprove(approve: Approve): String? {
        clickToButton("KAF022_664")
        selectRadio(getTextResource(approve.textRecourseId))
        clickToButton("KAF022_1") //register btn
        return getAndCloseMsgInfo()
    }

    @Step("「共通」タブで「本人による承認」に「{0}」をチェック")
    private fun selectRadio(approve: String) {
        scrollToElement(RADIO)
        RadioButton(page, RADIO).check(approve)
        takeScreenshot("「共通」タブで「本人による承認」に「$approve」をチェック")
    }

    companion object {
        const val RADIO = "//table[@id='fixed-table-a17']//td//div[@id='a17_4']"
    }
}