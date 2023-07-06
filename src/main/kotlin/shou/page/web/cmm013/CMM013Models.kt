package shou.page.web.cmm013


data class Position(val code: String = "", val name: String = "", val apply36Agreement: Boolean = false, val orderCode: String? = null) {}

data class ListPosition(val items: List<Position> = arrayListOf()) {}