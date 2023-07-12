package shou.page.web.kmf003

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class Kmf003Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["annualVacationList"] = deserializationXmlToObject(
            document.getElementsByTagName(
                ListAnnualVacation::class.java.simpleName
            ).item(0), ListAnnualVacation::class.java
        )
        return dataMap as T
    }
}