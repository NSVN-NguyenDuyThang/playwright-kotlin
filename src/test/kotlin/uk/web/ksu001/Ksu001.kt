package uk.web.ksu001

import org.testng.annotations.Test
import shou.BaseTest
import shou.GlobalConstants
import shou.page.web.kdl055.Kdl055Page
import shou.page.web.ksu001.Ksu001aPage
import shou.path.PathList
import java.io.File

class Ksu001() : BaseTest() {
    private lateinit var ksu001: Ksu001aPage
    @Test(groups = [LOGIN_DEFAULT])
    fun test_import_kdl055() {
        ksu001 = createInstance(Ksu001aPage::class.java)
        ksu001.openPageUrl(domain + PathList.KSU001.value)
        ksu001.openKdl055Dialog()
        val kdl055: Kdl055Page = createInstance(Kdl055Page::class.java)
        kdl055.inputFile(GlobalConstants.dataTestPath + File.separator + "スケジュール取り込みテンプレート.xlsx")
    }
}