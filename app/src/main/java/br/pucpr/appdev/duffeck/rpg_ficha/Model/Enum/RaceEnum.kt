package br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum

enum class RaceEnum(val value: String, val raceName: String) {
    DRAGONBORN("dragonborn", "Nascido de dragões"),
    DWARF("dwarf", "Anão"),
    ELF("elf", "Elfo"),
    GNOME("gnome", "Gnomo"),
    HALF_ELF("halfElf", "Meio Elfo"),
    HALF_ORC("halfOrc", "Meio Orc"),
    HALFLING("halfling", "Halfling"),
    HUMAN("human", "Humano"),
    TIEFLING("tiefling", "Tiefling")
}