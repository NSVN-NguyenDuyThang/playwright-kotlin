package shou.browser

import com.microsoft.playwright.Browser
import com.microsoft.playwright.Playwright
import java.util.*

object BrowserManager {
    fun browser(playwright: Playwright, browserType: String, webMode: Boolean): Browser {
        return BrowserFactory.valueOf(browserType.uppercase(Locale.getDefault()))
            .createInstance(playwright, webMode)
    }
}
