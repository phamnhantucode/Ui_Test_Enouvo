package com.phamnhantucode.uitestenouvo

import java.text.NumberFormat
import java.util.*

object Utils {
    fun numberCovert(number: Long): String {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
}