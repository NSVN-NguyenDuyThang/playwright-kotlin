package uk.mobile.kaf

import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import shou.common.BaseTest
import shou.common.model.PeriodTime
import shou.page.mobile.kaf.KafS02Page
import shou.page.mobile.kaf.model.DivisionType
import shou.page.mobile.kaf.model.StampApplication

class KafS02 : BaseTest() {
    private lateinit var kafS02: KafS02Page
    @BeforeClass
    fun prepare() {
        loginUkMobile()
        kafS02 = createInstance(KafS02Page::class.java)
    }

    @Test
    fun test_01_Start() {
        kafS02.openScreen()
    }

    @Test
    fun test_02_RegisterApplication() {
        kafS02.openScreen()
        val stampApplication = StampApplication(DivisionType.ADVANCE, "2023-06-13", PeriodTime("8:30", "17:30"), "memo")
        kafS02.registerApplication(stampApplication)
    }
}
