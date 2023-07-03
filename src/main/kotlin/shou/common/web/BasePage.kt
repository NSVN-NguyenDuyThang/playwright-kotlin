package shou.common.web

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GoBackOptions
import com.microsoft.playwright.options.WaitForSelectorState
import com.microsoft.playwright.options.WaitUntilState
import io.qameta.allure.Allure
import java.io.ByteArrayInputStream

/**
 *
 * @author thangnd
 */
open class BasePage {
    internal open lateinit var page: Page

    /**
     * Open page
     * @param url
     */
    internal fun openPageUrl(url: String) {
        page.navigate(url, Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED))
        page.waitForTimeout(1000.0)
        waitForJQueryAndJSLoadedSuccess()
    }

    /**
     * Click to element and open page in new tab
     * @param locator
     * @return
     */
    internal fun clickAndReturnNewPageInstance(locator: Locator): Page? {
        return page.context()?.waitForPage { clickToElement(locator) }
    }

    /**
     * Back to previous page
     */
    internal fun backToPage() {
        page.goBack(GoBackOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED))
    }

    /**
     * get and focus to page by page url
     * @param pageUrl
     * @return
     */
    internal fun getPageByUrl(pageUrl: String?): Page? {
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
    internal fun getPageByTitle(title: String): Page? {
        val listPage = page.context().pages()
        for (p in listPage) {
            if (p.title() == title) {
                p.bringToFront()
                return p
            }
        }
        return null
    }

    internal fun closePage(page: Page) {
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
    internal fun getFrame(frameTitle: String?): FrameLocator? {
        return page.frameLocator(getDynamicSelector(CommonUI.IFRAME, frameTitle))
    }

    /**
     * get text resource
     * @param textRsId
     * @return
     */
    internal fun getTextResource(textRsId: String?): String? {
        val handle = page.evaluateHandle(String.format("() => nts.uk.resource.getText('%s')", textRsId))
        return handle.toString()
    }

    /**
     * get message
     * @param messageId
     * @return
     */
    internal fun getMessageResource(messageId: String?): String? {
        val handle = page.evaluateHandle(String.format("() => nts.uk.resource.getMessage('%s')", messageId))
        return handle.toString()
    }

    internal fun waitForJSLoadedSuccess() {
        page.waitForFunction("() => document.readyState == 'complete'")
    }

    /**
     * Wait for JQuery and JS of page loaded success
     */
    internal fun waitForJQueryAndJSLoadedSuccess() {
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
    protected fun highlightElement(selector: String?) {
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

    internal fun takeScreenshot(title: String?) {
        Allure.addAttachment(title, ByteArrayInputStream(page.screenshot()))
    }

    protected fun fillToElement(selector: String?, value: String?) {
        highlightElement(selector)
        page.fill(selector, value)
    }

    protected fun fillToElement(locator: Locator, value: String?) {
        highlightElement(locator)
        locator.fill(value)
    }

    protected fun clickToElement(selector: String?) {
        highlightElement(selector)
        page.click(selector, Page.ClickOptions())
    }

    protected fun clickToElement(selector: String, vararg dynamicValues: String?) {
        highlightElement(selector, *dynamicValues)
        page.click(getDynamicSelector(selector, *dynamicValues))
    }

    protected fun clickToElement(locator: Locator) {
        highlightElement(locator)
        locator.click()
    }

    protected fun clickToButtonUsingJs(locator: Locator) {
        highlightElement(locator)
        locator.evaluate("element => element.click();")
    }

    protected fun doubleClickToElement(selector: String?) {
        highlightElement(selector)
        page.dblclick(selector)
    }

    protected fun doubleClickToElement(locator: Locator) {
        highlightElement(locator)
        locator.dblclick()
    }

    protected fun doubleClickToElement(selector: String, vararg dynamicValues: String?) {
        highlightElement(selector, *dynamicValues)
        page.dblclick(getDynamicSelector(selector, *dynamicValues))
    }

    protected fun getElementInnerText(locator: Locator): String? {
        highlightElement(locator)
        return locator.innerText()
    }

    protected fun getElementAttribute(locator: Locator, attribute: String): String? {
        return locator.getAttribute(attribute)
    }

    protected fun getElementAttribute(selector: String, attribute: String): String {
        return page.getAttribute(selector, attribute)
    }

    protected fun scrollToElement(locator: Locator) {
        locator.scrollIntoViewIfNeeded()
    }

    protected fun scrollToElement(selector: String) {
        page.locator(selector).scrollIntoViewIfNeeded()
    }

    protected fun dragAndDropElement(sourceSelector: String, targetSelector: String) {
        page.dragAndDrop(sourceSelector, targetSelector)
    }

    protected fun hoverOnElement(locator: Locator) {
        locator.hover(Locator.HoverOptions().setTrial(true))
    }

    protected fun hoverOnElement(selector: String) {
        page.hover(selector, Page.HoverOptions().setTrial(true))
    }

    protected fun waitForElementVisible(selector: String) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE))
    }

    protected fun waitForElementHidden(selector: String) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN))
    }

    protected fun waitForElementAttached(selector: String) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED))
    }

    protected fun waitForElementDetached(selector: String) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED))
    }
}