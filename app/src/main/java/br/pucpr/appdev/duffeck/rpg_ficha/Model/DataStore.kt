package br.pucpr.appdev.duffeck.rpg_ficha.Model

import android.content.Context
import android.util.Log
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
import io.reactivex.Observable

object DataStore {
    var ITEMS: MutableList<CharacterSheet> = ArrayList()
    var keys: MutableList<String> = arrayListOf()

    private var context: Context? = null
    fun setContext(value: Context) {
        context = value
        loadKeys()
    }

    init {
        /*val characterClasses = arrayListOf<CharacterClass>()
        characterClasses.add(CharacterClass("Barbeiro", 2))
        characterClasses.add(CharacterClass("Jardineiro", 5))
        val character = CharacterSheet(
            name = "Jão",
            characterClasses = characterClasses,
            experiencePoints = 1450,
            antecedent = "Advogado",
            playerName = "Juriscley"
        )
        character.key = "essa é a chave";
        ITEMS.add(character)*/
        this.getAllItems()
    }

    fun addItem(item: CharacterSheet) {
        APIConnection.addItem(item)
        ITEMS.add(item)
        keys.add(item.key)
        saveData()
    }

    fun getItem(idCharacter: String): CharacterSheet {
        val itemsssss = ITEMS
        val character = ITEMS.find { it.key == idCharacter }
        return character!!

    }

    fun getAllItems(): Observable<MutableList<CharacterSheet>> {
        return Observable.create<MutableList<CharacterSheet>> { emitter ->
            val database = Firebase.database.reference.child("personagens")
            val db = database
            var characters: MutableList<CharacterSheet> = arrayListOf()
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //val values = dataSnapshot.getValue()

                    for (child in dataSnapshot.getChildren()) {
                        val personagem = child.getValue(CharacterSheet::class.java)!!
                        personagem.key = child.key!!
                        characters.add(personagem)
                    }
                    ITEMS = characters
                    emitter.onNext(ITEMS)
                    emitter.onComplete()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("ERROR FIREBASE", databaseError.getMessage()) //Don't ignore errors!
                }
            }
            db.addListenerForSingleValueEvent(valueEventListener)
        }
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