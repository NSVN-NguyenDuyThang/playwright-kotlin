package shou.page.web.kmf003

import com.fasterxml.jackson.annotation.JsonValue


enum class Frame(@JsonValue val textRsId: String) {
    Frame1("KMF003_23"),
    Frame2("KMF003_24"),
    Frame3("KMF003_25"),
    Frame4("KMF003_26"),
    Frame5("KMF003_27")
}


data class AnnualVacation(val code: String = "", val name: String = "", val calStandardForAnnualWorkingDays: String? = null, val annualVacationGrantCriteria: String? = null, val frameList: FrameList? = null, val grantTimesList: GrantTimesList? = null) {}

data class ListAnnualVacation(val items: List<AnnualVacation> = arrayListOf()) {}
data class FrameItem(val frame: Frame? = null, val value: String? = null, val useFrame: Boolean = false, val useSettingOfFrame: Boolean = false){}

data class FrameList(val items: List<FrameItem> = arrayListOf()) {}

data class GrantTimeItem(val periodNo: String = "", val year: String = "", val month: String = "", val grantDays: String = "", val baseDate: String = ""){}

data class GrantTimesList(val items: List<GrantTimeItem> = arrayListOf()) {}
