package shou.page.web.cas009


data class AdvancedSetting(val settingName: String? = null, val used: Boolean? = null) {}


data class RoleInformation(val code: String? = null,val name: String? = null, val employeeRefRange: String? = null, val advancedSetting: List<AdvancedSetting>? = null) {}


data class General(val roleInformation: RoleInformation? = null) {}

