package uk.business.master

import org.testng.Assert
import org.testng.annotations.Test
import shou.BaseTest
import shou.page.web.cas005.Cas005Page
import shou.page.web.cas005.GeneralRole
import shou.page.web.cas009.Cas009Page
import shou.page.web.cas009.RoleInformation
import shou.page.web.cmm008.Cmm008aPage
import shou.page.web.cmm008.ListEmployment
import shou.page.web.cmm011.Cmm011aPage
import shou.page.web.cmm011.ListWorkplace
import shou.page.web.cmm013.Cmm013aPage
import shou.page.web.cmm013.ListPosition
import shou.page.web.cmm014.Cmm014Page
import shou.page.web.cmm014.ListClassification
import shou.page.web.cmm029.Cmm029aPage
import shou.page.web.cmm029.WorkSettingList
import shou.page.web.kmf001.Kmf001Page
import shou.page.web.kmf003.AnnualVacation
import shou.page.web.kmf003.Kmf003Page
import shou.page.web.kmk003.Kmk003Page
import shou.page.web.kmk003.ListWorkTime
import shou.page.web.kmk007.Kmk007Page
import shou.page.web.kmk007.ListWorktype
import shou.page.web.kmk012.Kmk012Page
import shou.page.web.kmk012.ListClosure
import shou.page.web.kmk012.ListClosureForEmployment
import shou.path.PathList
import shou.utils.model.Period
import java.util.*


class MasterSetup() : BaseTest() {
    private lateinit var cmm011a: Cmm011aPage
    private lateinit var cmm029a: Cmm029aPage
    private lateinit var cmm014: Cmm014Page
    private lateinit var cmm013a: Cmm013aPage
    private lateinit var cmm008a: Cmm008aPage
    private lateinit var kmk007: Kmk007Page
    private lateinit var kmk003: Kmk003Page
    private lateinit var kmk012: Kmk012Page
    private lateinit var kmf001: Kmf001Page
    private lateinit var kmf003: Kmf003Page
    private lateinit var cas005: Cas005Page
    private lateinit var cas009: Cas009Page

    @Test(groups = [LOGIN_DEFAULT], dataProvider = "WORK_SETTING_DATA", dataProviderClass = MasterDataProvider::class)
    fun step001_cmm029_workSetting(workSettingList: WorkSettingList) {
        cmm029a = createInstance(Cmm029aPage::class.java)
        cmm029a.openPageUrl(domain + PathList.CMM029A.value)
        Assert.assertEquals(cmm029a.registerWorkSetting(workSettingList), "Msg_15")
    }

    @Test(description = "test_002_KMK007_勤務種類の登録", dataProvider = "WORKTYPE_DATA", dataProviderClass = MasterDataProvider::class)
    fun step002_kmk007_registerWorktype(workTypeList: ListWorktype) {
        kmk007 = createInstance(Kmk007Page::class.java)
        kmk007.openPageUrl(domain + PathList.KMK007.value)
        kmk007.deleteWorktypeIfExisted( workTypeList.items.map { it.code } )
        for (workType in workTypeList.items) {
            Assert.assertEquals(kmk007.registerWorktype(workType), "Msg_15")
        }
    }

    @Test(description = "test_003_KMK003_就業時間帯の登録", dataProvider = "KMK003_MASTER_DATA", dataProviderClass = MasterDataProvider::class)
    fun step003_kmk003(workTimeList: ListWorkTime) {
        kmk003 = createInstance(Kmk003Page::class.java)
        kmk003.openPageUrl(domain + PathList.KMK003.value)
        kmk003.deleteWorkTimeIfExisted(workTimeList.items.map { it.code!! })
        for (workTime in workTimeList.items) {
            Assert.assertEquals(kmk003.registerWorktime(workTime), "Msg_15")
        }
    }

    @Test(dataProvider = "CLASSIFICATION_DATA", dataProviderClass = MasterDataProvider::class, description = "test_004_CMM014-分類情報の登録"
    )
    fun step004_cmm014_registerClassification(classificationList: ListClassification) {
        cmm014 = createInstance(Cmm014Page::class.java)
        cmm014.openPageUrl(domain + PathList.CMM014.value)
        cmm014.deleteClassificationIfExisted(classificationList.items.map { it.code })
        classificationList.items.forEach {
            Assert.assertEquals(cmm014.registerClassification(it), "Msg_15")
        }
    }

    @Test(dataProvider = "EMPLOYMENT_DATA", dataProviderClass = MasterDataProvider::class, description = "test_005_CMM008-雇用情報の登録")
    fun step005_cmm008_registerEmployment(listEmployment: ListEmployment) {
        cmm008a = createInstance(Cmm008aPage::class.java)
        cmm008a.openPageUrl(domain + PathList.CMM008A.value)
        val existedEmpList: List<String> = cmm008a.getExistedEmploymentCodeList(listEmployment.items.map{ it.code })
        listEmployment.items.forEach {
            Assert.assertEquals(cmm008a.registerEmployment(it, existedEmpList.contains(it.code)), "Msg_15")
        }
    }

