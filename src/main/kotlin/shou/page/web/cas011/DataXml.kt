package shou.page.web.cas011

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cas011Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap = mutableMapOf<String, Any>()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val cas011: DataCas011 = deserializationXmlToObject(document.getElementsByTagName("DataCas011").item(0), DataCas011::class.java)
        dataMap["dataRegister"] = cas011
        return dataMap as T
    }
}