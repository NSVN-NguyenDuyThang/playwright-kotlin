package shou.page.web.cas005

import org.apache.commons.lang3.StringUtils
import shou.utils.model.AggregateModel


data class DataRegister(val responsiblePersonRole: ResponsiblePersonRole? = null, val generalRole: GeneralRole? = null) {}

data class ListDataRegister(val items: List<DataRegister> = arrayListOf()) : AggregateModel(){}

data class ItemValue(val value: String? = null) : AggregateModel(){
    override fun toString(): String {
        return when (StringUtils.isEmpty(this.title)) {
            true -> value!!
            else -> "「${title}」で ー ${value}を選択"
        }
    }
}

data class GeneralRole(val code: ItemValue? = null, val name: ItemValue? = null, val employeeReferenceRange: ItemValue? = null, val approvalAuthority: ItemValue? = null) : AggregateModel() {}

data class ResponsiblePersonRole(val code: ItemValue? = null, val name: ItemValue? = null, val menuSetting: ItemValue? = null) : AggregateModel() {}