package shou.page.web.ksm006

import shou.page.web.cas005.ItemValue


data class GridSelected(val code: String? = null, val worktypes: List<ItemValue>? = null) {}

data class Classification(val gridSelected: GridSelected? = null, val itemWorkTime: ItemValue? = null) {}

data class Company(val gridSelected: GridSelected? = null, val itemWorkTime: ItemValue? = null) {}

data class DataRegisterWork(val company: Company? = null, val workplace: Workplace? = null, val classification: Classification? = null) {}

data class Workplace(val gridSelected: GridSelected? = null, val itemWorkTime: ItemValue? = null) {}