package br.pucpr.appdev.duffeck.rpg_ficha.Model

import android.content.Context
import android.util.Log
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
import io.reactivex.Observable

object DataStore {
    private lateinit var database: DatabaseReference

    private const val CHARACTER_BASE = "personagens"

    var ITEMS: MutableList<CharacterSheet> = ArrayList()
    var keys: MutableList<String> = arrayListOf()

    private var context: Context? = null
    fun setContext(value: Context) {
        context = value
        loadKeys()
    }

    init {
        database = Firebase.database.reference.child(CHARACTER_BASE)
        this.getAllItems()
    }

    fun addItem(item: CharacterSheet) {
        val chave = database.push().key
        chave?.let {
            database.child(it).setValue(item)
            ITEMS.add(item)
            keys.add(item.key)
            saveData()
        }
    }

    fun getItem(idCharacter: String): CharacterSheet {
        val character = ITEMS.find { it.key == idCharacter }
        return character!!

    }

    fun getAllItems(): Observable<MutableList<CharacterSheet>> {
        return Observable.create<MutableList<CharacterSheet>> { emitter ->
            var characters: MutableList<CharacterSheet> = arrayListOf()
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
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
            database.addListenerForSingleValueEvent(valueEventListener)
        }
    }

    fun editItem(character: CharacterSheet) {
        val c = ITEMS.find { it.key == character.key }
        c?.let {
            val db = database.child(it.key)
            db.setValue(it)
            ITEMS[ITEMS.indexOf(it)] = character
        }
    }

    fun removeItem(keyCharacter: String) {
        ITEMS.removeAt(ITEMS.indexOf(ITEMS.find { it.key == keyCharacter }))
        val db = database.child(keyCharacter)
        db.removeValue()
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