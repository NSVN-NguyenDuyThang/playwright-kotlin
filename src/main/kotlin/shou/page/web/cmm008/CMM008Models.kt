package shou.page.web.cmm008


data class Employment(val code: String = "", val name: String = "", val externalCode: String? = null, val memo: String? = null) {}

data class ListEmployment(val items: List<Employment> = arrayListOf()){}