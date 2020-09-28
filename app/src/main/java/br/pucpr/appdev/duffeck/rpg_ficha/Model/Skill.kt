package br.pucpr.appdev.duffeck.rpg_ficha.Model

import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Skill(
    var key: String,
    var name: String,
    val abilityScore: AbilityScoreEnum
) {
    var bonus: Int = 0
    var isProeficiency: Boolean = false


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "key" to key,
            "name" to name,
            "abilityScore" to abilityScore,
            "bonus" to bonus,
            "isProeficiency" to isProeficiency
        )
    }
}

//enum class ExpertiseEnum(val value: String) : Expertise {
//    ACROBACY("acrobacy")
//    ARCANISMO(INT)
//    ATLETISMO (FOR)
//    ATUAÇÃO (CAR)
//    BLEFAR (CAR)
//    FURTIVIDADE (DES)
//    HISTÓRIA (INT)
//    INTIMIDAÇÃO (CAR)
//    INTUIÇÃO (SAB)
//    INVESTIGAÇÃO (INT)
//    LIDAR COM ANIMAIS (SAB)
//    MEDICINA (SAB)
//    NATUREZA (INT)
//    PERCEPÇÃO (SAB)
//    PERSUASÃO (CAR)
//    PRESTIDIGITAÇÃO (DES)
//    RELIGIÃO (INT)
//    SOBREVIVÊNCIA (SAB)
//}
