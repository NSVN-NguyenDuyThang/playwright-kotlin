package shou.page.web.kdl055

import com.microsoft.playwright.FileChooser
import com.microsoft.playwright.FrameLocator
import shou.common.web.BasePage
import java.nio.file.Paths

class Kdl055Page() : BasePage() {

    private val kdl055Dlg: FrameLocator
        get() = getFrame(getTextResource("KDL055_1"))
    fun inputFile(path: String) {
        highlightElement(kdl055Dlg.locator("#file-upload"))
        val fileChooser: FileChooser = page.waitForFileChooser(Runnable { clickToElement(kdl055Dlg.locator("button.browser-button")) })
        fileChooser.setFiles(Paths.get(path))
        page.waitForTimeout(5000.0)
    }
}