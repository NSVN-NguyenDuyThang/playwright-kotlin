package shou.page.web.kaf022

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class KAF002Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val res: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        res["approve"] = deserializationXmlToObject(document.getElementsByTagName("Approve").item(0), Approve::class.java)
        res["employeeLoginCD"] = deserializationXmlToObject(document.getElementsByTagName("EmployeeLoginCD").item(0), String::class.java)
        return res as T
    }
}