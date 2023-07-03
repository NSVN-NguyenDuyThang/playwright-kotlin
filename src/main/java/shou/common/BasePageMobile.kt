package shou.common

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GoBackOptions
import com.microsoft.playwright.options.SelectOption
import com.microsoft.playwright.options.WaitForSelectorState
import com.microsoft.playwright.options.WaitUntilState
import io.qameta.allure.Allure
import java.awt.AWTException
import java.awt.Robot
import java.awt.event.KeyEvent
import java.io.ByteArrayInputStream

/**
 *
 * @author thangnd
 */
open class BasePageMobile {
    internal lateinit var page: Page
    fun setPage(page: Page) {
        this.page = page
    }

    fun openPageUrlMobileMode(url: String?) {
        page.navigate(url, Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED))
        waitForJSLoadedSuccess()
        page.waitForTimeout(5000.0)
    }

    protected fun openNavBar() {
        clickToElement("div.sidebar-header >> button")
    }

    protected fun selectItemOnNavBar(item: String) {
        clickToElement(String.format(CommonUI.NAVBAR_ITEM, item))
    }

    fun switchToDeviceMode() {
        try {
            val robot = Robot()
            robot.keyPress(KeyEvent.VK_CONTROL)
            robot.keyPress(KeyEvent.VK_SHIFT)
            robot.keyPress(KeyEvent.VK_M)
            robot.keyRelease(KeyEvent.VK_CONTROL)
            robot.keyRelease(KeyEvent.VK_SHIFT)
            robot.keyRelease(KeyEvent.VK_M)
        } catch (e: AWTException) {
            e.printStackTrace()
        }
    }

    /**
     * Back to previous page
     */
    fun backToPage() {
        page.goBack(GoBackOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED))
    }

    /**
     * get and focus to page by page url
     * @param pageUrl
     * @return
     */
    fun getPageByUrl(pageUrl: String?): Page? {
        val listPage = page.context().pages()
        for (p in listPage) {
            if (p.url() == pageUrl) {
                p.bringToFront()
                return p
            }
        }
        return null
    }

    /**
     *
     * @param title
     * @return
     */
    fun getPageByTitle(title: String?): Page? {
        val listPage = page.context().pages()
        for (p in listPage) {
            if (p.title() == title) {
                p.bringToFront()
                return p
            }
        }
        return null
    }

    fun closePage(page: Page) {
        page.close()
    }

    private fun getDynamicSelector(selector: String, vararg dynamicValues: String?): String? {
        return String.format(selector, *dynamicValues as Array<Any?>)
    }

    /**
     * get iframe dialog
     * @param frameTitle
     * @return
     */
    fun getFrame(frameTitle: String?): FrameLocator? {
        return page.frameLocator(getDynamicSelector(CommonUI.IFRAME, frameTitle))
    }

    /**
     * get text resource
     * @param textRsId
     * @return
     */
    fun getTextResource(textRsId: String?): String? {
        val handle = page.evaluateHandle(String.format("() => nts.uk.resource.getText('%s')", textRsId))
        return handle.toString()
    }

    /**
     * get message
     * @param messageId
     * @return
     */
    fun getMessageResource(messageId: String?): String? {
        val handle = page.evaluateHandle(String.format("() => nts.uk.resource.getMessage('%s')", messageId))
        return handle.toString()
    }

    fun waitForJSLoadedSuccess() {
        page.waitForFunction("() => document.readyState == 'complete'")
    }

    /**
     * Wait for JQuery and JS of page loaded success
     */
    fun waitForJQueryAndJSLoadedSuccess() {
        page.waitForFunction("() => jQuery.active == 0")
        page.waitForFunction("() => document.readyState == 'complete'")
    }

    /**
     * Highlight element before interact
     *
     * @param locator
     */
    protected fun highlightElement(locator: Locator) {
        val originalStyle = locator.getAttribute("style")
        locator.evaluate("ele => ele.setAttribute('style', 'border: 2px solid red; border-style: dashed;')")
        page.waitForTimeout(500.0)
        locator.evaluate(String.format("ele => ele.setAttribute('style', '%s')", originalStyle))
    }

    /**
     * Highlight element before interact
     * @param selector
     */
    protected fun highlightElement(selector: String) {
        val originalStyle = page.getAttribute(selector, "style")
        page.evalOnSelector(
            selector,
            "ele => ele.setAttribute('style', 'border: 2px solid red; border-style: dashed;')"
        )
        page.waitForTimeout(500.0)
        page.evalOnSelector(selector, String.format("ele => ele.setAttribute('style', '%s')", originalStyle))
    }

    /**
     * Highlight element before interact
     * @param selector
     * @param dynamicValues
     */
    protected fun highlightElement(selector: String, vararg dynamicValues: String?) {
        val originalStyle = page.getAttribute(getDynamicSelector(selector, *dynamicValues), "style")
        page.evalOnSelector(
            getDynamicSelector(selector, *dynamicValues),
            "ele => ele.setAttribute('style', 'border: 2px solid red; border-style: dashed;')"
        )
        page.waitForTimeout(500.0)
        page.evalOnSelector(
            getDynamicSelector(selector, *dynamicValues),
            String.format("ele => ele.setAttribute('style', '%s')", originalStyle)
        )
    }

    protected fun removeAttribute(locator: Locator, attr: String?) {
        locator.evaluate(String.format("element => element.removeAttribute('%s')", attr))
    }

    fun takeScreenshot(title: String?) {
        Allure.addAttachment(title, ByteArrayInputStream(page.screenshot()))
    }

    fun fillToElement(selector: String, value: String?) {
        highlightElement(selector)
        page.fill(selector, value)
    }

    fun fillToElement(locator: Locator, value: String?) {
        highlightElement(locator)
        locator.fill(value)
    }

    fun selectOptionByValue(selector: String, value: String?) {
        highlightElement(selector)
        page.locator(selector).selectOption(value)
    }

    fun selectOptionByLabel(selector: String, label: String?) {
        highlightElement(selector)
        page.locator(selector).selectOption(SelectOption().setLabel(label))
    }

    fun clickToElement(selector: String) {
        highlightElement(selector)
        page.locator(selector).evaluate("element => element.click();")
    }

    fun clickToElement(selector: String, vararg dynamicValues: String?) {
        highlightElement(selector, *dynamicValues)
        page.locator(getDynamicSelector(selector, *dynamicValues)).evaluate("element => element.click();")
    }

    fun clickToElement(locator: Locator) {
        highlightElement(locator)
        locator.evaluate("element => element.click();")
    }

    fun clickToElement(parentLocator: Locator, selector: String?) {
        val childLocator = parentLocator.locator(selector)
        highlightElement(childLocator)
        childLocator.evaluate("element => element.click();")
    }

    fun clickToButton(locator: Locator) {
        highlightElement(locator)
        locator.evaluate("element => element.click();")
    }

    fun doubleClickToElement(selector: String) {
        highlightElement(selector)
        page.dblclick(selector)
    }

    fun doubleClickToElement(locator: Locator) {
        highlightElement(locator)
        locator.dblclick()
    }

    fun doubleClickToElement(selector: String, vararg dynamicValues: String?) {
        highlightElement(selector, *dynamicValues)
        page.dblclick(getDynamicSelector(selector, *dynamicValues))
    }

    fun getElementInnerText(locator: Locator): String? {
        highlightElement(locator)
        return locator.innerText()
    }

    fun getElementAttribute(locator: Locator, attribute: String?): String? {
        return locator.getAttribute(attribute)
    }

    fun getElementAttribute(selector: String?, attribute: String?): String? {
        return page.getAttribute(selector, attribute)
    }

    fun scrollToElement(locator: Locator) {
        locator.scrollIntoViewIfNeeded()
    }

    fun scrollToElement(selector: String?) {
        page.locator(selector).scrollIntoViewIfNeeded()
    }

    fun dragAndDropElement(sourceSelector: String?, targetSelector: String?) {
        page.dragAndDrop(sourceSelector, targetSelector)
    }

    fun hoverOnElement(locator: Locator) {
        locator.hover(Locator.HoverOptions().setTrial(true))
    }

    fun hoverOnElement(selector: String?) {
        page.hover(selector, Page.HoverOptions().setTrial(true))
    }

    fun waitForElementVisible(selector: String?) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE))
    }

    fun waitForElementHidden(selector: String?) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN))
    }

    fun waitForElementAttached(selector: String?) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED))
    }

    fun waitForElementDetached(selector: String?) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED))
    }
}
