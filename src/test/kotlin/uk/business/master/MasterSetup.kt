package uk.business.master

import org.testng.Assert
import org.testng.annotations.Test
import shou.BaseTest
import shou.page.web.cmm011.Cmm011aPage
import shou.page.web.cmm011.ListWorkplace
import shou.path.PathList
import shou.utils.model.Period


class MasterSetup() : BaseTest() {
    private lateinit var cmm011a: Cmm011aPage
    @Test(groups = [LOGIN_DEFAULT], dataProvider = "WORKPLACE_DATA", dataProviderClass = MasterDataProvider::class)
    fun step001_cmm011_addWorkplace(period: Period, workplaceList: ListWorkplace) {
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