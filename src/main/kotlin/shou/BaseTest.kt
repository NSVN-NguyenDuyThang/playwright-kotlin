package shou

import com.microsoft.playwright.*
import io.qameta.allure.Allure
import org.testng.ITestResult
import org.testng.annotations.*
import shou.browser.BrowserManager
import shou.browser.PageFactory
import shou.common.mobile.BasePageMobile
import shou.common.model.EmployeeLogin
import shou.common.web.BasePage
import shou.page.mobile.ccg.Ccg007MobilePage
import shou.page.web.ccg007.Ccg007Page
import shou.page.web.cps002.CPS002RegisterEmployee
import shou.page.web.cps002.EmployeeSettingList
import shou.path.PathList
import shou.utils.xml.XmlHelper.readFile
import java.awt.Toolkit
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.lang.reflect.Method
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Function
import java.util.stream.Collectors


open class BaseTest {
    protected lateinit var playwright: Playwright
    protected lateinit var browser: Browser
    private lateinit var browserContext: BrowserContext
    protected lateinit var page: Page
    private lateinit var ccg007Mobile: Ccg007MobilePage
    private  lateinit var ccg007: Ccg007Page
    @BeforeSuite
    @Parameters("contractCD", "contractPW", "companyCode", "employeeCode", "employeePW", "domain", "cloudEnv", "webMode")
    fun beforeSuite(
        contractCode: String,
        contractPW: String,
        companyCode: String,
        employeeCode: String,
        employeePW: String,
        domain: String,
        @Optional("true") cloudEnv: Boolean,
        @Optional("true") webMode: Boolean
    ) {
        Companion.contractCode = contractCode
        Companion.contractPW = contractPW
        Companion.companyCode = companyCode
        Companion.employeeCode = employeeCode
        Companion.employeePW = employeePW
        Companion.domain = domain
        Companion.cloudEnv = cloudEnv
        Companion.webMode = webMode
        /*get account login cps002 */
        val objs: Map<String, Any> = readFile(CPS002RegisterEmployee(), "master", "cps002_register_employee.xml")
        val employeeSettingList: EmployeeSettingList = objs["employeeSettingList"] as EmployeeSettingList
        employees = employeeSettingList.items.map {it.employeeCode!! to EmployeeLogin(companyCode, it.employeeCode!!, it.password!!) }.toMap()
        deleteAllureReport()
        deleteDownloadedFileFromDir()
        deleteRecordVideoFromDir()
    }

    @BeforeTest
    @Parameters("browser")
    fun beforeTest(browserName: String) {
        Companion.browserName = browserName
    }

    @BeforeClass
    fun createPlaywrightAndBrowserInstances() {
        playwright = Playwright.create()
        browser = BrowserManager.browser(playwright, browserName, Companion.webMode)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        browserContext = browser.newContext(
            Browser.NewContextOptions().setViewportSize(screenSize.width, screenSize.height)
                .setRecordVideoDir(Paths.get(GlobalConstants.recordVideo))
                .setRecordVideoSize(screenSize.width, screenSize.height)
                .setIsMobile(true)
        )
        page = browserContext.newPage()
        when(Companion.webMode) {
            true ->  page.navigate(domain + PathList.CCG007.value)
            false ->  page.navigate(domain + PathList.CCG007M.value)
        }
        page.waitForTimeout(5000.0)
        isFirstLoginOfSession = true
    }

    @BeforeMethod
    fun beforeMethod(method: Method) {
        println("Test method: ${method.name}")
        val groups = method.getAnnotation(Test::class.java).groups
        for (group in groups) {
            when (group) {
                LOGIN_DEFAULT -> loginWithDefault()
                LOGIN_OTHER -> {
                    Companion.loginOther ?: throw NullPointerException("Employee login is null")
                    loginWith(Companion.loginOther!!)
                }
            }
        }
    }


