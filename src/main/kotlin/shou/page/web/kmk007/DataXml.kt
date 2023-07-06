package shou.page.web.kmk007

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Kmk007Master() : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["workTypeList"] =
            deserializationXmlToObject(
                document.getElementsByTagName(ListWorktype::class.java.simpleName).item(0),
                ListWorktype::class.java
            )
        return dataMap as T
    }
}