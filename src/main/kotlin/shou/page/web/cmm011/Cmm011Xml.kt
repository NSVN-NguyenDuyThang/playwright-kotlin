package shou.page.web.cmm011

import org.w3c.dom.Document
import shou.utils.model.Period
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import java.io.IOException
import javax.xml.transform.TransformerException

class Cmm011Xml : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val result = mutableMapOf<String, Any>()
        XmlHelper.normalizeToXmlMapperFormat(document)
        result["historyDate"] = XmlHelper.deserializationXmlToObject(document.getElementsByTagName("Period").item(0), Period::class.java)
        result["workplaceList"] = XmlHelper.deserializationXmlToObject(document!!.getElementsByTagName("ListWorkplace").item(0), ListWorkplace::class.java)
        return result as T
    }
}
