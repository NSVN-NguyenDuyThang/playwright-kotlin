package shou.common

import com.microsoft.playwright.Page

object PageFactory {
    fun <T : BasePageMobile> createInstance(page: Page, basePage: Class<T>): T {
        try {
            val instance: BasePageMobile = basePage.getDeclaredConstructor().newInstance()
            instance.setPage(page)
            return basePage.cast(instance)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw NullPointerException("Page class instantiation failed.")
    }
}
