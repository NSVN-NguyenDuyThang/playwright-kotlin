package uk.business.master

import org.testng.annotations.DataProvider
import shou.page.web.cmm011.Cmm011Master
import shou.utils.xml.XmlHelper
class MasterDataProvider {
    @DataProvider(name = "WORKPLACE_DATA")
    fun cmm011_getWorkplaceData(): Array<Array<Any?>> {
        val data: Map<String, Any> = XmlHelper.readFile(Cmm011Master(), "master", "cmm011_register_workplace.xml")
        return arrayOf(
            arrayOf(data["historyDate"], data["workplaceList"])
        )
    }
}