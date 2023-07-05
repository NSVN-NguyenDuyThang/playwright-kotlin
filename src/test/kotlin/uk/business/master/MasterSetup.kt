package uk.business.master

import org.testng.Assert
import org.testng.annotations.Test
import shou.BaseTest
import shou.page.web.cmm011.Cmm011aPage
import shou.page.web.cmm011.ListWorkplace
import shou.page.web.cmm014.Cmm014Page
import shou.page.web.cmm014.ListClassification
import shou.page.web.cmm029.Cmm029aPage
import shou.page.web.cmm029.WorkSettingList
import shou.path.PathList
import shou.utils.model.Period

class MasterSetup() : BaseTest() {
    private lateinit var cmm011a: Cmm011aPage
    private lateinit var cmm029a: Cmm029aPage
    private lateinit var cmm014: Cmm014Page

    @Test(groups = [LOGIN_DEFAULT], dataProvider = "WORK_SETTING_DATA", dataProviderClass = MasterDataProvider::class)
    fun step001_cmm029_workSetting(workSettingList: WorkSettingList) {
        cmm029a = createInstance(Cmm029aPage::class.java)
        cmm029a.openPageUrl(domain + PathList.CMM029A.value)
        Assert.assertEquals(cmm029a.registerWorkSetting(workSettingList), "Msg_15")
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