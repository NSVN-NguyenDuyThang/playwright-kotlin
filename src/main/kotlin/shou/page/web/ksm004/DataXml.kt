package shou.page.web.ksm004

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class KSM004Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val calendarRegistration = deserializationXmlToObject(
            document.getElementsByTagName("calendarRegistration")
                .item(0), CalendarRegistration::class.java
        )
        val employeeLoginCD = deserializationXmlToObject(
            document.getElementsByTagName("employeeLoginCD")
                .item(0), String::class.java
        )
        dataMap["calendarRegistration"] = calendarRegistration
        dataMap["employeeLoginCD"] = employeeLoginCD
        return dataMap as T
    }
}

