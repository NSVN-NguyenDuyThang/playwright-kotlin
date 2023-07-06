package uk.business.master

import org.testng.Assert
import org.testng.annotations.Test
import shou.BaseTest
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
import shou.page.web.kmk003.Kmk003Page
import shou.page.web.kmk003.ListWorkTime
import shou.page.web.kmk003.WorkTime
import shou.page.web.kmk007.Kmk007Page
import shou.page.web.kmk007.ListWorktype
import shou.path.PathList
import shou.utils.model.Period
import java.util.stream.Collectors


class MasterSetup() : BaseTest() {
    private lateinit var cmm011a: Cmm011aPage
    private lateinit var cmm029a: Cmm029aPage
    private lateinit var cmm014: Cmm014Page
    private lateinit var cmm013a: Cmm013aPage
    private lateinit var cmm008a: Cmm008aPage
    private lateinit var kmk007: Kmk007Page
    private lateinit var kmk003: Kmk003Page

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

    @Test(dataProvider = "WORKPLACE_DATA", dataProviderClass = MasterDataProvider::class)
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
}