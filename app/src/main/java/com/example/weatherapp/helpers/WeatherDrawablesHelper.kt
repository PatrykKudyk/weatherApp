package com.example.weatherapp.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.weatherapp.R

class WeatherDrawablesHelper {

    fun getCorrectDrawable(isDay: Int, code: Int, context: Context): Drawable? {
        return if (isDay == 0) {
            return getNightImage(code, context)
        } else {
            return getDayImage(code, context)
        }
    }

    private fun getNightImage(code: Int, context: Context): Drawable? {
        return when (code) {
            1000 -> return context.getDrawable(R.drawable.day_113)
            1003 -> return context.getDrawable(R.drawable.day_116)
            1006 -> return context.getDrawable(R.drawable.day_119)
            1009 -> return context.getDrawable(R.drawable.day_122)
            1030 -> return context.getDrawable(R.drawable.day_143)
            1063 -> return context.getDrawable(R.drawable.day_176)
            1066 -> return context.getDrawable(R.drawable.day_179)
            1069 -> return context.getDrawable(R.drawable.day_182)
            1072 -> return context.getDrawable(R.drawable.day_185)
            1087 -> return context.getDrawable(R.drawable.day_200)
            1114 -> return context.getDrawable(R.drawable.day_227)
            1117 -> return context.getDrawable(R.drawable.day_230)
            1135 -> return context.getDrawable(R.drawable.day_248)
            1147 -> return context.getDrawable(R.drawable.day_260)
            1150 -> return context.getDrawable(R.drawable.day_263)
            1153 -> return context.getDrawable(R.drawable.day_266)
            1168 -> return context.getDrawable(R.drawable.day_281)
            1171 -> return context.getDrawable(R.drawable.day_284)
            1180 -> return context.getDrawable(R.drawable.day_293)
            1183 -> return context.getDrawable(R.drawable.day_296)
            1186 -> return context.getDrawable(R.drawable.day_299)
            1189 -> return context.getDrawable(R.drawable.day_302)
            1192 -> return context.getDrawable(R.drawable.day_305)
            1195 -> return context.getDrawable(R.drawable.day_308)
            1198 -> return context.getDrawable(R.drawable.day_311)
            1201 -> return context.getDrawable(R.drawable.day_314)
            1204 -> return context.getDrawable(R.drawable.day_317)
            1207 -> return context.getDrawable(R.drawable.day_320)
            1210 -> return context.getDrawable(R.drawable.day_323)
            1213 -> return context.getDrawable(R.drawable.day_326)
            1216 -> return context.getDrawable(R.drawable.day_329)
            1219 -> return context.getDrawable(R.drawable.day_332)
            1222 -> return context.getDrawable(R.drawable.day_335)
            1225 -> return context.getDrawable(R.drawable.day_338)
            1237 -> return context.getDrawable(R.drawable.day_350)
            1240 -> return context.getDrawable(R.drawable.day_353)
            1243 -> return context.getDrawable(R.drawable.day_356)
            1246 -> return context.getDrawable(R.drawable.day_359)
            1249 -> return context.getDrawable(R.drawable.day_362)
            1252 -> return context.getDrawable(R.drawable.day_365)
            1255 -> return context.getDrawable(R.drawable.day_368)
            1258 -> return context.getDrawable(R.drawable.day_371)
            1261 -> return context.getDrawable(R.drawable.day_374)
            1264 -> return context.getDrawable(R.drawable.day_377)
            1273 -> return context.getDrawable(R.drawable.day_386)
            1276 -> return context.getDrawable(R.drawable.day_389)
            1279 -> return context.getDrawable(R.drawable.day_392)
            1282 -> return context.getDrawable(R.drawable.day_395)
            else -> return null
        }
    }

    private fun getDayImage(code: Int, context: Context): Drawable? {
        return when (code) {
            1000 -> return context.getDrawable(R.drawable.night_113)
            1003 -> return context.getDrawable(R.drawable.night_116)
            1006 -> return context.getDrawable(R.drawable.night_119)
            1009 -> return context.getDrawable(R.drawable.night_122)
            1030 -> return context.getDrawable(R.drawable.night_143)
            1063 -> return context.getDrawable(R.drawable.night_176)
            1066 -> return context.getDrawable(R.drawable.night_179)
            1069 -> return context.getDrawable(R.drawable.night_182)
            1072 -> return context.getDrawable(R.drawable.night_185)
            1087 -> return context.getDrawable(R.drawable.night_200)
            1114 -> return context.getDrawable(R.drawable.night_227)
            1117 -> return context.getDrawable(R.drawable.night_230)
            1135 -> return context.getDrawable(R.drawable.night_248)
            1147 -> return context.getDrawable(R.drawable.night_260)
            1150 -> return context.getDrawable(R.drawable.night_263)
            1153 -> return context.getDrawable(R.drawable.night_266)
            1168 -> return context.getDrawable(R.drawable.night_281)
            1171 -> return context.getDrawable(R.drawable.night_284)
            1180 -> return context.getDrawable(R.drawable.night_293)
            1183 -> return context.getDrawable(R.drawable.night_296)
            1186 -> return context.getDrawable(R.drawable.night_299)
            1189 -> return context.getDrawable(R.drawable.night_302)
            1192 -> return context.getDrawable(R.drawable.night_305)
            1195 -> return context.getDrawable(R.drawable.night_308)
            1198 -> return context.getDrawable(R.drawable.night_311)
            1201 -> return context.getDrawable(R.drawable.night_314)
            1204 -> return context.getDrawable(R.drawable.night_317)
            1207 -> return context.getDrawable(R.drawable.night_320)
            1210 -> return context.getDrawable(R.drawable.night_323)
            1213 -> return context.getDrawable(R.drawable.night_326)
            1216 -> return context.getDrawable(R.drawable.night_329)
            1219 -> return context.getDrawable(R.drawable.night_332)
            1222 -> return context.getDrawable(R.drawable.night_335)
            1225 -> return context.getDrawable(R.drawable.night_338)
            1237 -> return context.getDrawable(R.drawable.night_350)
            1240 -> return context.getDrawable(R.drawable.night_353)
            1243 -> return context.getDrawable(R.drawable.night_356)
            1246 -> return context.getDrawable(R.drawable.night_359)
            1249 -> return context.getDrawable(R.drawable.night_362)
            1252 -> return context.getDrawable(R.drawable.night_365)
            1255 -> return context.getDrawable(R.drawable.night_368)
            1258 -> return context.getDrawable(R.drawable.night_371)
            1261 -> return context.getDrawable(R.drawable.night_374)
            1264 -> return context.getDrawable(R.drawable.night_377)
            1273 -> return context.getDrawable(R.drawable.night_386)
            1276 -> return context.getDrawable(R.drawable.night_389)
            1279 -> return context.getDrawable(R.drawable.night_392)
            1282 -> return context.getDrawable(R.drawable.night_395)
            else -> return null
        }
    }
}