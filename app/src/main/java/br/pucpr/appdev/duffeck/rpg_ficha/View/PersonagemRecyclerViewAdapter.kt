package br.pucpr.appdev.duffeck.rpg_ficha.View

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.CharacterClassEnum
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.RaceEnum
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
        RaceEnum.values().find { e -> e.value == item.race }?.let {
            holder.characterRace.text = it.raceName
        }
        holder.characterClassLevel.text = ""
        item.characterClasses.forEach {
            CharacterClassEnum.values().find { e -> e.value == it.name }?.let {

                holder.characterClassLevel.text =
                    holder.characterClassLevel.text.toString() + it.className
            }
            holder.characterClassLevel.text =
                holder.characterClassLevel.text.toString() + " / " + it.level + "\n"
        }
        holder.imgRace.setImageResource(getImagePerRace(item.race))
    }

    fun getImagePerRace(race: String): Int {
        if (race == RaceEnum.DRAGONBORN.value) {
            return R.drawable.dragonborn
        }
        if (race == RaceEnum.DWARF.value) {
            return R.drawable.dwarf
        }
        if (race == RaceEnum.ELF.value) {
            return R.drawable.elf
        }
        if (race == RaceEnum.GNOME.value) {
            return R.drawable.gnome
        }
        if (race == RaceEnum.HALFLING.value) {
            return R.drawable.halfling
        }
        if (race == RaceEnum.HALF_ELF.value) {
            return R.drawable.half_elf
        }
        if (race == RaceEnum.HALF_ORC.value) {
            return R.drawable.half_orc
        }
        if (race == RaceEnum.HUMAN.value) {
            return R.drawable.human
        }
        if (race == RaceEnum.TIEFLING.value) {
            return R.drawable.tiefling
        }
        return R.drawable.unknown
    }

    override fun getItemCount(): Int = characterSheetList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var characterName: TextView
        var characterRace: TextView
        var characterClassLevel: TextView
        var imgRace: ImageView

        init {
            characterName = view.findViewById(R.id.characterName)
            characterRace = view.findViewById(R.id.characterRace)
            characterClassLevel = view.findViewById(R.id.characterClassLevel)
            imgRace = view.findViewById(R.id.imgRace)
        }
    }
}