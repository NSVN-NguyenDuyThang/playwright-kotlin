package shou.page.web.cas001

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Cas001Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val dataCas001 = deserializationXmlToObject(
            document.getElementsByTagName("DataCas001").item(0),
            DataCas001::class.java
        )
        dataMap["dataCas001"] = dataCas001
        return dataMap as T
    }
}

