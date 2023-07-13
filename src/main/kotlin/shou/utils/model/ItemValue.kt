package shou.utils.model

import org.apache.commons.lang3.StringUtils

data class ItemValue(val value: String? = null) : AggregateModel(){
    override fun toString(): String {
        return when (StringUtils.isEmpty(this.title)) {
            true -> value!!
            else -> "「${title}」で ー ${value}を選択"
        }
    }
}