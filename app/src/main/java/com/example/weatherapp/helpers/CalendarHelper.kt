package com.example.weatherapp.helpers

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CalendarHelper {

    fun getDateTimeFromString(date: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return LocalDateTime.parse(date, formatter)
    }

    fun getDateFromString(date: String): LocalDate{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(date, formatter)
    }

    fun getDayOfWeekFromSDateInString(date: String): String {
        val localDate = getDateFromString(date)
        var dayName = localDate.dayOfWeek.toString().lowercase()
        dayName = dayName.substring(0, 1).uppercase() + dayName.substring(1)
        return dayName
    }
}