package shou.page.web.cas001

import shou.page.web.cas005.ItemValue
import shou.utils.model.AggregateModel


data class RoleSetting(val rowSelect: ItemValue? = null) {}

data class Category(val rowSelect: ItemValue? = null, val permissionSettingOther: ItemValue? = null, val permissionSettingPersonal: ItemValue? = null, val usageAuthoritySetting: UsageAuthoritySetting? = null) {}

data class UsageAuthoritySetting(val selectAll: Boolean? = null, val settings: List<ItemValue>? = null) {}

data class AccessAuthoritySetting(val role: RoleSetting? = null,val categorys: List<Category>? = null) : AggregateModel() {}

data class DataCas001(val general: AccessAuthoritySetting? = null, val person: AccessAuthoritySetting? = null) : AggregateModel() {}



