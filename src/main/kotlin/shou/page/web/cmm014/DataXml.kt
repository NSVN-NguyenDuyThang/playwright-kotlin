package shou.page.web.cmm014

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cmm014Master() : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["classificationList"] =
            deserializationXmlToObject(
                document.getElementsByTagName("ListClassification").item(0),
                ListClassification::class.java
            )
        return dataMap as T
    }
}