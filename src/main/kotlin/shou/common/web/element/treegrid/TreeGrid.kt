package shou.common.web.element.treegrid

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import shou.common.web.BasePage

/**
 * @property wrapper locator tới tree-grid_container
 */
class TreeGrid(override var page: Page, private val wrapper: String) : BasePage() {
    /**
     * Chọn 1 node <br>
     * <i>Áp dụng cho cả single-tree-grid và multiple-tree-grid</i>
     * @param code
     * @return node text của node được chọn
     */
    fun selectByCode(code: String): String {
        val node: Locator = page.locator(wrapper).locator(String.format(NODE_BY_CODE, code))
        clickToElement(node)
        return getNodeText(node)
    }
    /**
     * Chọn nhiều node <br>
     * <i>Chỉ áp dụng cho multiple-tree-grid</i>
     * @param codes
     * @return list node text của các node được chọn
     */
    fun selectListNode(codes: List<String>) : List<String> {
        uncheckAll()
        val wrapperLocator: Locator = page.locator(wrapper);
        return codes.map {
            clickToElement(wrapperLocator.locator(CHECKBOX_NODE))
            getNodeText(wrapperLocator.locator(String.format(NODE_BY_CODE, it)))
        }
    }
    /**
     * Chọn tất cả các node <br>
     * <i>Chỉ áp dụng cho multiple-tree-grid</i>
     * @return list node text của tất cả các node
     */
    fun selectAllNode(): List<String> {
        val nodeTextLst = mutableListOf<String>()
        checkAll()
        val nodes: Locator = page.locator(wrapper).locator(NODE)
        for (idx in 0 until nodes.count()) {
            nodeTextLst.add(getNodeText(nodes.nth(idx)))
        }
        return nodeTextLst
    }

    private fun checkAll() {
        val checkAll: Locator = page.locator(wrapper).locator(CHECK_ALL)
        if ("off" == checkAll.getAttribute("data-chk")) {
            clickToElement(checkAll)
        }
    }

    private fun uncheckAll() {
        val checkAll: Locator = page.locator(wrapper).locator(CHECK_ALL)
        if ("on" == checkAll.getAttribute("data-chk")) {
            clickToElement(checkAll)
        }
    }

    private fun getNodeText(node: Locator): String {
        return node.locator("td").innerText()
    }

    companion object {
        private const val NODE = "//tr[@data-id]"
        private const val NODE_BY_CODE = "//tr[@data-id and ./td[starts-with(text(),'%s ')]]"
        private const val CHECK_ALL = "//span[@name='hchk' and @data-role='checkbox'"
        private const val CHECKBOX_NODE = "//span[@name='chk']"
    }
}