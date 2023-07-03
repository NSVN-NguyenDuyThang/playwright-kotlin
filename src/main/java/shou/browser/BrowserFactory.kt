package shou.browser

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Playwright
import shou.common.GlobalConstants
import java.nio.file.Paths

enum class BrowserFactory {
    CHROMIUM {
        override fun createInstance(playwright: Playwright): Browser {
            return playwright.chromium().launch(options())
        }
    },
    CHROME {
        override fun createInstance(playwright: Playwright): Browser {
            return playwright.chromium().launch(options().setChannel("chrome"))
        }
    },
    EDGE {
        override fun createInstance(playwright: Playwright): Browser {
            return playwright.chromium().launch(options().setChannel("msedge"))
        }
    },
    FIREFOX {
        override fun createInstance(playwright: Playwright): Browser {
            return playwright.firefox().launch(options())
        }
    },
    WEBKIT {
        override fun createInstance(playwright: Playwright): Browser {
            return playwright.webkit().launch(options())
        }
    };

    fun options(): LaunchOptions {
        return LaunchOptions()
            .setHeadless(false)
            .setArgs(
                mutableListOf(
                    "--incognito",
                    "--auto-open-devtools-for-tabs",
                    "--disable-notifications",
                    "--disable-geolocation"
                )
            )
            .setDownloadsPath(Paths.get(GlobalConstants.downloadPath))
    }

    abstract fun createInstance(playwright: Playwright): Browser
}
