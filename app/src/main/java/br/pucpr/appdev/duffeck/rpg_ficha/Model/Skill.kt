package br.pucpr.appdev.duffeck.rpg_ficha.Model

import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum


class Skill(
    var key: String,
    var name: String,
    val abilityScore: AbilityScoreEnum
) {
    var bonus: Int = 0
    var isProeficiency: Boolean = false

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
