package shou.utils

import org.apache.commons.lang3.RandomStringUtils




object DataFaker {
    /* Random Alphanumeric */
    fun generateFakeAlphaNumeric(lenght: Int): String {
        val hasLetters = true
        val hasNumbers = true
        return RandomStringUtils.random(lenght, hasLetters, hasNumbers)
    }
}