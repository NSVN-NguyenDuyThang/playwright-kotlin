package shou.page.web.cps002

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class CPS002RegisterEmployee() : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        dataMap["employeeSettingList"] =
            deserializationXmlToObject(
                document.getElementsByTagName("EmployeeSettingList").item(0),
                EmployeeSettingList::class.java
            )
        return dataMap as T
    }
}