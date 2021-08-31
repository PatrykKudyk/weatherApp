package com.example.weatherapp.helpers

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalendarHelper {

    fun getDateTimeFromString(date: String): LocalDateTime {
        var formatter = when (date.length) {
            16 -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            15 -> DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
            else -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        }
        return LocalDateTime.parse(date, formatter)
    }

    private fun getDateFromString(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(date, formatter)
    }

    fun getDayOfWeekFromSDateInString(date: String): String {
        val localDate = getDateFromString(date)
        var dayName = localDate.dayOfWeek.toString().lowercase()
        dayName = dayName.substring(0, 1).uppercase() + dayName.substring(1)
        return dayName
    }

    fun getTimeFromDate(date: String): String {
        val time = getDateTimeFromString(date)
        return "${time.hour}:${time.minute}"
    }
}