package shou.page.web.ksm006

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class KSM006Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val dataMap: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val company = deserializationXmlToObject(
            document.getElementsByTagName("company").item(0),
            Company::class.java
        )
        val workplace = deserializationXmlToObject(
            document.getElementsByTagName("workplace").item(0),
            Workplace::class.java
        )
        val classification = deserializationXmlToObject(
            document.getElementsByTagName("classification").item(0),
            Classification::class.java
        )
        val employeeLoginCD = deserializationXmlToObject(
            document.getElementsByTagName("employeeLoginCD")
                .item(0), String::class.java
        )
        dataMap["company"] = company
        dataMap["workplace"] = workplace
        dataMap["classification"] = classification
        dataMap["employeeLoginCD"] = employeeLoginCD
        return dataMap as T
    }
}

