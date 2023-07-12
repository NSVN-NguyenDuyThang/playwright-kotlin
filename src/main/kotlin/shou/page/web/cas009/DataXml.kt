package shou.page.web.cas009

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cas009Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val generalRoleInformation: General = deserializationXmlToObject(
            document.getElementsByTagName("general").item(0),
            General::class.java
        )
        dataMap["generalRoleInformation"] = generalRoleInformation
        return dataMap as T
    }
}