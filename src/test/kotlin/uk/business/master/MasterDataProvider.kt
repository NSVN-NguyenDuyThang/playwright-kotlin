package uk.business.master

import org.testng.annotations.DataProvider
import shou.page.web.cas005.Cas005Master
import shou.page.web.cas005.GeneralRole
import shou.page.web.cas005.ListDataRegister
import shou.page.web.cas009.Cas009Master
import shou.page.web.cas009.General
import shou.page.web.cas009.RoleInformation
import shou.page.web.cas011.Cas011Master
import shou.page.web.cas011.DataCas011
import shou.page.web.cas011.DataRegister
import shou.page.web.cmm008.Cmm008Master
import shou.page.web.cmm011.Cmm011Master
import shou.page.web.cmm013.Cmm013Master
import shou.page.web.cmm014.Cmm014Master
import shou.page.web.cmm029.Cmm029Master
import shou.page.web.kmf003.Kmf003Master
import shou.page.web.kmf003.ListAnnualVacation
import shou.page.web.kmk003.Kml003Master
import shou.page.web.kmk007.Kmk007Master
import shou.page.web.kmk012.Kmk012Master
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.readFile
import java.io.IOException
import java.util.function.Function
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
    }

    @DataProvider(name = "KMK003_MASTER_DATA")
    fun kmk003_getWorktimeData(): Array<Any?>? {
        val data: Map<String, Any> = readFile(Kml003Master(), "master", "kmk003_master.xml")
        return arrayOf(data["listWorktime"])
    }

    @DataProvider(name = "KMK012_CLOSURE_DATA")
    fun kmk012_getClosingData(): Array<Array<Any?>>? {
        val data: Map<String, Any> = readFile(Kmk012Master(), "master", "kmk012_closure_setting.xml")
        return arrayOf(
            arrayOf(
                data["closureList"],
                data["closureForEmploymentList"]
            )
        )
    }

    @DataProvider(name = "KMF003_ANNUAL_VACATION_DATA")
    fun kmf003_getAnnualVacationData(): Array<Any?>? {
        val data: Map<String, Any> = readFile(Kmf003Master(), "master", "kmf003_master.xml")
        return (data["annualVacationList"] as ListAnnualVacation?)!!.items.toTypedArray()
    }

    @DataProvider(name = "CAS005_ROLE_DATA")
    fun cas005_getRoleData(): Array<Array<Any>>? {
        val data = readFile<Map<String, Any>>(Cas005Master(), "master", "cas005_register_role.xml")
        val listDataRegister: ListDataRegister = data["listDataRegister"] as ListDataRegister
        val dataRegisters: MutableList<GeneralRole> = ArrayList()
        dataRegisters.addAll(listDataRegister.items.map { it.generalRole!! })
        return dataRegisters.map { arrayOf<Any>(it) }.toTypedArray()
    }

    @DataProvider(name = "CAS009_MASTER")
    fun cas009(): Array<Array<RoleInformation>> {
        val data: Map<String, Any> = readFile(Cas009Master(), "master", "cas009_master.xml")
        val result = mutableListOf<RoleInformation>()
        val general: General = data["generalRoleInformation"] as General
        result.add(general.roleInformation!!)
        return result.map { arrayOf(it) }.toTypedArray()
    }

    @DataProvider(name = "CAS011_MASTER")
    fun cas011(): Array<Array<DataRegister>> {
        val data: Map<String, Any> = readFile(Cas011Master(), "master", "cas011_master.xml")
        val result = arrayListOf<DataRegister>()
        val dataCas011 = data["dataRegister"] as DataCas011
        result.add(dataCas011?.dataRegister!!)
        return result.map { arrayOf(it) }.toTypedArray()
    }
}