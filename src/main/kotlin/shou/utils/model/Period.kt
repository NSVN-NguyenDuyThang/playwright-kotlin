package shou.utils.model

import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
class Period {
    lateinit var start: String
    var end: String? = null
}