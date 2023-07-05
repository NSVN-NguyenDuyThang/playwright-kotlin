package shou.page.web.cmm014

import com.microsoft.playwright.Locator
import io.qameta.allure.Step
import shou.common.web.BasePage
import shou.common.web.CommonUI

class Cmm014Page() : BasePage() {
    @Step("情報分類の登録「{0}」")
    fun registerClassification(classification: Classification): String? {
        clickToButton("CMM014_1") // create new
        inputInformation(classification)
        clickToButton("CMM014_2") // register
        return getAndCloseMsgInfo()
    }

    @Step("[前提条件] 分類が存在する場合は削除します。")
    fun deleteClassificationIfExisted(clfList: List<String?>) {
        val clfRows : Locator = page.locator(CLASSIFICATION_ITEM_LIST)
        clfRows.all().map { it.getAttribute("data-id") }
            .filter { clfList.contains(it) }
            .forEach { deleteClassification(it) }
    }

    @Step("分類の削除 「{0}」")
    private fun deleteClassification(code: String): String? {
        chooseClassification(code)
        clickToButton("CMM014_4") //delete button
        messageConfirmYes("Msg_18")
        return getAndCloseMsgInfo()
    }

    @Step("入力分類")
    fun inputInformation(classification: Classification) {
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "clfCode"), classification.code, "コード")
        fillToElement(String.format(CommonUI.INPUT_BY_ID, "clfName"), classification.name, "名称")
        if (classification.memo != null) {
            fillToElement(String.format(CommonUI.INPUT_BY_ID, "memo"), classification.memo, "メモ")
        }
    }

    @Step("「コード={0}」の分類を選択する")
    fun chooseClassification(code: String) {
        clickToElement(String.format(CLASSIFICATION_ITEM, code))
        takeScreenshot("「コード=$code」の分類を選択する")
    }

    companion object {
        private const val CLASSIFICATION_ITEM_LIST = "//tr[@data-id]"
        private const val CLASSIFICATION_ITEM = "//tr[@data-id='%s']"
    }

}