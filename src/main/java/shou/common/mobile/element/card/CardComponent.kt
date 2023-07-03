package shou.common.mobile.element.card

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.mobile.BasePageMobile
import shou.common.mobile.CommonUI

class CardComponent(override var page: Page, private var cardHeader: String, private var wrapperComp: Locator? = null) : BasePageMobile() {
    fun getCardBody(): Locator {
        return if (wrapperComp != null) {
            wrapperComp!!.locator(String.format(CommonUI.CARD_BODY_BY_HEADER, cardHeader))
        } else page!!.locator(String.format(CommonUI.CARD_BODY_BY_HEADER, cardHeader))
    }

    fun getCardBody(label: String): Locator {
        return if (wrapperComp != null) {
            wrapperComp!!.locator(String.format(CommonUI.CARD_BODY_BY_HEADER_AND_TITLE, cardHeader, label))
        } else page!!.locator(String.format(CommonUI.CARD_BODY_BY_HEADER_AND_TITLE, cardHeader, label))
    }
}
