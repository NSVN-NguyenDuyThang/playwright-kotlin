package shou.page.web.cmm029

data class WorkSetting(val title: String? = null, val titleTextRsId: String = "", var used: Boolean = false) {}

data class WorkSettingList(val items: List<WorkSetting> = arrayListOf()) {}

data class OtherSetting(val selectOptionTextRsTd: String = "") {}


