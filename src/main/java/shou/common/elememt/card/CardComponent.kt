package shou.common.elememt.card

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.BasePageMobile
import shou.common.CommonUI

class CardComponent : BasePageMobile {
    private var cardHeader: String?
    private var wrapperComp: Locator? = null

    constructor(page: Page, cardHeader: String) {
        setPage(page)
        this.cardHeader = cardHeader
    }

    constructor(cardHeader: String?, wrapperComp: Locator?) {
        setPage(page)
        this.cardHeader = cardHeader
        this.wrapperComp = wrapperComp
    }

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
