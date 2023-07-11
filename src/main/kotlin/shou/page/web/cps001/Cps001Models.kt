package shou.page.web.cps001

data class Employee(val code: String = "", val select: String = "") {}

data class EmployeeList(val items: List<Employee> = arrayListOf()) {}

data class CategorySetting(val name: String = "", val employeeCode: String? = null, val settingList: List<Category>? = null, var employees: List<Employee>? = null) {}

data class Category(val title: String = "", val titleTextRsId: String = "", val value: String = "") {}