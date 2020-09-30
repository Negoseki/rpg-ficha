package br.pucpr.appdev.duffeck.rpg_ficha.Model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


object APIConnection {

    private lateinit var database: DatabaseReference

    init {
        database = Firebase.database.reference.child("personagens")
    }

    fun addItem(item: CharacterSheet) {
        val chave = database.push().key
        chave?.let {
            database.child(it).setValue(item)

        }
    }

    fun getItem(idCharacter: String): CharacterSheet {
        var character = CharacterSheet()
        val db = database.child(idCharacter)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(CharacterSheet::class.java)
                character.key = idCharacter
                character = value!!
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ERROR FIREBASE", databaseError.getMessage()) //Don't ignore errors!
            }
        }
        database.addListenerForSingleValueEvent(valueEventListener)
        return character
    }

    fun editItem(character: CharacterSheet) {
        val db = database.child(character.key)
        database.setValue(character)
    }

    fun removeItem(idCharacter: String) {
        val db = database.child(idCharacter)
        database.removeValue()
    }

    fun getAllItems(): MutableList<CharacterSheet> {
        val db = database
        var characters: MutableList<CharacterSheet> = arrayListOf()
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value =
                    dataSnapshot.getValue(CharacterSheet::class.java) as MutableList<CharacterSheet>
                characters = value
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ERROR FIREBASE", databaseError.getMessage()) //Don't ignore errors!
            }
        }
        db.addListenerForSingleValueEvent(valueEventListener)
        return characters
    }
}