    @AfterMethod
    fun afterMethod(result: ITestResult) {
        isFirstLoginOfSession = false
        if (result.status == ITestResult.FAILURE) {
            println("Test case execution status is FAILURE")
            Allure.addAttachment("Test case execution status is FAILURE", ByteArrayInputStream(page.screenshot()))
        }
    }

    @AfterClass
    fun closeBrowserAndPlaywrightSessions() {
        isFirstLoginOfSession = false
        val recordPath = page.video().path()
        page.close()
        browserContext.close()
        attachVideoToAllure(recordPath)
    }

    @AfterSuite
    fun afterSuite() {
        browser.close()
        playwright.close()
    }

    private fun deleteDownloadedFileFromDir() {
        val path = GlobalConstants.downloadPath
        val directory = File(path)
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                file.delete()
            }
        }
    }

    private fun deleteAllureReport() {
        try {
            val pathFolderDownload = GlobalConstants.allureReport
            val file = File(pathFolderDownload)
            val listOfFiles = file.listFiles()
            for (i in listOfFiles.indices) {
                if (listOfFiles[i].isFile && listOfFiles[i].name != "categories.json") {
                    File(listOfFiles[i].toString()).delete()
                }
            }
        } catch (e: Exception) {
            print(e.message)
        }
    }

    private fun deleteRecordVideoFromDir() {
        val path = GlobalConstants.recordVideo
        val directory = File(path)
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                file.delete()
            }
        }
    }

    private fun attachVideoToAllure(recordPath: Path?) {
        try {
            Allure.addAttachment("Recording for class test", "video/webm", Files.newInputStream(recordPath), "webm")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    protected fun <T : BasePageMobile> createInstance(basePage: Class<T>): T {
        return PageFactory.createInstance(page, basePage)
    }

    protected fun<T : BasePage> createInstance(basePage: Class<T>) : T {
        return PageFactory.createInstance(page, basePage)
    }

    protected fun loginUkMobile() {
        ccg007Mobile = createInstance(Ccg007MobilePage::class.java)
        page.waitForTimeout(5000.0)
        ccg007Mobile.switchToDeviceMode()
        ccg007Mobile.inputContract(contractCode, contractPW)
        ccg007Mobile.inputCompany(companyCode, employeeCode, employeePW)
    }

    protected fun loginWithDefault() {
        ccg007 = createInstance(Ccg007Page::class.java)
        ccg007.openPageUrl(Companion.domain + PathList.CCG007.value)
        if (Companion.cloudEnv && Companion.isFirstLoginOfSession) {
            ccg007.contractLogin(Companion.contractCode, Companion.contractPW)
        }
        ccg007.companyLogin(Companion.companyCode, Companion.employeeCode, Companion.employeePW)
    }

    protected fun loginWith(emp: EmployeeLogin) {
        ccg007 = createInstance(Ccg007Page::class.java)
        ccg007.openPageUrl(Companion.domain + PathList.CCG007.value)
        if (Companion.cloudEnv && Companion.isFirstLoginOfSession) {
            ccg007.contractLogin(Companion.contractCode, Companion.contractPW)
        }
        ccg007.companyLogin(emp.companyCD, emp.employeeCD, emp.employeePW)
    }

    companion object {
        protected lateinit var browserName: String
        protected lateinit var contractCode: String
        protected lateinit var contractPW: String
        @JvmStatic
        protected lateinit var companyCode: String
        protected lateinit var employeeCode: String
        protected lateinit var employeePW: String
        internal lateinit var domain: String
        protected var cloudEnv: Boolean = true
        protected var webMode: Boolean = true
        private var isFirstLoginOfSession = false
        const val LOGIN_DEFAULT: String = "LOGIN_DEFAULT"
        const val LOGIN_OTHER: String = "LOGIN_OTHER"

        /*key: employeeCode*/
        internal var employees: Map<String, EmployeeLogin> = HashMap()
        internal var loginOther: EmployeeLogin? = null
    }
}
