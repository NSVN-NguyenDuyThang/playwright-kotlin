package shou.page.web.ksm004

import shou.page.web.cas005.ItemValue


data class CollectiveSetting( val code: ItemValue? = null, val yearMonth: ItemValue? = null, val timePeriodStart: ItemValue? = null,
                              val timePeriodEnd: ItemValue? = null, val monday: ItemValue? = null, val tuesday: ItemValue? = null,
                              val wednesday: ItemValue? = null, val thursday: ItemValue? = null, val friday: ItemValue? = null,
                              val saturday: ItemValue? = null, val sunday: ItemValue? = null) {}

data class CalendarRegistration(val items: List<CollectiveSetting>? = null) {}
