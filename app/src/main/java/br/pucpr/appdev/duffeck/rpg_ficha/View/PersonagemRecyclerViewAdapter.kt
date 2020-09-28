package br.pucpr.appdev.duffeck.rpg_ficha.View

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.R


class PersonagemRecyclerViewAdapter(
    private val characterSheetList: List<CharacterSheet>
) : RecyclerView.Adapter<PersonagemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rcv_fragment_personagem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = characterSheetList[position]
        holder.characterName.text = item.name
        holder.characterRace.text = item.race
        holder.characterClassLevel.text = ""
        item.characterClasses.forEach {
            holder.characterClassLevel.text =
                holder.characterClassLevel.text.toString() + it.name + " / " + it.level + "\n"
        }
    }

    override fun getItemCount(): Int = characterSheetList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var characterName: TextView
        var characterRace: TextView
        var characterClassLevel: TextView

        init {
            characterName = view.findViewById(R.id.characterName)
            characterRace = view.findViewById(R.id.characterRace)
            characterClassLevel = view.findViewById(R.id.characterClassLevel)
        }
    }
}