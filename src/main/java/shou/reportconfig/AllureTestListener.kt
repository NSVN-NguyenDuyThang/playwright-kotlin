package shou.reportconfig

import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

class AllureTestListener : ITestListener {
    override fun onTestFailure(iTestResult: ITestResult?) {}
    override fun onStart(iTestContext: ITestContext?) {}
    override fun onTestSkipped(iTestResult: ITestResult?) {}
    override fun onTestFailedButWithinSuccessPercentage(iTestResult: ITestResult?) {}
    override fun onFinish(arg0: ITestContext?) {}
    override fun onTestStart(arg0: ITestResult?) {}
    override fun onTestSuccess(arg0: ITestResult?) {}
}