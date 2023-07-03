package shou.common

object CommonUI {
    //web
    val IFRAME: String = "xpath=//span[text()='%s']/parent::div/following-sibling::div/iframe"

    // mobile
    val NAVBAR_ITEM: String = "//nav[@class='sidebar show']//li/a/span[text()='%s']"

    // mobile.switchbtn
    val SWITCH_BTN_OPTION_BY_VALUE: String = "label input[value='%s']"
    val SWITCH_BTN_OPTION_BY_TEXT: String = "/span[normalize-space()='%s']"

    // mobile.card
    val CARD_BODY_BY_HEADER: String =
        "//div[contains(@class, 'card') and .//div[contains(@class, 'card-header')]//span[normalize-space()='%s']]//div[contains(@class, 'card-body')]"
    val CARD_BODY_BY_HEADER_AND_TITLE: String =
        "//div[contains(@class, 'card') and .//div[contains(@class, 'card-header')]//span[normalize-space()='%s']]//div[contains(@class, 'card-body')][./preceding-sibling::div[normalize-space()='%s']]"
}
