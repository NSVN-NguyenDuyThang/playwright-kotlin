package shou.page.web.cmm014


data class Classification( val code: String = "", val name: String = "" , val memo: String? = null) {}

data class ListClassification(val items: List<Classification> = arrayListOf()) {}