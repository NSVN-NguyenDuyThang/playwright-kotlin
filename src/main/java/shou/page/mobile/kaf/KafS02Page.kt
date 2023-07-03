package shou.page.mobile.kaf

import shou.common.BasePageMobile
import shou.common.elememt.card.CardComponent
import shou.common.element.datetimepicker.DateTimePicker
import shou.common.element.switchbtn.SwitchBtnElement
import shou.common.model.PeriodTime
import shou.page.mobile.kaf.model.StampApplication

class KafS02Page : BasePageMobile() {
    fun openScreen() {
        openNavBar()
        page.waitForTimeout(1000.0)
        selectItemOnNavBar("打刻申請☆☆☆T")
        waitForJSLoadedSuccess()
    }

    fun registerApplication(stampApplication: StampApplication) {
        SwitchBtnElement(page, page.locator("#prePostSelect")).clickToBtnByValue(stampApplication.division.value)
        inputAppDate(stampApplication.appDate)
        inputWorkingHours(stampApplication.workingHours)
        inputReason(stampApplication.reason)
        clickToElement("//button[text()='申請する']")
        page.waitForTimeout(55000.0)
    }

    private fun inputAppDate(value: String?) {
        val appDateCard = CardComponent(page, "申請日").getCardBody()
        DateTimePicker(page, appDateCard.locator("input")).inputValue(value)
    }

    fun inputWorkingHours(periodTime: PeriodTime) {
        val cardBody = CardComponent(page, "出勤・退勤").getCardBody("勤務時間")
        DateTimePicker(page, cardBody.locator("input").locator("nth=0")).inputValue(periodTime.startTime)
        DateTimePicker(page, cardBody.locator("input").locator("nth=1")).inputValue(periodTime.endTime)
    }

    fun inputReason(text: String?) {
        val cardBody = CardComponent(page, "申請理由").getCardBody()
        cardBody.scrollIntoViewIfNeeded()
        fillToElement(cardBody.locator("textarea"), text)
        page.waitForTimeout(2000.0)
    }
}
