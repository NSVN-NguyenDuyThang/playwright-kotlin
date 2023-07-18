package uk.business.master

import org.testng.Assert
import org.testng.annotations.Test
import shou.BaseTest
import shou.page.web.cas001.Cas001Page
import shou.page.web.cas001.Category
import shou.page.web.cas001.DataCas001
import shou.page.web.cas005.Cas005Page
import shou.page.web.cas005.GeneralRole
import shou.page.web.cas009.Cas009Page
import shou.page.web.cas009.RoleInformation
import shou.page.web.cas011.Cas011Page
import shou.page.web.cas011.DataRegister
import shou.page.web.cas013.Cas013Page
import shou.page.web.cas014.Cas014Page
import shou.page.web.cas014.Position
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
import shou.page.web.cps001.CategorySetting
import shou.page.web.cps001.Cps001Page
import shou.page.web.cps002.Cps002Page
import shou.page.web.cps002.EmployeeSettingList
import shou.page.web.kaf022.Approve
import shou.page.web.kaf022.Kaf022Page
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
import shou.page.web.ksm004.CalendarRegistration
import shou.page.web.ksm004.Ksm004Page
import shou.page.web.ksm005.Ksm005Page
import shou.page.web.ksm005.MonthlyPattern
import shou.page.web.ksm006.*
import shou.path.PathList
import shou.utils.DataFaker
import shou.utils.model.Period


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
    private lateinit var cas011: Cas011Page
    private lateinit var cas014: Cas014Page
    private lateinit var ksm005: Ksm005Page
    private lateinit var cps002: Cps002Page
    private lateinit var cas001: Cas001Page
    private lateinit var cps001: Cps001Page
    private lateinit var cas013: Cas013Page
    private lateinit var ksm004: Ksm004Page
    private lateinit var ksm006: Ksm006Page
    private lateinit var kaf022: Kaf022Page

    @Test(groups = [LOGIN_DEFAULT], dataProvider = "WORK_SETTING_DATA", dataProviderClass = MasterDataProvider::class, description = "test_001_CMM029-機能の選択 - 勤務の設定")
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

    @Test(groups = [LOGIN_DEFAULT], dataProvider = "WORKPLACE_DATA", dataProviderClass = MasterDataProvider::class, description = "test_007_CMM011-職場情報の登録")
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
            cas005.selectRowEdit(role.code!!)
            cas005.clickButtonDelete()
            cas005.getMessageResult()
        }
        cas005.clickTab(role.titleTextRsId)
        cas005.clickButtonNew()
        cas005.inputCode(role.code!!)
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

    @Test(description = "test_012_cas011_ロールセットの登録", dataProvider = "CAS011_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step012_cas011(dataRegister: DataRegister) {
        cas011 = createInstance(Cas011Page::class.java)
        cas011.openPageUrl(domain + PathList.CAS011.value)
        if (cas011.isExistGridRole(dataRegister.code?.value!!)) {
            Assert.assertTrue(true, String.format("コード「%s」は存在します」", dataRegister.code!!.value!!))
        } else {
            cas011.clickButtonNew()
            cas011.inputCode(dataRegister.code!!)
            cas011.inputName(dataRegister.code)
            cas011.selectGrid1(dataRegister.selection?.selectables!!)
            cas011.clickButtonMoveForward()
            cas011.clickButtonSave()
            Assert.assertEquals(cas011.getMessageResult(), "Msg_15")
        }
    }

    @Test(description = "test_013_cas014_ロールセットの付与", dataProvider = "CAS014_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step013_cas014(position: Position) {
        cas014 = createInstance(Cas014Page::class.java)
        cas014.openPageUrl(domain + PathList.CAS014.value)
        cas014.changeComboBoxInGrid(position.positionItems)
        cas014.clickButtonSave()
        Assert.assertEquals(cas014.getMessageResult(), "Msg_15")
    }

    @Test(description = "test_014_ksm005_月間パターンの登録", dataProvider = "KSM005_MASTER", dataProviderClass = MasterDataProvider::class
    )
    fun step014_ksm005(monthlyPattern: MonthlyPattern) {
        ksm005 = createInstance(Ksm005Page::class.java)
        ksm005.openPageUrl(domain + PathList.KSM005.value)
        //check pattern code, if exists then delete
        ksm005.checkExistsPatternCode(monthlyPattern.patternCode?.value!!)
        //Step 2.
        ksm005.clickButtonCreateNew()
        //Step 3
        ksm005.inputCodeName(monthlyPattern.patternCode!!.value!!, monthlyPattern.patternName?.value!!)
        //Step 4
        ksm005.clickButtonRegister()
        //Step 5 and 6
        val messInfoRegister: String = ksm005.getMsgDisplayed()

        //Step 7. open dialog batch setting
        ksm005.clickButtonOpenDialogBatchSetting()

        //Step 8. open dialog kdl003a
        ksm005.clickButtonOpenDialogKLD003A()
        //Step 8.1 and 8.2
        ksm005.selectWorkTypeAndWordTimeAndClickSubmitKDL003(monthlyPattern.workingDay?.value!!, monthlyPattern.workTime?.value!!)
        //Step 9. open dialog holiday kdl002a
        ksm005.clickOpenDialogHolidayKDL002A()
        //Step 9.1 and 9.2
        val nameStepHoliday: String = ksm005.getTextResource("KSM005_30")
        ksm005.selectWorkTypeClickSubmitKDL002A(nameStepHoliday, monthlyPattern.legalHoliday?.value!!)

        //Step 10. open dialog non holiday
        ksm005.clickOpenDialogNonHolidayKDL002A()
        //Step 10.1 and 10.2
        val nameStepNonHoliday: String = ksm005.getTextResource("KSM005_31")
        ksm005.selectWorkTypeClickSubmitKDL002A(nameStepNonHoliday, monthlyPattern.nonLegalHoliday?.value!!)

        //Step 11
        ksm005.clickButtonExecuteBatchSetting()
        Assert.assertEquals(messInfoRegister, "Msg_15")
    }

    @Test(description = "test_015_CPS002_個人情報の新規登録", dataProvider = "CPS002_REGISTER_EMPLOYEE", dataProviderClass = MasterDataProvider::class)
    fun step015_cps002(employeeSettings: EmployeeSettingList) {
        cps002 = createInstance(Cps002Page::class.java)
        cps002.openPageUrl(domain + PathList.CPS002.value)
        employeeSettings.items.forEach { employeeSetting ->
            employeeSetting.cardNo = companyCode + employeeSetting.cardNo
            employeeSetting.loginId = companyCode + employeeSetting.loginId
            Assert.assertEquals(cps002.settingMaster(employeeSetting), cps002.contendActual())
        }
    }

    @Test(description = "test_016_cas001_個人情報アクセス権限の設定", dataProvider = "CAS001_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step016_cas001(setting: DataCas001) {
        cas001 = createInstance(Cas001Page::class.java)
        cas001.openPageUrl(domain + PathList.CAS001.value)
        val general = setting.general
        val person = setting.person
        cas001.selectTab(general!!.titleTextRsId)
        cas001.selectRowGridRole(general.role!!.rowSelect!!)
        general.categorys!!.forEach { (rowSelect, permissionSettingOther, permissionSettingPersonal, usageAuthoritySetting1): Category ->
            cas001.selectRowGridCategory(rowSelect!!)
            cas001.selectRadioPermission(permissionSettingOther!!)
            cas001.selectRadioPermission(permissionSettingPersonal!!)
            cas001.checkAllGridUsageAuthoritySetting(usageAuthoritySetting1!!.selectAll!!)
            usageAuthoritySetting1.settings!!.forEach { cas001.selectRadioGridUsageAuthoritySetting(it) }
            cas001.clickButtonSave()
            Assert.assertEquals(cas001.getMessageResult(), "Msg_15")
        }
        cas001.selectTab(person!!.titleTextRsId)
        cas001.selectRowGridRole(person.role!!.rowSelect!!)
        person.categorys!!.forEach { (rowSelect, permissionSettingOther, permissionSettingPersonal, usageAuthoritySetting1): Category ->
            cas001.selectRowGridCategory(rowSelect!!)
            cas001.selectRadioPermission(permissionSettingOther!!)
            cas001.selectRadioPermission(permissionSettingPersonal!!)
            cas001.checkAllGridUsageAuthoritySetting(usageAuthoritySetting1!!.selectAll!!)
            usageAuthoritySetting1.settings!!.forEach{ cas001.selectRadioGridUsageAuthoritySetting(it) }
            cas001.clickButtonSave()
            Assert.assertEquals(cas001.getMessageResult(), "Msg_15")
        }
    }

    @Test(description = "test_017_CPS001_個人情報の登録", groups = [LOGIN_OTHER], dataProvider = "CPS001_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step017_cps001(categorySetting: CategorySetting?) {
        cps001 = createInstance(Cps001Page::class.java)
        cps001.openPageUrl(domain + PathList.CPS001.value)
        Assert.assertEquals(cps001.settingCategory(categorySetting!!), "Msg_15")
    }

    @Test(groups = [LOGIN_DEFAULT], description = "test_018_cas013_担当ロールの付与", dataProvider = "CAS013_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step018_cas013(dataRegister: shou.page.web.cas013.DataRegister) {
        cas013 = createInstance(Cas013Page::class.java)
        cas013.openPageUrl(domain + PathList.CAS013.value)
        if (cas013.checkCodeGridRoleIsExist(dataRegister.employeeId!!.value!!)) {
            cas013.selectRowGridRole(dataRegister.employeeId!!.value!!)
            cas013.clickButtonDelete()
            cas013.getMessageResult()
        }
        cas013.clickButtonNew()
        cas013.selectInformationOnPersonalChoiceDlg(dataRegister)

        cas013.selectDropdownSystem(dataRegister.system!!)
        cas013.selectDropdownRoll(dataRegister.roll!!)
        cas013.inputPeriod(dataRegister.periodStart!!, dataRegister.periodEnd!!)
        cas013.clickButtonSave()
        Assert.assertEquals(cas013.getMessageResult(), "Msg_15")
    }

    @Test(description = "test_019_ksm004_カレンダーの登録", groups = [LOGIN_OTHER], dataProvider = "KSM004_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step019_ksm004(calendarRegistration: CalendarRegistration) {
        ksm004 = createInstance(Ksm004Page::class.java)
        ksm004.openPageUrl(domain + PathList.KSM004.value)
        val yearMonth: String = DataFaker.getSystemYearMonth() //get system date
        val endDate: String = DataFaker.addMonthsFromSystemDate(6) //get system date + 6 month
        //tab company
        val companySetting = calendarRegistration.items[0]
        ksm004.selectTabCompany()
        ksm004.inputDateTabCompany(yearMonth)
        ksm004.clickOpenDialogKSM004D()
        ksm004.inputDataStartDateAndEndDateDialogKSM004D(yearMonth, endDate)
        ksm004.selectRadioKSM004D(companySetting)
        ksm004.clickButtonSubmitDialogKDL004D()
        val messCompany: String = ksm004.clickButtonSaveCompany()

        //tab workplace
        val wplSetting = calendarRegistration.items[1]
        val workplaceCode = wplSetting.code!!.value
        ksm004.selectTabWorkplace()
        ksm004.selectRowWorkplaceCode(workplaceCode!!)
        ksm004.inputDateTabWorkplace(yearMonth)
        ksm004.clickOpenDialogKSM004DTabWorkplace()
        ksm004.inputDataStartDateAndEndDateDialogKSM004D(yearMonth, endDate)
        ksm004.selectRadioKSM004D(wplSetting)
        ksm004.clickButtonSubmitDialogKDL004D()
        val messWorkplace: String = ksm004.clickButtonSaveWorkplace()

        //tab class
        val classSetting = calendarRegistration.items[2]
        val classCode = classSetting.code!!.value
        ksm004.selectTabClass()
        ksm004.selectRowGridClassification(classCode!!)
        ksm004.inputDateTabClassification(yearMonth)
        ksm004.clickOpenDialogKSM004DTabClass()
        ksm004.inputDataStartDateAndEndDateDialogKSM004D(yearMonth, endDate)
        ksm004.selectRadioKSM004D(classSetting)
        ksm004.clickButtonSubmitDialogKDL004D()
        val messClass: String = ksm004.clickButtonSaveClassification()
        Assert.assertEquals(messCompany, "Msg_15")
        Assert.assertEquals(messWorkplace, "Msg_15")
        Assert.assertEquals(messClass, "Msg_15")
    }

    @Test(description = "test_020_ksm006_基本勤務の登録", groups = [LOGIN_OTHER], dataProvider = "KSM006_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step020_ksm006(registerWork: DataRegisterWork) {
        ksm006 = createInstance(Ksm006Page::class.java)
        ksm006.openPageUrl(domain + PathList.KSM006.value)
        //tab company
        val dataCompany: Company? = registerWork.company
        val workTime: String? = dataCompany?.itemWorkTime?.value
        val workTypeKDL003: String? = dataCompany?.gridSelected?.worktypes?.get(0)?.value
        val workTypeKDL002: String? = dataCompany?.gridSelected?.worktypes?.get(1)?.value //法定休日
        val workTypeKDL002_2: String? = dataCompany?.gridSelected?.worktypes?.get(2)?.value //法定外休日
        ksm006.selectTabCompany()
        ksm006.clickOpenDialogWorkingDayKDL003()
        ksm006.selectWorkTypeAndWordTimeAndClickSubmitKDL003(workTypeKDL003!!, workTime!!)
        ksm006.clickOpenDialogHolidayKDL002()
        ksm006.selectWorkTypeClickSubmitKDL002(ksm006.getTextResource("KSM005_30"), workTypeKDL002!!)
        ksm006.clickOpenDialogNonHolidayKDL002()
        ksm006.selectWorkTypeClickSubmitKDL002(ksm006.getTextResource("KSM005_31"), workTypeKDL002_2!!)
        val messCompany: String = ksm006.clickButtonRegister()!!

        //tab work place
        val dataWorkplace: Workplace? = registerWork.workplace
        val workplaceCode: String? = dataWorkplace?.gridSelected?.code
        val wplWorkTime: String? = dataWorkplace?.itemWorkTime?.value
        val wplWorkTypeKDL003: String? = dataWorkplace?.gridSelected?.worktypes?.get(0)?.value
        val wplWorkTypeKDL002: String? = dataWorkplace?.gridSelected?.worktypes?.get(1)?.value //法定休日
        val wplWorkTypeKDL002_2: String? = dataWorkplace?.gridSelected?.worktypes?.get(2)?.value //法定外休日
        ksm006.clickTabWorkPlace()
        ksm006.selectRowTreGridWorkplaceCode(workplaceCode!!)
        ksm006.clickOpenDialogWorkingDayKDL003()
        ksm006.selectWorkTypeAndWordTimeAndClickSubmitKDL003(wplWorkTypeKDL003!!, wplWorkTime!!)
        ksm006.clickOpenDialogHolidayKDL002()
        ksm006.selectWorkTypeClickSubmitKDL002(ksm006.getTextResource("KSM005_30"), wplWorkTypeKDL002!!)
        ksm006.clickOpenDialogNonHolidayKDL002()
        ksm006.selectWorkTypeClickSubmitKDL002(ksm006.getTextResource("KSM005_31"), wplWorkTypeKDL002_2!!)
        val messWorkplace: String = ksm006.clickButtonRegister()!!

        //tab Classification
        val classification: Classification? = registerWork.classification
        val classificationCode: String? = classification?.gridSelected?.code
        val classWorkTime: String? = classification?.itemWorkTime?.value
        val classWorkTypeKDL003: String? = classification?.gridSelected?.worktypes?.get(0)?.value
        val classWorkTypeKDL002: String? = classification?.gridSelected?.worktypes?.get(1)?.value //法定休日
        val classWorkTypeKDL002_2: String? = classification?.gridSelected?.worktypes?.get(2)?.value //法定外休日
        ksm006.clickTabClassification()
        ksm006.selectRowGridClassification(classificationCode!!)
        ksm006.clickOpenDialogWorkingDayKDL003()
        ksm006.selectWorkTypeAndWordTimeAndClickSubmitKDL003(classWorkTypeKDL003!!, classWorkTime!!)
        ksm006.clickOpenDialogHolidayKDL002()
        ksm006.selectWorkTypeClickSubmitKDL002(ksm006.getTextResource("KSM005_30"), classWorkTypeKDL002!!)
        ksm006.clickOpenDialogNonHolidayKDL002()
        ksm006.selectWorkTypeClickSubmitKDL002(ksm006.getTextResource("KSM005_31"), classWorkTypeKDL002_2!!)
        val messClass: String = ksm006.clickButtonRegister()!!
        Assert.assertEquals(messCompany, "Msg_15")
        Assert.assertEquals(messWorkplace, "Msg_15")
        Assert.assertEquals(messClass, "Msg_15")
    }

    @Test(description = "test_021_KAF022_申請の設定", groups = [LOGIN_OTHER], dataProvider = "KAF022_MASTER", dataProviderClass = MasterDataProvider::class)
    fun step021_kaf022(approve: Approve) {
        kaf022 = createInstance(Kaf022Page::class.java)
        kaf022.openPageUrl(domain + PathList.KAF022.value)
        Assert.assertEquals(kaf022.settingApprove(approve), "Msg_15")
    }

}