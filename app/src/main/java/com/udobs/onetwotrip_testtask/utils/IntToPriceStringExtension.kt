package com.udobs.onetwotrip_testtask.utils

fun Int.toPriceString(): String {
    val x = this.toString().reversed()
    val s: StringBuilder = java.lang.StringBuilder("")
    x.mapIndexed { index, c ->
        if ((index) % 3 == 0) {
            s.append(" ")
        }
        s.append(c)
    }
    return s.toString().reversed().trim()
}
