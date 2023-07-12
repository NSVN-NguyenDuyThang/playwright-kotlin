package shou.page.web.cmm011

import com.fasterxml.jackson.annotation.JsonValue

enum class StartScreenStatus {
    HISTORY_DIALOG_DISPLAY,
    MSG_373_DIALOG
}

enum class PlaceOfCreation(@JsonValue val textRsId: String) {
    ABOVE("CMM011_211"),
    UNDER("CMM011_212"),
    CHILD("CMM011_213")
}

enum class ActionWhenDuplicateCode(var textRsId: String) {
    REGISTER_AS_A_DIFFERENT_WORKPLACE("CMM011_208"),
    REGISTER_AS_THE_SAME_WORKPLACE("CMM011_209")
}

data class Workplace(    val baseOnWorkplaceCode: String? = null,
                         val placeOfCreation: PlaceOfCreation? = null,
                         val code: String = "",
                         val name: String = "",
                         val displayName: String = "",
                         val genericName: String = "") {
}

data class ListWorkplace(val items: List<Workplace> = arrayListOf()){}
