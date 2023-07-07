package shou.common.web.element.iggrid

import com.microsoft.playwright.Locator

interface ElementAction {
    fun click()
    fun doubleClick()
    fun fill(locator: Locator, key: String)
}