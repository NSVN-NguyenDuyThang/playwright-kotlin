package shou.utils

import org.apache.commons.lang3.RandomStringUtils
import java.time.LocalDate

import java.time.format.DateTimeFormatter







object DataFaker {
    /* Random Alphanumeric */
    fun generateFakeAlphaNumeric(lenght: Int): String {
        val hasLetters = true
        val hasNumbers = true
        return RandomStringUtils.random(lenght, hasLetters, hasNumbers)
    }
    fun addDay(format: String?, dateString: String?, day: Int): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        val localDate = LocalDate.parse(dateString, formatter)
        val plusDay = localDate.plusDays(day.toLong())
        return formatter.format(plusDay)
    }
}