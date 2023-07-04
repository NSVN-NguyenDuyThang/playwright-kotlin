package shou.common.web

object CommonUI {
    //web
    const val IFRAME: String = "//span[text()='%s']/parent::div/following-sibling::div/iframe"
    const val MSG_ID: String = "//div[@class='control pre']"
    const val DISPLAY_MSG_ID: String = "//div[@class='control pre' and text()='%s']"
    const val MSG_CONTENT: String = "//div[@class='text']"
    const val CLOSE_BTN: String = "//button[@class='large' and text()='閉じる']"
    const val YES_BTN: String = "//button[@class='yes large danger']"
    const val NO_BTN: String = "//button[@class='no large']"
    const val BUTTON: String = "//button[normalize-space()='%s']"
}
