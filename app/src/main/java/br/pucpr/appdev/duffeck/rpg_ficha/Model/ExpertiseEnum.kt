package br.pucpr.appdev.duffeck.rpg_ficha.Model


interface Expertise {
    val abilityScore: AbilityScoreEnum
    val sheet: CharacterSheetModel

    fun getExpertiseValue(isProeficiency: Boolean): Int {
        return if (isProeficiency) {
            sheet.getAbiltyScoreModifier(abilityScore) + sheet.proficiencyBonus
        } else {
            sheet.getAbiltyScoreModifier(abilityScore)
        }
    }
}

enum class ExpertiseEnum(val value: String, isProeficiency) : Expertise {
    ACROBACY("acrobacy") {
        override val abilityScore: AbilityScoreEnum
            get() = AbilityScoreEnum.DEXTERITY
        override val sheet: CharacterSheetModel
            get() = TODO("Not yet implemented")
    }
    ARCANISMO (INT)
    ATLETISMO (FOR)
    ATUAÇÃO (CAR)
    BLEFAR (CAR)
    FURTIVIDADE (DES)
    HISTÓRIA (INT)
    INTIMIDAÇÃO (CAR)
    INTUIÇÃO (SAB)
    INVESTIGAÇÃO (INT)
    LIDAR COM ANIMAIS (SAB)
    MEDICINA (SAB)
    NATUREZA (INT)
    PERCEPÇÃO (SAB)
    PERSUASÃO (CAR)
    PRESTIDIGITAÇÃO (DES)
    RELIGIÃO (INT)
    SOBREVIVÊNCIA (SAB)
}

Acrobacia (Des)
Arcanismo (Int)
Atletismo (For)
Atuação (Car)
Blefar (Car)
Furtividade (Des)
História (Int)
Intimidação (Car)
Intuição (Sab)
Investigação (Int)
Lidar com Animais (Sab)
Medicina (Sab)
Natureza (Int)
Percepção (Sab)
Persuasão (Car)
Prestidigitação (Des)
Religião (Int)
Sobrevivência (Sab)