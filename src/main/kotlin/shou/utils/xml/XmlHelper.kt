package shou.utils.xml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.xml.sax.SAXException
import shou.GlobalConstants
import java.io.File
import java.io.IOException
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class XmlHelper(private val xmlMapper: XmlMapper = XmlMapper()) {
    @Throws(SAXException::class, IOException::class, ParserConfigurationException::class, TransformerException::class)
    fun <T> readFile(required: Required, vararg pathToFile: String): T {
        var filePath: String = GlobalConstants.dataTestPath
        for (path in pathToFile) {
            filePath += File.separator + path
        }
        val inputFile = File(filePath)
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc: Document = dBuilder.parse(inputFile)
        doc.documentElement.normalize()
        return required.readFile(doc)
    }

    fun normalizeNodeToXmlMapperFormat(document: Document, root: Node?): Document {
        recursiveNode(document, root)
        return document
    }

    fun normalizeToXmlMapperFormat(document: Document): Document {
        recursiveNode(document, document.firstChild)
        return document
    }

    private fun recursiveNode(document: Document, root: Node?) {
        if (root != null) {
            //handle
            if (root is Element) {
                val node: Node = root
                val attrMap: NamedNodeMap? = node.attributes
                if (attrMap != null) {
                    val attrNameList = mutableListOf<String>()
                    // append node
                    for (j in 0 until attrMap.length) {
                        val attrName: String = attrMap.item(j).nodeName
                        attrNameList.add(attrName)
                        val attrValue: String = attrMap.item(j).nodeValue
                        val subNode: Element = document.createElement(attrName)
                        subNode.appendChild(document.createTextNode(attrValue))
                        node.appendChild(subNode)
                    }
                    //remove attribute
                    val el: Element = node as Element
                    for (attrName in attrNameList) {
                        el.removeAttribute(attrName)
                    }
                }
            }
            recursiveNode(document, root.firstChild)
            recursiveNode(document, root.nextSibling)
        }
    }


    @Throws(TransformerException::class, IOException::class)
    fun <T> deserializationXmlToObject(node: Node?, resultType: Class<T>): T {
        val tf = TransformerFactory.newInstance()
        val transformer = tf.newTransformer()
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        val writer = StringWriter()
        transformer.transform(DOMSource(node), StreamResult(writer))
        println(writer.buffer.toString())
        return xmlMapper.readValue(writer.buffer.toString(), resultType)
    }


    interface Required {
        @Throws(TransformerException::class, IOException::class)
        fun <T> readFile(document: Document?): T
    }

}