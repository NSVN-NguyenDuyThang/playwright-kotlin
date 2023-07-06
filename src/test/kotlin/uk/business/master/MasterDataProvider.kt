package uk.business.master

import org.testng.annotations.DataProvider
import shou.page.web.cmm008.Cmm008Master
import shou.page.web.cmm011.Cmm011Master
import shou.page.web.cmm013.Cmm013Master
import shou.page.web.cmm014.Cmm014Master
import shou.page.web.cmm029.Cmm029Master
import shou.page.web.kmk007.Kmk007Master
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

    @DataProvider(name = "EMPLOYMENT_DATA")
    fun cmm008_getEmploymentData(): Array<Any?>? {
        val data: Map<String, Any> =
            readFile(Cmm008Master(), "master", "cmm008_register_employment.xml")
        return arrayOf(data["employmentList"])
    }

    @DataProvider(name = "WORKTYPE_DATA")
    fun kmk007_getWorktypeData(): Array<Any?>? {
        val data: Map<String, Any> = readFile(Kmk007Master(), "master", "kmk007_register_worktype.xml")
        return arrayOf(data["workTypeList"])
    };

}