package shou.page.web.cas005

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cas005Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["listDataRegister"] = deserializationXmlToObject(document.getElementsByTagName("ListDataRegister").item(0), ListDataRegister::class.java)
        return dataMap as T
    }
}