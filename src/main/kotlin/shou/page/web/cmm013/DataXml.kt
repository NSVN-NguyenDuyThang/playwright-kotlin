package shou.page.web.cmm013

import org.w3c.dom.Document
import shou.utils.xml.XmlHelper
import shou.utils.xml.XmlHelper.Required
import shou.utils.xml.XmlHelper.deserializationXmlToObject
import java.io.IOException
import javax.xml.transform.TransformerException

class Cmm013Master : Required {
    @Throws(TransformerException::class, IOException::class)
    override fun <T> readFile(document: Document): T {
        XmlHelper.normalizeToXmlMapperFormat(document)
        val res: MutableMap<String, Any> = HashMap()
        res["listPosition"] = deserializationXmlToObject(
            document.getElementsByTagName("ListPosition").item(0),
            ListPosition::class.java
        )
        return res as T
    }
}
