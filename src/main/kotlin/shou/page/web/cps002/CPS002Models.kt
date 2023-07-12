package shou.page.web.cps002


enum class EnumValidateEmployee(val des: String, val value: Int) {
    EMPLOYEE_EXITED("Employee is exited", 0),
    EMPLOYEE_ERROR("Employee attribute error", 1),
    EMPLOYEE_BASIC("Employee is continue step", 2);

    fun check(): Boolean {
        return value != 1
    }
}
data class EmployeeSetting (
    val actor: String? = null,
    val joiningDate: String? = null,
    val employeeCode: String? = null,
    val employeeName: String? = null,
    var cardNo: String? = null,
    var loginId: String? = null,
    val password: String? = null,
    val defaultInformation: String? = null,
    val personalName: String? = null,
    val displayName: String? = null,
    val birthDay: String? = null,
    val sex: String? = null,
    val classificationCD: String? = null,
    val employmentCD: String? = null,
    val jobTitleCD: String? = null,
    val workPlaceCD: String? = null,
    val employmentCategory: String? = null,
    val contract: String? = null,
    val workDuringVacation: String? = null,
    val attendenceAndLeaving: String? = null,
    val classification: String? = null,
    val scheduleManagement: String? = null,
    val scheduleCreate: String? = null,
    val monthlyPattern: String? = null,
    val businessReference: String? = null,
    val workTypeWeekday: String? = null,
    val workHoursWeekday: String? = null,
    val holidayType: String? = null,
    val workTypeLeave: String? = null,
    val workHoursHoliday: String? = null,
    val criteriaGrantingAnnualLeave: String? = null,
    val annualLeaveGrantTable: String? = null
){}

data class EmployeeSettingList(val items: List<EmployeeSetting> = arrayListOf()) {}