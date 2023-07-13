package shou.browser

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Playwright
import shou.GlobalConstants
import java.nio.file.Paths

enum class BrowserFactory {
    CHROMIUM {
        override fun createInstance(playwright: Playwright, webMode: Boolean): Browser {
            return playwright.chromium().launch(options(webMode))
        }
    },
    CHROME {
        override fun createInstance(playwright: Playwright, webMode: Boolean): Browser {
            return playwright.chromium().launch(options(webMode).setChannel("chrome"))
        }
    },
    EDGE {
        override fun createInstance(playwright: Playwright, webMode: Boolean): Browser {
            return playwright.chromium().launch(options(webMode).setChannel("msedge"))
        }
    },
    FIREFOX {
        override fun createInstance(playwright: Playwright, webMode: Boolean): Browser {
            return playwright.firefox().launch(options(webMode))
        }
    },
    WEBKIT {
        override fun createInstance(playwright: Playwright, webMode: Boolean): Browser {
            return playwright.webkit().launch(options(webMode))
        }
    };

    fun options(webMode: Boolean): LaunchOptions {
        var optLst = mutableListOf<String>( "--incognito", "--disable-notifications", "--disable-geolocation")
        if (!webMode) {
            optLst.add("--auto-open-devtools-for-tabs")
        }
        return LaunchOptions()
            .setHeadless(true)
            .setArgs(optLst)
            .setDownloadsPath(Paths.get(GlobalConstants.downloadPath))
    }

    abstract fun createInstance(playwright: Playwright, webMode: Boolean): Browser
}
