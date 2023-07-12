package shou.page.web.kmk007

import com.fasterxml.jackson.annotation.JsonValue


enum class WorkTypeUnit(@JsonValue val textRsID: String) {
    /* 出勤 */
    OneDay("KMK007_19"),
    /* 休日 */
    HalfDay("KMK007_20")
}
enum class WorkTypeClassification(val key: String, @JsonValue val workTypeName: String) {
    Attendance("0", "出勤"),
    Holiday("1", "休日"),
    AnnualHoliday("2", "年休"),
    YearlyReserved("3", "積立年休"),
    SpecialHoliday("4", "特別休暇"),
    Absence("5", "欠勤"),
    SubstituteHoliday("6", "代休"),
    Shooting("7", "振出"),
    Pause("8", "振休"),
    TimeDigestVacation("9", "時間消化休暇"),
    ContinuousWork("10", "連続勤務"),
    HolidayWork("11", "休日出勤"),
    LeaveOfAbsence("12", "休職"),
    Close("13", "休業")
}

data class ListOptionAfternoon(val items: List<String> = arrayListOf()) {}

data class ListOptionMorning(val items: List<String> = arrayListOf()) {}

data class ListOptionOneDay(val items: List<String> = arrayListOf()) {}

data class ListWorktype(val items: List<WorkType> = arrayListOf()) {}

data class WorkType(val code: String = "",
                    val name: String? = null,
                    val shortName: String? = null,
                    val workTypeUnit: WorkTypeUnit? = null,
                    val workTypeCls: WorkTypeClassification? = null,
                    val workTypeMorningCls: WorkTypeClassification? = null,
                    val workTypeAfternoonCls: WorkTypeClassification? = null,
                    val holidayCls: String? = null,
                    val holidayClsMorning: String? = null,
                    val holidayClsAfternoon: String? = null,
                    val optionsListOneDay: ListOptionOneDay? = null,
                    val optionsListMorning: ListOptionMorning? = null,
                    val optionsListAfternoon: ListOptionAfternoon? = null) {}

