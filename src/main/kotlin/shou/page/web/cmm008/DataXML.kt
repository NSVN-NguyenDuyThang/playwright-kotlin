package shou.page.web.cmm008

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cmm008Master() : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["employmentList"] =
            deserializationXmlToObject(
                document.getElementsByTagName("ListEmployment").item(0),
                ListEmployment::class.java
            )
        return dataMap as T
    }
}