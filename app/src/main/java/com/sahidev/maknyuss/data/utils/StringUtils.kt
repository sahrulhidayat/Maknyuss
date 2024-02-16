package com.sahidev.maknyuss.data.utils

import java.util.Locale

fun String.trimTrailingZero(): String {
    return this.trimEnd { it == '0' }
        .trimEnd { it == '.' }
        .trimEnd { it == ',' }
}

fun String.capitalize(): String {
    return this.replaceFirstChar { char ->
        if (char.isLowerCase()) char.titlecase(Locale.getDefault())
        else char.toString()
    }
}