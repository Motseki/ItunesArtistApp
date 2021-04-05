package com.wednesday.itunes.music.app.util


fun Double.toCurrency(code: String): String {
    return "$this $code"
}