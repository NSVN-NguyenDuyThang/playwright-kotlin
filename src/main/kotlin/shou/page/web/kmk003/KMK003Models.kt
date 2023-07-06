package shou.page.web.kmk003

import com.fasterxml.jackson.annotation.JsonValue
import shou.utils.model.Period

enum class ModeSelection(@JsonValue val textRsId: String) {
    REDUCE("KMK003_190"),
    DETAIL("KMK003_191")
}

enum class SettingMethod(@JsonValue val value: String) {
    FIXED("固定勤務"),
    FLEX("流動勤務")
}

enum class TabPanelSelection(@JsonValue val textRsId: String) {
    PREDETERMINED("KMK003_17"),
    FLEXIBLE_TIME("KMK003_317"),
    WORKING_HOUR("KMK003_18"),
    OVERTIME("KMK003_89"),
    BREAK_TIME("KMK003_20"),
    HOLIDAY_WORK("KMK003_90"),
    HOLIDAY_BREAK_TIME("KMK003_21"),
    COMPENSATORY_HOLIDAY("KMK003_25")
}

enum class WorktimeCategory(@JsonValue val value: String) {
    NORMAL("通常勤務・変形労働用"),
    FLEX("フレックス勤務用")
}
data class PeriodList(val items : List<Period> = arrayListOf()) {}
data class ElapsedTimeList(val items: List<String> = arrayListOf()) {}
data class BreakTimeSetting (
    val settingForHalfday: Boolean? = null /* 半日勤務の時間帯の設定(#KMK003_319). Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FIXED]*/,
    val fixedBreaktime: Boolean? = null /* 休憩時間帯の固定(#KMK003_141). Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FLEX] hoặc [WorkTime.workTimeCategory] = [WorktimeCategory.FLEX]*/,
    val breakTimeList: PeriodList? = null /* 時間帯(#KMK003_54)*/
){}


data class CompensatoryVacationSetting (
    val checkToVacationWork: Boolean = false /* 休日出勤(#KMK003_105)*/,
    var oneDayTime: String? = null /* 1日(#KMK003_42)*/,
    var halfDayTime: String? = null /* 半日(#KMK003_107)*/
) {}

data class FlextimeSetting(val oneDayWorkingTimeList: PeriodList? = null /*時間帯(#KMK003_54) */){}

data class OvertimeSetting (
    val settingForHalfday: Boolean? = null /* 半日勤務の時間帯の設定(#KMK003_319) */,
    val automaticCalculate: Boolean? = null /* 法定内残業自動計算(#KMK003_185)*/,
    val overTimeList: PeriodList? = null /* 時間帯(#KMK003_54). Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FIXED] */,
    val elapsedTimeList: ElapsedTimeList? = null /*経過時間(#KMK003_174). Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FLEX] */
){}

data class Predetermined (
    val dayStartTime: String? = null /* 1日の始まりの時刻(#KMK003_32) */,
    val rangeHoursPerDay: String? = null /* 1日の範囲時間(#KMK003_33) */,
    val usedCoreTimeZone: Boolean? = false /* コアタイム時間帯(#KMK003_157). Khi [WorkTime.workTimeCategory] = [WorktimeCategory.FLEX], trường dữ liệu này sẽ được hiển thị */,
    val coreTimeZone: Period? = null /*コアタイム時間帯(#KMK003_318). Khi [WorkTime.workTimeCategory] = [WorktimeCategory.FLEX], trường dữ liệu này sẽ được hiển thị */,
    val workingHours: Period? = null /* 就業時刻*/,
    val useWorkingHours2: Boolean? = false /* 就業時刻２(#KMK003_37) */,
    val workingHours2: Period? = null,
    val endTimeOfMorningShift: String? = null /* 半日勤務の前半終了時刻(#KMK003_39) */,
    val startTimeOfAfternoonShift: String? = null /* 半日勤務の後半開始時刻(#KMK003_40)*/,
    val workingHour1Day: String? = null /* 1日(#KMK003_42) */,
    val workingHourMorning: String? = null /* 午前(#KMK003_43) */,
    val workingHourAfternoon: String? = null /* 午後(#KMK003_44)*/
){}

data class VacationBreakSetting(
    val fixedBreaktime: Boolean? = null /* 休憩時間帯の固定(#KMK003_141). Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FLEX] hoặc [WorkTime.workTimeCategory] = [WorktimeCategory.FLEX] */,
    val vacationBreakTimeList: PeriodList? = null /* 時間帯(#KMK003_54) */
){}

data class VacationHoursSetting(
    val oneDayWorkingTimeList: PeriodList? = null /* 時間帯(#KMK003_54). Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FIXED] */,
    val elapsedTimeList: ElapsedTimeList? = null /* 経過時間(#KMK003_174)
     * Hiển thị khi [WorkTime.settingMethod] = [SettingMethod.FLEX] */
){}

data class WorkingHoursSetting(val oneDayWorkingTimeList: PeriodList? = null /* 時間帯(#KMK003_54)*/){}

data class WorkTime(
    val code: String? = null /* コード(#KMK003_10) */,
    val name: String? = null /* 名称（#KMK003_11） */,
    val subName: String? = null /* 略名 （#KMK003_14） */,
    val workTimeCategory: WorktimeCategory? = null /* 勤務形態(#KMK003_7) */,
    val settingMethod: SettingMethod? = null /* 設定方法(#KMK003_8) */,
    val predeterminedTab: Predetermined? = null /* 所定(#KMK003_17) */,
    val flextimeTab: FlextimeSetting? = null /* Hiển thị khi [WorkTime.workTimeCategory] = [WorktimeCategory.FLEX] */,
    val workingHoursTab: WorkingHoursSetting? = null /* 勤務時間(#KMK003_18). Hiển thị khi [WorkTime.workTimeCategory] = [WorktimeCategory.NORMAL] */,
    val overtimeTab: OvertimeSetting? = null /* 残業時間帯(#KMK003_89) */,
    val breaktimeTab: BreakTimeSetting? = null /* 休憩時間帯(#KMK003_20) */,
    val vacationHoursTab: VacationHoursSetting? = null /* 休出時間帯(#KMK003_90) */,
    val vacationBreakTab: VacationBreakSetting? = null /*休出休憩(#KMK003_21) */,
    val compensatoryVacationTab: CompensatoryVacationSetting? = null /* 代休(#KMK003_25)*/
){}

data class ListWorkTime(val items: List<WorkTime> = arrayListOf()) {}

