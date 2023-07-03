package shou.page.mobile.kaf.model

import lombok.AllArgsConstructor
import shou.common.model.PeriodTime

@AllArgsConstructor
class StampApplication (val division: DivisionType,
                        val appDate: String,
                        val workingHours: PeriodTime,
                        val reason: String){
}
