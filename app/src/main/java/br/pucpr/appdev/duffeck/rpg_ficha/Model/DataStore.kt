package br.pucpr.appdev.duffeck.rpg_ficha.Model

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DataStore {

    /**
     * An array of sample (dummy) items.
     */
    var ITEMS: MutableList<CharacterSheet> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */

    init {
        val characterClasses = arrayListOf<CharacterClass>()
        characterClasses.add(CharacterClass("Barbeiro", 2))
        characterClasses.add(CharacterClass("Jardineiro", 5))
        val character = CharacterSheet(
            name = "Jão",
            characterClasses = characterClasses,
            experiencePoints = 1450,
            antecedent = "Advogado",
            playerName = "Juriscley"
        )
        ITEMS.add(character)
    }

    fun addItem(item: CharacterSheet) {
        ITEMS.add(item)
    }

    fun getItem(position: Int): CharacterSheet {
        return ITEMS.get(position)
    }

    fun getAllItems(): MutableList<CharacterSheet> {
        ITEMS = APIConnection.getAllItems()
        return ITEMS
    }

    fun editItem(character: CharacterSheet, position: Int) {

    }

    fun removeItem(position: Int) {

    }

    fun clearItems() {

    }

    fun characterCount(): Int {
        return ITEMS.size;
    }
}