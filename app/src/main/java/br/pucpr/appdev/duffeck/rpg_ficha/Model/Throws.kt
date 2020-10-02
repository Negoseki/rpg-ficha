package br.pucpr.appdev.duffeck.rpg_ficha.Model

import com.google.firebase.database.Exclude

class Throws(
    var throw1: Boolean = false,
    var throw2: Boolean = false,
    var throw3: Boolean = false
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "throw1" to throw1,
            "throw2" to throw2,
            "throw3" to throw3
        )
    }
}