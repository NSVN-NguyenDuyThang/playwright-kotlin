package shou.page.web.cas011

import shou.page.web.cas005.ItemValue
import shou.utils.model.AggregateModel


data class DataCas011(val dataRegister: DataRegister? = null) : AggregateModel() {}

data class DataRegister(val code: ItemValue? = null, val name: ItemValue? = null, val emplyeeRole: ItemValue? = null, val personRole: ItemValue? = null, val selection: Selection? = null) {}

data class Selection(val selectables: List<ItemValue>? = null) : AggregateModel() {}