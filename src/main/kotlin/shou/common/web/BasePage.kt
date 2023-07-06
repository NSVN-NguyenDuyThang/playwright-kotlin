package shou.common.web

import com.microsoft.playwright.FrameLocator
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GoBackOptions
import com.microsoft.playwright.PlaywrightException
import com.microsoft.playwright.options.WaitForSelectorState
import com.microsoft.playwright.options.WaitUntilState
import io.qameta.allure.Allure
import io.qameta.allure.Step
import java.io.ByteArrayInputStream
import java.util.concurrent.Callable

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

    private fun getDynamicSelector(selector: String, vararg dynamicValues: String?): String {
        return String.format(selector, *dynamicValues as Array<Any?>)
    }

    /**
     * get iframe dialog
     * @param frameTitle
     * @return
     */
    internal fun getFrame(frameTitle: String): FrameLocator {
        return page.frameLocator(getDynamicSelector(CommonUI.IFRAME, frameTitle)).first() ?: throw PlaywrightException("No such frame with $frameTitle title")
    }

    internal fun getVisibleFrame(): FrameLocator {
        return page.frameLocator(CommonUI.VISIBLE_IFRAME).first()
    }


    /**
     * get text resource
     * @param textRsId
     * @return
     */
    internal fun getTextResource(textRsId: String?): String {
        val handle = page.evaluateHandle("() => nts.uk.resource.getText('$textRsId')")
        return handle.toString()
    }

    /**
     * get message
     * @param messageId
     * @return
     */
    internal fun getMessageResource(messageId: String): String {
        val handle = page.evaluateHandle("() => nts.uk.resource.getMessage('$messageId')")
        return handle.toString()
    }

    /**
     * Wait for JQuery and JS of page loaded success
     */
    internal fun waitForJQueryAndJSLoadedSuccess() {
        page.waitForFunction("() => jQuery.active == 0")
        page.waitForFunction("() => document.readyState == 'complete'")
    }

    protected fun setAttribute(locator: Locator?, attribute: String, value: String) {
        locator?.evaluate("ele => ele.setAttribute('$attribute', '$value')")
    }

    protected fun removeAttribute(locator: Locator, attribute: String) {
        locator.evaluate("ele => ele.removeAttribute('$attribute')")
    }

    /**
     * Highlight element before interact
     *
     * @param locator
     */
    protected fun highlightElement(locator: Locator?) {
        val originalStyle: String = locator?.getAttribute("style") ?: ""
        setAttribute(locator, "style", "border: 2px solid red; border-style: dashed;");
        page.waitForTimeout(500.0)
        setAttribute(locator, "style", originalStyle)
    }

    /**
     * Highlight element before interact
     * @param selector
     */
    protected fun highlightElement(selector: String?) {
        val originalStyle : String = page.getAttribute(selector, "style") ?: ""
        page.evalOnSelector(
            selector,
            "ele => ele.setAttribute('style', 'border: 2px solid red; border-style: dashed;')"
        )
        page.waitForTimeout(500.0)
        page.evalOnSelector(selector,"ele => ele.setAttribute('style', '$originalStyle')")
    }

    internal fun takeScreenshot(title: String?) {
        Allure.addAttachment(title, ByteArrayInputStream(page.screenshot()))
    }

    @Step("{0}")
    protected fun addStep(step: String, runnable: Runnable) {
        runnable.run()
        takeScreenshot(step)
    }

    internal fun fillToElement(selector: String?, value: String?) {
        highlightElement(selector)
        page.fill(selector, value)
    }
    @Step("テキスト [{1}] を {2}に入力")
    internal fun fillToElement(selector: String?, value: String?, elementName: String?) {
        fillToElement(selector, value)
    }

    internal fun fillToElement(locator: Locator?, value: String?) {
        highlightElement(locator)
        locator?.fill(value)
    }

    @Step("テキスト [{1}] を {2}に入力")
    internal fun fillToElement(locator: Locator?, value: String?, elementName: String?) {
        fillToElement(locator, value)
    }

    internal fun pressKeyToElement(locator: Locator?, key: String) {
        locator?.press(key)
    }

    @Step("{0")
    internal fun clickToElement(step: String, selector: String?) {
        clickToElement(selector)
    }

    internal fun clickToElement(selector: String?) {
        highlightElement(selector)
        page.click(selector, Page.ClickOptions())
    }

    internal fun clickToElement(locator: Locator?) {
        highlightElement(locator)
        locator?.click()
    }

    @Step("{0}")
    internal fun clickToElement(step: String, locator: Locator?) {
       clickToElement(locator)
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

    protected fun getElementInnerText(locator: Locator): String {
        highlightElement(locator)
        return locator.innerText()
    }

    protected fun getElementAttribute(locator: Locator?, attribute: String): String? {
        return locator?.getAttribute(attribute)
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

    protected fun waitForElementVisible(selector: String, timeoutMs: Double) {
        page.waitForSelector(selector, Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeoutMs))
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
    @Step("メッセージが表示される")
    internal fun getAndCloseMsgInfo(): String {
        val msgId = getElementInnerText(page.locator(CommonUI.MSG_ID))
        takeScreenshot("メッセージ（$msgId）が表示される")
        closeMsgInfo(msgId)
        return msgId
    }
    @Step("閉じる {0}")
    internal fun closeMsgInfo(msgId: String) {
        waitForElementVisible(String.format(CommonUI.DISPLAY_MSG_ID, msgId))
        clickToElement(CommonUI.CLOSE_BTN)
    }
    @Step("ボタン「 はい」をクリック")
    internal fun messageConfirmYes(msgId: String) {
        waitForElementVisible(String.format(CommonUI.DISPLAY_MSG_ID, msgId))
        clickToElement(CommonUI.YES_BTN)
        waitForJQueryAndJSLoadedSuccess()
    }
    @Step("ボタン「いいえ」をクリックする")
    internal fun messageConfirmNo(msgId: String) {
        waitForElementVisible(String.format(CommonUI.DISPLAY_MSG_ID, msgId))
        clickToElement(CommonUI.NO_BTN)
        waitForJQueryAndJSLoadedSuccess()
    }

    internal fun clickToButton(textRsId: String) {
        clickToButtonName(getTextResource(textRsId))
    }
    @Step("ボタン「{0}」をクリックする")
    private fun clickToButtonName(text: String) {
        clickToElement(String.format(CommonUI.BUTTON, text))
        takeScreenshot(String.format("ボタン「$text」をクリックする"));
    }

    internal fun clickToButton(frame: FrameLocator, textRsId: String) {
        clickToButtonInFrame(frame, getTextResource(textRsId))
    }

    @Step("ボタン「{1}」をクリックする")
    private fun clickToButtonInFrame(frame: FrameLocator, text: String) {
        clickToElement(frame.locator(String.format(CommonUI.BUTTON, text)))
        takeScreenshot(String.format("ボタン「$text」をクリックする"));
    }

    companion object {
        internal const val CHECK_TO_CHECKBOX = ""
        internal const val CHECK_TO_SWITCH_BOX = ""
        internal const val CHECK_TO_RADIO_BUTTON = "Select option 「%s」"
        internal const val SELECT_FROM_COMBOBOX = ""
        internal const val SELECT_DATE_TIME_VALUE = ""
    }


}
