package shou.page.web.cmm029

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cmm029Master() : XmlHelper.Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val result = mutableMapOf<String, Any>()
        XmlHelper.normalizeToXmlMapperFormat(document)
        result["workSettingList"] =
            deserializationXmlToObject(
                document.getElementsByTagName("WorkSettingList").item(0),
                WorkSettingList::class.java
            )
        return result as T
    }
}