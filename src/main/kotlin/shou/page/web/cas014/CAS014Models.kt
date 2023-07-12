package shou.page.web.cas014


data class PositionItem(val code: String = "", val value: String = "null") {

    override fun toString(): String {
        return String.format("「%s」で - %sを選択", code, value)
    }
}

data class Position(val positionItems: List<PositionItem> = arrayListOf()) {}