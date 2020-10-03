package br.pucpr.appdev.duffeck.rpg_ficha.Model

import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.CharacterClassEnum
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class CharacterClass(var name: String = "", var level: Int = 0) {

    override fun toString(): String {
        var classe = this.name;
        CharacterClassEnum.values().find { e -> e.value == name }?.let {
            classe = it.className;
        }
        return classe + " / " + this.level
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "level" to level
        )
    }
}