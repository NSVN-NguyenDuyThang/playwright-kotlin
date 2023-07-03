package shou.common.mobile

object CommonUI {
    // mobile
    const val NAVBAR_ITEM: String = "//nav[@class='sidebar show']//li/a/span[text()='%s']"

    // mobile.switchbtn
    const val SWITCH_BTN_OPTION_BY_VALUE: String = "label input[value='%s']"
    const val SWITCH_BTN_OPTION_BY_TEXT: String = "/span[normalize-space()='%s']"

    // mobile.card
    const val CARD_BODY_BY_HEADER: String =
        "//div[contains(@class, 'card') and .//div[contains(@class, 'card-header')]//span[normalize-space()='%s']]//div[contains(@class, 'card-body')]"
    const val CARD_BODY_BY_HEADER_AND_TITLE: String =
        "//div[contains(@class, 'card') and .//div[contains(@class, 'card-header')]//span[normalize-space()='%s']]//div[contains(@class, 'card-body')][./preceding-sibling::div[normalize-space()='%s']]"
}
