package shou.page.web.kmk012

import com.fasterxml.jackson.annotation.JsonValue


enum class UseSetting(@JsonValue var textRsId: String) {
    USE("KMK012_3"),
    NOT_USE("KMK012_4")

}
data class ClosureData(val closureNo: String? = null, val useSetting: UseSetting? = null, val name: String? = null, val closingDate: String? = null) {}

data class ListClosure(val items: List<ClosureData> = arrayListOf()) {}
data class ClosureForEmployment(val employmentCode: String = "", val closureCode: String = "") {}

data class ListClosureForEmployment(val items: List<ClosureForEmployment> = arrayListOf()) {}