    @Test(dataProvider = "POSITION_DATA", dataProviderClass = MasterDataProvider::class, description = "test_006_CMM013-職位情報の登録")
    fun step006_cmm013_registerPosition(positionList: ListPosition) {
        cmm013a = createInstance(Cmm013aPage::class.java)
        cmm013a.openPageUrl(domain + PathList.CMM013A.value)
        val existedPstList : List<String> = cmm013a.getExistedPosition(positionList.items.map {it.code})
        positionList.items.forEach {
            Assert.assertEquals(cmm013a.registerPosition(it, existedPstList.contains(it.code)), "Msg_15")
        }
    }

    @Test(groups = [LOGIN_DEFAULT], dataProvider = "WORKPLACE_DATA", dataProviderClass = MasterDataProvider::class)
    fun step007_cmm011_addWorkplace(period: Period, workplaceList: ListWorkplace) {
        cmm011a = createInstance(Cmm011aPage::class.java)
        cmm011a.openPageUrl(Companion.domain + PathList.CMM011A.value)
        val screenStatus = cmm011a.handleStartScreen()
        // 1. Thêm lịch sử và workplace đầu tiên
        val actualMsgIdList = cmm011a.addHistory(period.start, workplaceList.items[0], screenStatus)
        Assert.assertEquals(actualMsgIdList[0], "Msg_373")
        Assert.assertEquals(actualMsgIdList[1], "Msg_15")
        // 2. Thêm các workplace còn lại
        for (i in 1 until workplaceList.items.size) {
            Assert.assertEquals(cmm011a.addWorkplace(workplaceList.items[i]), "Msg_15")
        }
    }

    @Test(description = "test_008_KMK012_処理年月の設定", dataProvider = "KMK012_CLOSURE_DATA", dataProviderClass = MasterDataProvider::class)
    fun step008_kmk012_closureSetting(listClosure: ListClosure, listClosureForEmployment: ListClosureForEmployment) {
        kmk012 = createInstance(Kmk012Page::class.java)
        kmk012.openPageUrl(domain + PathList.KMK012.value)
        for (closureData in listClosure.items) {
            kmk012.registerClosureData(closureData)
            Assert.assertEquals(kmk012.verifyRegisterSuccess(), "Msg_15")
        }
        kmk012.openDialogAssignClosureForEmployment()
        for ((employmentCode, closureCode) in listClosureForEmployment.items) {
            kmk012.assignClosureForEmployment(employmentCode, closureCode)
        }
        kmk012.clickToRegisterAssignButton()
        Assert.assertEquals(kmk012.verifyRegisterSuccess(), "Msg_15")
        kmk012.closeDialogAssignClosureForEmployment()
    }

    @Test(description = "test_009_KMF001_休暇の設定 - 年休付与の登録", dataProvider = "KMF003_ANNUAL_VACATION_DATA", dataProviderClass = MasterDataProvider::class)
    fun step009_kmf001_kmf003_annualVacationSetting(annualVacation: AnnualVacation) {
        kmf001 = createInstance(Kmf001Page::class.java)
        kmf001.openPageUrl(domain + PathList.KMF001.value)
        kmf001.clickToRegisterAnnualVacation()
        kmf003 = createInstance(Kmf003Page::class.java)
        val annualVacationCode = listOf(annualVacation.code)
        kmf003.deleteAnnualVacationIfExisted(annualVacationCode)
        Assert.assertEquals(kmf003.registerAnnualVacation(annualVacation), "Msg_15")
        Assert.assertEquals(kmf003.registerGrantTime(annualVacation.frameList!!, annualVacation.grantTimesList!!), "Msg_15")
        kmf003.closeDialog()
    }

    @Test(description = "test_010_cas005_ロールの登録(就業)", dataProvider = "CAS005_ROLE_DATA", dataProviderClass = MasterDataProvider::class)
    fun step010_cas005_registerRole(role: GeneralRole) {
        cas005 = createInstance(Cas005Page::class.java)
        cas005.openPageUrl(domain + PathList.CAS005.value)
        cas005.clickTab(role.titleTextRsId)
        val isExist: Boolean? = cas005.checkCodeGridIsExist(role.code!!.value!!)
        if (isExist == true) {
            cas005.selectRowEdit(role.code)
            cas005.clickButtonDelete()
            cas005.getMessageResult()
        }
        cas005.clickTab(role.titleTextRsId)
        cas005.clickButtonNew()
        cas005.inputCode(role.code)
        cas005.inputName(role.name!!)
        cas005.selectComboEmployeeRefRange(role.employeeReferenceRange!!)
        cas005.selectApprovalAuthority(role.approvalAuthority!!)
        cas005.clickButtonSave()
        Assert.assertEquals(cas005.getMessageResult(), "Msg_15")
    }

    @Test(description = "test_011_cas009_ロールの登録(個人情報)", dataProvider = "CAS009_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step011_cas009(role: RoleInformation) {
        cas009 = createInstance(Cas009Page::class.java)
        cas009.openPageUrl(domain + PathList.CAS009.value)
        cas009.clickTabGeneral()
        if (cas009.checkCodeGridIsExist(role.code!!) == true) {
            cas009.selectRowEdit(role.code!!)
        }
        cas009.clickTabGeneral()
        cas009.clickButtonNew()
        cas009.inputCode(role.code!!)
        cas009.inputName(role.name)
        cas009.selectComboBox(role.employeeRefRange)
        cas009.selectCheckBoxInTable(role.advancedSetting!!)
        cas009.clickButtonSave()
        Assert.assertEquals(cas009.getMessageResult(), "Msg_15")
    }

}