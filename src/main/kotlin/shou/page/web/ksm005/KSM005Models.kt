package shou.page.web.ksm005

import shou.page.web.cas005.ItemValue


data class MonthlyPattern(val patternCode: ItemValue? = null, val patternName: ItemValue? = null, val workTime: ItemValue? = null, val workingDay: ItemValue? = null,val legalHoliday: ItemValue? = null, val nonLegalHoliday: ItemValue? = null) {

}