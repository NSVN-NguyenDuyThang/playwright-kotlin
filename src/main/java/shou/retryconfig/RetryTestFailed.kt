package shou.retryconfig

import org.testng.IRetryAnalyzer
import org.testng.ITestResult
import shou.common.GlobalConstants

class RetryTestFailed : IRetryAnalyzer {
    private var retryCount = 0
    override fun retry(result: ITestResult): Boolean {
        if (retryCount < GlobalConstants.retryTest) {
            println("Retry test name: " + result.name + "with: " + (retryCount + 1) + "time(s).")
            retryCount++
            return true
        }
        return false
    }
}
