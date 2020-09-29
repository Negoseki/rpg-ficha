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

    fun getItem(idCharacter: String) {
        var character: CharacterSheet
        val database = Firebase.database.reference.child("personagens").child(idCharacter)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("RESULT", databaseError.getMessage()) //Don't ignore errors!
            }
        }
        database.addListenerForSingleValueEvent(valueEventListener)
    }

    fun editItem(character: CharacterSheet, position: Int) {

    }

    fun removeItem(position: Int) {

    }

    fun clearItems() {

    }

    fun getAllItems(): MutableList<CharacterSheet> {
        val database = Firebase.database.reference.child("personagens")
        var characters: MutableList<CharacterSheet> = arrayListOf()
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val characters = dataSnapshot.getValue(CharacterSheet::class.java) as MutableList<CharacterSheet>
                Log.d("aaaa", "aaaa")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("RESULT", databaseError.getMessage()) //Don't ignore errors!
            }
        }
        database.addListenerForSingleValueEvent(valueEventListener)
        return characters
    }
}