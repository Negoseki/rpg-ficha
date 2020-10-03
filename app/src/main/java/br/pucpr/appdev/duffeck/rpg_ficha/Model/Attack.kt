package br.pucpr.appdev.duffeck.rpg_ficha.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Attack(
    var name: String = "",
    var bonus: Int = 0,
    var damage: String = "",
    var description: String = ""
) {


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "bonus" to bonus,
            "damage" to damage,
            "description" to description
        )
    }
}