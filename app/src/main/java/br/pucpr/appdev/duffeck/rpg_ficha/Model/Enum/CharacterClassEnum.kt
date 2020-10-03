package br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum

enum class CharacterClassEnum(val value: String, val className: String) {
    BARBARIAN("barbarian", "Bárbaro"),
    BARD("bard", "Bardo"),
    CLERIC("cleric", "Clerigo"),
    DRUID("druid", "Druida"),
    FIGHTER("fighter", "Guerreiro"),
    MONK("monk", "Monge"),
    PALADIN("paladin", "Paladino"),
    RANGER("ranger", "Patrulheiro"),
    ROGUE("rogue", "Ladino"),
    SORCERER("sorcerer", "Feiticeiro"),
    WARLOCK("warlock", "Bruxo"),
    WIZARD("wizard", "Mago")
}