package shou.page.web.cas013

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cas013Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val cas013: DataRegister = deserializationXmlToObject(document.getElementsByTagName("DataRegister").item(0), DataRegister::class.java)
        dataMap["dataRegister"] = cas013
        return dataMap as T
    }
}

