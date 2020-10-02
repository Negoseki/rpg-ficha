package br.pucpr.appdev.duffeck.rpg_ficha.Model

import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.SkillEnum
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class CharacterSheet(
    var name: String = "",
    var playerName: String = "",
    var race: String = "",
    var characterClasses: MutableList<CharacterClass> = arrayListOf(),
    var antecedent: String = "",
    var alignment: String = "",
    var experiencePoints: Int = 0,
    var strength: Int = 0,
    var dexterity: Int = 0,
    var constitution: Int = 0,
    var charisma: Int = 0,
    var wisdom: Int = 0,
    var intelligence: Int = 0,
    var inspiration: Boolean = false,
    var proficiencyBonus: Int = 0,
    var resistanceTests: MutableList<String> = arrayListOf(), // AbilityScoreEnum
    var skills: MutableList<Skill> = arrayListOf(),
    var bonusCA: Int = 0,
    var pvAtual: Int = 0,
    var pvTotal: Int = 0,
    var dadosVida: String = "",
    var deslocamento: Double = 0.0,
    var savingThrows: Throws = Throws(),
    var failThrows: Throws = Throws()
) {
    @Exclude
    var key: String = ""

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

    fun getIniciativa(): Int {
        return getAbiltyScoreModifier(AbilityScoreEnum.DEXTERITY)
    }

    fun getSkillModifier(skillValue: String): Int {
        val skillEnum = skills.find { it.key == skillValue }
        skillEnum?.abilityScore?.let {
            if (skillEnum.isProeficiency) {
                return getAbiltyScoreModifier(skillEnum.abilityScore) + proficiencyBonus + skillEnum.bonus
            } else {
                return getAbiltyScoreModifier(skillEnum.abilityScore) + skillEnum.bonus
            }
        }
        return 0
    }

    fun getCABase(): Int {
        return 10 + getAbiltyScoreModifier(AbilityScoreEnum.DEXTERITY)
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "playerName" to playerName,
            "race" to race,
            "characterClasses" to characterClasses,
            "antecedent" to antecedent,
            "alignment" to alignment,
            "experiencePoints" to experiencePoints,
            "strength" to strength,
            "dexterity" to dexterity,
            "constitution" to constitution,
            "charisma" to charisma,
            "wisdom" to wisdom,
            "intelligence" to intelligence,
            "inspiration" to inspiration,
            "proficiencyBonus" to proficiencyBonus,
            "resistanceTests" to resistanceTests,
            "skills" to skills
        )
    }

    fun getMod(ability: AbilityScoreEnum): Int {
        var valorBase = 0;
        when (ability) {
            AbilityScoreEnum.STRENGTH -> valorBase = this.strength
            AbilityScoreEnum.DEXTERITY -> valorBase = this.dexterity
            AbilityScoreEnum.CONSTITUTION -> valorBase = this.constitution
            AbilityScoreEnum.INTELLIGENCE -> valorBase = this.intelligence
            AbilityScoreEnum.WISDOM -> valorBase = this.wisdom
            AbilityScoreEnum.CHARISMA -> valorBase = this.charisma
        }

        if (valorBase > 0) {
            return (valorBase - 10) / 2
        }
        return 0;
    }


}