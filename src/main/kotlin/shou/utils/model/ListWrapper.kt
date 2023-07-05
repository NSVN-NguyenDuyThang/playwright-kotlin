package shou.utils.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import lombok.ToString

@ToString
@AllArgsConstructor
@NoArgsConstructor
open class ListWrapper<T> {
    @get:JacksonXmlElementWrapper
    open lateinit var items: List<T>
}