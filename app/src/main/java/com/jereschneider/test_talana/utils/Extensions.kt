package com.jereschneider.test_talana.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.util.regex.Matcher
import java.util.regex.Pattern


val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline body: (T) -> Unit) {
    liveData.observe(this, { body.invoke(it) })
}

fun isEmailValid(email: String): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(email)
    return matcher.matches()
}

fun getDifferenceDate(date : Long) : String {
    var str = "• hace "
    str += if (date/24 > 365){
        if ((date/24/365) > 1) "${(date/24/365).toInt()} años"
        else "${(date/24/365).toInt()} año"
    }
    else if (date > 24){
        if ((date/24) > 1) "${(date/24).toInt()} días"
        else "${(date/24).toInt()} día"
    }
    else{
        if (date > 1) "$date minutos"
        else "$date minuto"
    }

    return str
}