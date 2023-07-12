package shou.page.web.cps001

import org.w3c.dom.Document
import org.w3c.dom.NodeList
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class CPS001Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val res: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        var settingList = CategorySetting()
        var employees = EmployeeList()
        val nodeListEmployee: NodeList = document.getElementsByTagName("Employee")
        val nodeList: NodeList = document.getElementsByTagName("itemSetting")
        for (i in 0 until nodeList.length) {
            settingList = deserializationXmlToObject(nodeList.item(i), CategorySetting::class.java)
        }
        for (i in 0 until nodeListEmployee.length) {
            employees = deserializationXmlToObject(nodeListEmployee.item(i), EmployeeList::class.java)
        }
        res["categorySetting"] = settingList
        res["employees"] = employees
        res["employeeLoginCD"] = deserializationXmlToObject(document.getElementsByTagName("EmployeeLoginCD").item(0), String::class.java)
        return res as T
    }
}

