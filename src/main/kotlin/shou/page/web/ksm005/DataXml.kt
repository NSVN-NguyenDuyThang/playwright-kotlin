package shou.page.web.ksm005

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException


class KSM005Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        val data: MutableMap<String, Any> = HashMap()
        XmlHelper.normalizeToXmlMapperFormat(document)
        val monthlyPattern = deserializationXmlToObject(
            document.getElementsByTagName("monthlyPattern").item(0),
            MonthlyPattern::class.java
        )
        data["monthlyPattern"] = monthlyPattern
        return data as T
    }
}