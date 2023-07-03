package shou.common

import com.microsoft.playwright.*
import io.qameta.allure.Allure
import org.testng.ITestResult
import org.testng.annotations.*
import shou.browser.BrowserManager
import shou.page.mobile.ccg.Ccg007Page
import shou.path.PathList
import java.awt.Toolkit
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

open class BaseTest {
    protected lateinit var playwright: Playwright
    protected lateinit var browser: Browser
    private lateinit var browserContext: BrowserContext
    protected lateinit var page: Page
    private lateinit var ccg007Mobile: Ccg007Page
    @BeforeSuite
    @Parameters("contractCD", "contractPW", "companyCode", "employeeCode", "employeePW", "domain")
    fun beforeSuite(
        contractCode: String,
        contractPW: String,
        companyCode: String,
        employeeCode: String,
        employeePW: String,
        domain: String
    ) {
        Companion.contractCode = contractCode
        Companion.contractPW = contractPW
        Companion.companyCode = companyCode
        Companion.employeeCode = employeeCode
        Companion.employeePW = employeePW
        Companion.domain = domain
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
        browser = BrowserManager.browser(playwright, browserName)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        browserContext = browser.newContext(
            Browser.NewContextOptions().setViewportSize(screenSize.width, screenSize.height)
                .setRecordVideoDir(Paths.get(GlobalConstants.recordVideo))
                .setRecordVideoSize(screenSize.width, screenSize.height)
                .setIsMobile(true)
        )
        page = browserContext.newPage()
        page.navigate(domain + PathList.CCG007.value)
        page.waitForTimeout(5000.0)
    }

    @AfterMethod
    fun afterMethod(result: ITestResult) {
        try {
            if (result.status == ITestResult.FAILURE) {
                println("Test case execution status is FAILURE")
                Allure.addAttachment("Test case execution status is FAILURE", ByteArrayInputStream(page.screenshot()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @AfterClass
    fun closeBrowserAndPlaywrightSessions() {
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

    protected fun loginUkMobile() {
        ccg007Mobile = createInstance<Ccg007Page>(Ccg007Page::class.java)
        page.waitForTimeout(5000.0)
        ccg007Mobile.switchToDeviceMode()
        ccg007Mobile.inputContract(contractCode, contractPW)
        ccg007Mobile.inputCompany(companyCode, employeeCode, employeePW)
    }

    companion object {
        protected lateinit var browserName: String
        protected lateinit var contractCode: String
        protected lateinit var contractPW: String
        protected lateinit var companyCode: String
        protected lateinit var employeeCode: String
        protected lateinit var employeePW: String
        protected lateinit var domain: String
    }
}
