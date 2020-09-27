package br.pucpr.appdev.duffeck.rpg_ficha.Model

import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.RaceEnum
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.SkillEnum

class CharacterSheet(
    var name: String,
    var playerName: String,
    var race: String,
    var characterClasses: MutableList<CharacterClass>,
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
    var skills: MutableList<Skill>
) {

    init {
        this.skills = mutableListOf()
        // Perícias de Força
        this.skills.add(
            Skill(
                SkillEnum.ATHLETICS.value,
                "Atletismo",
                AbilityScoreEnum.STRENGTH
            )
        )
        // Perícias de Destreza
        this.skills.add(
            Skill(
                SkillEnum.ACROBATICS.value,
                "Acrobacia",
                AbilityScoreEnum.DEXTERITY
            )
        )

        this.skills.add(
            Skill(
                SkillEnum.SLEIGHT_OF_HAND.value,
                "Prestidigitação",
                AbilityScoreEnum.DEXTERITY
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.STEALTH.value,
                "Furtividade",
                AbilityScoreEnum.DEXTERITY
            )
        )

        // Perícias de Inteligencia
        this.skills.add(
            Skill(
                SkillEnum.ARCANA.value,
                "Arcanismo",
                AbilityScoreEnum.INTELLIGENCE
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.HISTORY.value,
                "História",
                AbilityScoreEnum.INTELLIGENCE
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.INVESTIGATION.value,
                "Investigação",
                AbilityScoreEnum.INTELLIGENCE
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.NATURE.value,
                "Natureza",
                AbilityScoreEnum.INTELLIGENCE
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.RELIGION.value,
                "Religião",
                AbilityScoreEnum.INTELLIGENCE
            )
        )
        // Perícias de Sabedoria
        this.skills.add(
            Skill(
                SkillEnum.ANIMAL_HANDLING.value,
                "Lidar com Animais",
                AbilityScoreEnum.WISDOM
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.INSIGHT.value,
                "Intuição",
                AbilityScoreEnum.WISDOM
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.MEDICINE.value,
                "Medicina",
                AbilityScoreEnum.WISDOM
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.PERCEPTION.value,
                "Percepção",
                AbilityScoreEnum.WISDOM
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.SURVIVAL.value,
                "Sobrevivência",
                AbilityScoreEnum.WISDOM
            )
        )
        // Perícias de Sabedoria
        this.skills.add(
            Skill(
                SkillEnum.DECEPTION.value,
                "Blefar",
                AbilityScoreEnum.CHARISMA
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.INTIMIDATION.value,
                "Intimidar",
                AbilityScoreEnum.CHARISMA
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.PERFORMANCE.value,
                "Atuação",
                AbilityScoreEnum.CHARISMA
            )
        )
        this.skills.add(
            Skill(
                SkillEnum.PERSUASION.value,
                "Persuasão",
                AbilityScoreEnum.CHARISMA
            )
        )
    }

    fun getPassivePerception(): Int {
        return 10 + wisdom
    }

    fun getAbiltyScoreModifier(abiltyScore: AbilityScoreEnum): Int {
        return Math.floor(
            (Utils.readInstanceProperty<Int>(
                this,
                abiltyScore.value
            ) / 2 - 5).toDouble()
        ).toInt()
    }

    fun getSkillModifier(skillValue: String): Int {
        val skillEnum = skills.find { it.key == skillValue }
        skillEnum?.let {
            if (it.isProeficiency) {
                return getAbiltyScoreModifier(it.abilityScore) + proficiencyBonus + it.bonus
            } else {
                return getAbiltyScoreModifier(it.abilityScore) + it.bonus
            }
        }
        return 0
    }
}