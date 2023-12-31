package shou.common.web

object CommonUI {
    //web
    const val IFRAME: String = "//span[text()='%s']/parent::div/following-sibling::div/iframe"
    const val VISIBLE_IFRAME: String = "//div[@role='dialog' and not(contains(@style, 'display: none;'))]//iframe"
    const val ERROR_DIALOG = "//button[@class='btn-error small danger']"
    const val MSG_ID_ERROR_DIALOG = "//td[starts-with(text(),'Msg_')]"
    const val MSG_ID: String = "//div[@class='control pre']"
    const val DISPLAY_MSG: String = "//div[(@class='control pre' or @class='text') and text()='%s']"
    const val MSG_CONTENT: String = "//div[@class='text']"
    const val CLOSE_BTN: String = "//button[@class='large' and text()='閉じる']"
    const val YES_BTN: String = "//button[@class='yes large danger']"
    const val NO_BTN: String = "//button[@class='no large']"
    const val BUTTON: String = "//button[normalize-space()='%s']"
    const val INPUT_BY_ID: String = "//input[@id='%s']"
    const val INPUT_BY_VALUE_NAME: String = "//input[contains(@data-bind, 'value: %s')]"
    const val TEXTAREA_BY_ID: String = "//textarea[@id='%s']"
}
