package shou.page.web.kmk003

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException

class Kml003Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["listWorktime"] = deserializationXmlToObject(
            document.getElementsByTagName("ListWorkTime").item(0),
            ListWorkTime::class.java
        )
        return dataMap as T
    }
}
