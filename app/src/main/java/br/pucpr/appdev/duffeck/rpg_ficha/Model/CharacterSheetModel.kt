package br.pucpr.appdev.duffeck.rpg_ficha.Model

import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils

class CharacterSheetModel(
    var name: String,
    var playerName: String,
    var race: String,
    var antecedent: String,
    var alignment: String,
    var experiencePoints: Int,
    var strength: Int,
    var dexterity: Int,
    var constitution: Int,
    var charisma: Int,
    var wisdom: Int,
    var intelligence: Int,
    var inspiration: Boolean,
    var proficiencyBonus: Int,
    var resistanceTests: MutableList<AbilityScoreEnum>,
    var expertise: MutableList<ExpertiseEnum>
) {

    fun getPassiveWisdom(): Int {
        return 1
    }

    fun getAbiltyScoreModifier(abiltyScore: AbilityScoreEnum): Int {
        return Utils.readInstanceProperty<Int>(this, abiltyScore.value) / 2 - 5
    }
}