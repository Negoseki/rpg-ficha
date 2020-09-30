package br.pucpr.appdev.duffeck.rpg_ficha.Model

import android.content.Context
import br.pucpr.appdev.duffeck.rpg_ficha.R
import java.io.File
import java.util.*

object DataStore {
    var ITEMS: MutableList<CharacterSheet> = ArrayList()
    var keys: MutableList<String> = arrayListOf()

    private var context: Context? = null
    fun setContext(value: Context) {
        context = value
        loadKeys()
    }

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
        APIConnection.addItem(item)
        ITEMS.add(item)
        keys.add(item.key)
        saveData()
    }

    fun getItem(idCharacter: String): CharacterSheet {
        val character = ITEMS.find { it.key === idCharacter }
        return character!!

    }

    fun getAllItems(): MutableList<CharacterSheet> {
        ITEMS = APIConnection.getAllItems()
        return ITEMS
    }

    fun editItem(character: CharacterSheet) {
        val c = ITEMS.find { it.key == character.key }
        APIConnection.editItem(character!!)
        ITEMS[ITEMS.indexOf(c)] = character
    }

    fun removeItem(keyCharacter: String) {
        ITEMS.removeAt(ITEMS.indexOf(ITEMS.find { it.key == keyCharacter }))
        APIConnection.removeItem(keyCharacter)
        keys.removeAt(keys.indexOf(keys.find { it == keyCharacter }))
        saveData()
    }

    fun characterCount(): Int {
        return ITEMS.size;
    }

    fun loadKeys() {
        val context = context ?: return
        val file =
            File(context.filesDir.absolutePath + "/${context.getString(R.string.filename_keys)}")
        if (file.exists()) {
            file.bufferedReader().use {
                keys = arrayListOf()
                val iterator = it.lineSequence().iterator()
                while (iterator.hasNext()) {
                    keys.add(iterator.next())
                }
            }
        }
    }

    fun saveData() {
        val context = context ?: return
        val file =
            File(context.filesDir.absolutePath + "/${context.getString(R.string.filename_keys)}")
        if (!file.exists()) {
            file.createNewFile()
        } else {
            file.writeText("")
        }

        file.printWriter().use {
            for (key in keys) {
                it.println(key)
            }
        }
    }
}