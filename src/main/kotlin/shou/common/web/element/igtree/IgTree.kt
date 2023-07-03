package shou.common.web.element.igtree

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.PlaywrightException
import shou.common.web.BasePage
import java.util.*

/**
 * Class xử lý dạng tree node (thường sử dụng trong workplace)
 * @property wrapper locator đến thẻ ul chứa class ui-igtree-collection (component chứa toàn bộ
 * tree node)
 */
class IgTree(override var page: Page, private val wrapper: String) : BasePage() {
    /**
     * Chọn item trong tree node
     *
     * @param code
     * @return content của node
     */
    fun selectByCode(code: String): String {
        val node: Locator = page.locator(wrapper).locator(String.format(NODE, code))
        clickToElement(node)
        return node.innerText()
    }
    /**
     * Expand tất cả các tree node
     */
    fun expandAllNode() {
        page.locator(wrapper).locator(EXPANDER_ICON).evaluateAll("ele => ele.removeAttribute('style')")
    }
    /**
     * get parent node
     * @param code
     * @return content của node cha nếu có; null nếu không có
     */
    fun getParentNode(code: String) : String? {
        return try {
            page.locator(wrapper).locator(String.format(NODE, code)).locator(PARENT_NODE).innerText()
        } catch (e : PlaywrightException) {
            null
        }
    }
    /**
     * get các child node
     * @param code
     * @return danh sách content của các node con; danh sách trống nếu không có
     */
    fun getChildNodes(code: String): List<String> {
        val childNodes: Locator = page.locator(wrapper).locator(String.format(NODE, code)).locator(CHILD_NODES)
        return when (childNodes.count()) {
            0 -> Collections.emptyList()
            else -> {
                childNodes.allInnerTexts()
            }
        }
    }

    companion object {
        private const val NODE = "//li/a[starts-with(text(), '%s ')]"
        private const val PARENT_NODE = "/parent::li/parent::ul[@data-depth]/preceding-sibling::a"
        private const val CHILD_NODES = "/following-sibling::ul[@data-depth]/li/a"
        private const val EXPANDER_ICON = "//li/ul[@data-depth and contains(@style, 'display: none')]"
    }
}