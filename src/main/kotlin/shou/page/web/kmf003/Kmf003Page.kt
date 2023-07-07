package shou.page.web.kmf003

import com.microsoft.playwright.FrameLocator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI
import shou.common.web.element.switchbox.SwitchBox

class Kmf003Page() : BasePage() {

    private val settingFrame : FrameLocator
        get() = getFrame(getTextResource("KMF003_2"))
    @Step("新規登録前にデータを削除する")
    fun deleteAnnualVacationIfExisted(codes: List<String?>) {
        page.locator(VACATION_LIST).all().map { it.getAttribute("data-id") }.filter { codes.contains(it) }
            .forEach {
                clickToElement("Click to $it", String.format(VACATION_ITEM, it))
                clickToButton("KMF003_6")
                messageConfirmYes("Msg_18")
                closeMsgInfo(getMessageResource("Msg_16"))
            }
    }

    private fun clickToCreateNewButton() {
        clickToButton("KMF003_3")
    }

    @Step("年休コードを入力")
    private fun inputVacationCode(vacationCode: String) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "input-code"), vacationCode, "コード")
    }

    @Step("年休名称を入力")
    private fun inputVacationName(vacationName: String) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "input-name"), vacationName, "名前")
    }

    @Step("年間労働日数の計算基準を選択")
    private fun chooseCalculationStandardForAnnualWorkingDays(caculationType: String?) {
        SwitchBox(page, String.format(SWITCH_BUTTON, getTextResource("KMF003_16"))).select(getTextResource(caculationType))
        takeScreenshot("年間労働日数の計算基準を選択")
    }

    @Step("年休付与基準の設定を選択")
    private fun chooseAnnualVacationGrantCriteria(grantCriteria: String?) {
        SwitchBox(page, String.format(SWITCH_BUTTON, getTextResource("KMF003_19"))).select(getTextResource(grantCriteria))
        takeScreenshot("年休付与基準の設定を選択")
    }

    @Step("チェックボックスにチェックを入れる")
    private fun checkToUseCondtion(conditionFrame: String, isUsed: Boolean) {
        if (isUsed) {
            clickToElement(String.format(CONDITION_CHECKBOX, conditionFrame))
            takeScreenshot("チェックボックスにチェックを入れる")
        }
    }

    @Step("データを入力")
    private fun inputConditionFrame(conditionFrame: String, value: String) {
        when (conditionFrame) {
            Frame.Frame1.textRsId -> typeToElement(String.format(CONDITION_FRAME_1_TEXTBOX, conditionFrame), value)
            else -> typeToElement(String.format(CONDITION_FRAME_2_TO_5_TEXTBOX, conditionFrame), value)
        }
        takeScreenshot("データを入力")
    }

    @Step("「勤続年数」と「年休付与日数」にデータを入力")
    private fun inputGrantTime(year: String, month: String, grantDays: String, periodNo: String) {
        typeToElement(settingFrame.locator(String.format(YEAR_TEXTBOX, periodNo)), year)
        typeToElement(settingFrame.locator(String.format(MONTH_TEXTBOX, periodNo)), month)
        typeToElement(settingFrame.locator(String.format(GRANT_DAY_TEXTBOX, periodNo)), grantDays)
        takeScreenshot("「勤続年数」と「年休付与日数」にデータを入力")
    }

    @Step("ボタン「設定」をクリック")
    private fun clickToSettingFrameButton(frame: String) {
        clickToElement(String.format(CONDITION_FRAME_SETTING_BUTTON, frame))
        takeScreenshot("ボタン「設定」をクリック")
    }

    fun closeDialog() {
        clickToButton(settingFrame, "KMF003_56")
    }

    @Step("年休付与基準のデータを登録")
    fun registerGrantTime(frameList: FrameList, grantTimesList: GrantTimesList): String? {
        for (frameItem in frameList.items) {
            if (frameItem.useSettingOfFrame) {
                clickToSettingFrameButton(frameItem.frame!!.textRsId)
                for ((periodNo, year, month, grantDays) in grantTimesList.items) {
                    inputGrantTime(year, month, grantDays, periodNo)
                }
                clickToButton(settingFrame,"KMF003_4")
            }
        }
        return getAndCloseMsgInfo()
    }

    @Step("年休を登録")
    fun registerAnnualVacation(vacation: AnnualVacation): String? {
        clickToCreateNewButton()
        inputVacationCode(vacation.code)
        inputVacationName(vacation.name)
        chooseCalculationStandardForAnnualWorkingDays(vacation.calStandardForAnnualWorkingDays)
        chooseAnnualVacationGrantCriteria(vacation.annualVacationGrantCriteria)
        for (conditionItem in vacation.frameList!!.items) {
            if (conditionItem.frame === Frame.Frame1) {
                inputConditionFrame(conditionItem.frame.textRsId, conditionItem.value!!)
            } else if (conditionItem.useFrame) {
                checkToUseCondtion(conditionItem.frame!!.textRsId, true)
                inputConditionFrame(conditionItem.frame.textRsId, conditionItem.value!!)
            }
        }
        clickToButton("KMF003_4")
        return getAndCloseMsgInfo()
    }

    companion object {
        const val VACATION_LIST = "//table[@id='multi-list']//tr[@data-id]"
        const val VACATION_ITEM = "//tr[@data-id='%s']"
        const val SWITCH_BUTTON = "//span[text()='%s']/ancestor::td/following-sibling::td/div"
        const val CONDITION_FRAME_1_TEXTBOX = "//div[not(@style='display: none;')]/span/input[contains(@data-bind,'#[%s]')]"
        const val CONDITION_FRAME_2_TO_5_TEXTBOX = "//input[contains(@data-bind,'#[%s]')]"
        const val CONDITION_CHECKBOX = "//input[contains(@data-bind,'#[%s]')]/ancestor::td/preceding-sibling::td/div//input"
        const val CONDITION_FRAME_SETTING_BUTTON = "//input[contains(@data-bind,'#[%s]')]/ancestor::td/following-sibling::td/button"
        const val YEAR_TEXTBOX = "//td[text()='%s']/following-sibling::td//input[contains(@class,'year')]"
        const val MONTH_TEXTBOX = "//td[text()='%s']/following-sibling::td//input[contains(@class,'months')]"
        const val GRANT_DAY_TEXTBOX = "//td[text()='%s']/following-sibling::td//input[contains(@data-bind,'grantDays')]"
        const val TABLE_GRANT_TIMES = "id=b2_1"
    }
}