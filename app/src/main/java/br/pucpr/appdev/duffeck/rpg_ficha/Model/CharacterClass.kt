package br.pucpr.appdev.duffeck.rpg_ficha.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class CharacterClass(var name: String = "", var level: Int = 0) {

    override fun toString(): String {
        return this.name + " nv " + this.level
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "level" to level
        )
    }
}