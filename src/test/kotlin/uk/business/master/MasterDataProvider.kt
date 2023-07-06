package uk.business.master

import org.testng.annotations.DataProvider
import shou.page.web.cmm011.Cmm011Master
import shou.page.web.cmm013.Cmm013Master
import shou.page.web.cmm014.Cmm014Master
import shou.page.web.cmm029.Cmm029Master
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.readFile
import java.io.IOException
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.TransformerException


class MasterDataProvider {

    @DataProvider(name = "WORK_SETTING_DATA")
    fun cmm029_getWorkSettingData(): Array<Any?>? {
        val data: Map<String, Any> = readFile(Cmm029Master(), "master", "cmm029_worksetting.xml")
        return arrayOf(data["workSettingList"])
    }
    @DataProvider(name = "WORKPLACE_DATA")
    fun cmm011_getWorkplaceData(): Array<Array<Any?>> {
        val data: Map<String, Any> = XmlHelper.readFile(Cmm011Master(), "master", "cmm011_register_workplace.xml")
        return arrayOf(
            arrayOf(data["historyDate"], data["workplaceList"])
        )
    }

    @DataProvider(name = "CLASSIFICATION_DATA")
    fun cmm014_getClassificationData(): Array<Any?>? {
        val data: Map<String, Any> =
            readFile(Cmm014Master(), "master", "cmm014_register_classification.xml")
        return arrayOf(data["classificationList"])
    }

    @DataProvider(name = "POSITION_DATA")
    fun cmm013_getPositionData(): Array<Any?>? {
        val data: Map<String, Any> = readFile(Cmm013Master(), "master", "cmm013_register_position.xml")
        return arrayOf(data["listPosition"])
    }

}