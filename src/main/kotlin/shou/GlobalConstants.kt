package shou

import java.io.File

object GlobalConstants {
    private val projectPath: String = System.getProperty("user.dir")
    val recordVideo: String = projectPath + File.separator + "record-video"
    val downloadPath: String = projectPath + File.separator + "downloadFile"
    val uploadFile: String = projectPath + File.separator + "uploadFile"
    val dataTestPath: String = projectPath + File.separator + "dataTest"
    val allureReport: String = projectPath + File.separator + "allure-results"
    const val retryTest = 3
    const val longTimeout: Long = 30
    const val shortTimeout: Long = 10
}
