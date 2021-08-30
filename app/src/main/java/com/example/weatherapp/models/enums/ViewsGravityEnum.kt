package com.example.weatherapp.models.enums

enum class ViewsGravityEnum {
    START   ,
    CENTER,
    END;


    companion object {
        fun fromInt(value: Int) = ViewsGravityEnum.values().first { it.ordinal == value}
    }
}