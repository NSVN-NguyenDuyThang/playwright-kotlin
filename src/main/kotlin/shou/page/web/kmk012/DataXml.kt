package shou.page.web.kmk012

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Kmk012Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val closureList = deserializationXmlToObject(
            document.getElementsByTagName(ListClosure::class.java.simpleName).item(0),
            ListClosure::class.java
        )
        val closureForEmploymentList: ListClosureForEmployment = deserializationXmlToObject(
            document.getElementsByTagName(
                ListClosureForEmployment::class.java.getSimpleName()
            ).item(0), ListClosureForEmployment::class.java
        )
        dataMap["closureList"] = closureList
        dataMap["closureForEmploymentList"] = closureForEmploymentList
        return dataMap as T
    }
}