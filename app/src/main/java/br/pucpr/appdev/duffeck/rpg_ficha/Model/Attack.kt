package br.pucpr.appdev.duffeck.rpg_ficha.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Attack(
    var name: String = "",
    var bonus: Int = 0
) {


/*
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "level" to level
        )
    }
 */
}