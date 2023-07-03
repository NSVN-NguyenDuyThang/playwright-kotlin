package shou.browser

import com.microsoft.playwright.Page
import shou.common.mobile.BasePageMobile
import shou.common.web.BasePage

object PageFactory {
    /**
     * For Mobile Web Base
     */
    fun <T : BasePageMobile> createInstance(page: Page, basePage: Class<T>): T {
        try {
            var instance: BasePageMobile = basePage.getDeclaredConstructor().newInstance()
            instance.page = page
            return basePage.cast(instance)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw NullPointerException("Page class instantiation failed.")
    }

    /**
     * For Web base
     */
    fun<T : BasePage> createInstance(page: Page, basePage: Class<T>) : T {
        try {
            var instance: BasePage = basePage.getDeclaredConstructor().newInstance()
            instance.page = page
            return basePage.cast(instance)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw NullPointerException("Page class instantiation failed.")
    }

}
