package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterClass
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.DataStore
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class HabilidadesFragment : Fragment() {
    private lateinit var viewOfLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_editar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.mnuEditar) {
            findNavController().navigate(
                R.id.action_navigation_habilidades_to_editarHabilidades
            )
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (context as MainActivity).toggleBottomNavigation(true)
        (context as MainActivity).toggleToolbar(true)
        (context as MainActivity).toolbar.title = "Habilidades"
        (context as MainActivity).toolbar.menu

        val preferences: SharedPreferences =
            (context as MainActivity).getSharedPreferences(
                "chaveSheet",
                Context.MODE_PRIVATE
            )
        val chave = preferences.getString("chaveSheet", "Teste")

        val viewOfLayout = inflater.inflate(R.layout.fragment_habilidades, container, false)

        val character = DataStore.getItem(chave.toString())
        Log.d("RESULT2", character.name) //Don't ignore errors!
        /*val key = database.push().key
        character.key = key.toString()
        key?.let{
            database.child(key).setValue(character)
        }*/

        val chrName = viewOfLayout?.findViewById<TextView>(R.id.chrName)
        chrName?.text = (character.name)

        val chrClassLvl = viewOfLayout?.findViewById<TextView>(R.id.chrClassLvl)
        val textoClasses = character.characterClasses.map { it.toString() }
        chrClassLvl?.text = (textoClasses.joinToString())

        val chrExp = viewOfLayout?.findViewById<TextView>(R.id.chrExp)
        chrExp?.text = (character.experiencePoints.toString())

        val chrAntecedente = viewOfLayout?.findViewById<TextView>(R.id.chrAntecedente)
        chrAntecedente?.text = (character.antecedent)

        val chrNomeJogador = viewOfLayout?.findViewById<TextView>(R.id.chrNomeJogador)
        chrNomeJogador?.text = (character.playerName)

        val chrFor = viewOfLayout?.findViewById<TextView>(R.id.chrFor)
        val chrForMod = viewOfLayout?.findViewById<TextView>(R.id.chrForMod)
        val chrForRes = viewOfLayout?.findViewById<TextView>(R.id.chrForRes)
        chrForMod?.text = (character.strength.toString())
        chrFor?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.STRENGTH)))
        var forRes = character.getMod(AbilityScoreEnum.STRENGTH)
        if (character.resistanceTests.contains(AbilityScoreEnum.STRENGTH.toString())) {
            forRes += character.proficiencyBonus
        }
        chrForRes?.text = Utils.getValueStringWithSignal(forRes)

        val chrDes = viewOfLayout?.findViewById<TextView>(R.id.chrDes)
        val chrDesMod = viewOfLayout?.findViewById<TextView>(R.id.chrDesMod)
        val chrDesRes = viewOfLayout?.findViewById<TextView>(R.id.chrDesRes)
        chrDesMod?.text = (character.dexterity.toString())
        chrDes?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.DEXTERITY)))
        var forDes = character.getMod(AbilityScoreEnum.DEXTERITY)
        if (character.resistanceTests.contains(AbilityScoreEnum.DEXTERITY.toString())) {
            forDes += character.proficiencyBonus
        }
        chrDesRes?.text = Utils.getValueStringWithSignal(forDes)

        val chrCon = viewOfLayout?.findViewById<TextView>(R.id.chrCon)
        val chrConMod = viewOfLayout?.findViewById<TextView>(R.id.chrConMod)
        val chrConRes = viewOfLayout?.findViewById<TextView>(R.id.chrConRes)
        chrConMod?.text = (character.constitution.toString())
        chrCon?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.CONSTITUTION)))
        var forCon = character.getMod(AbilityScoreEnum.CONSTITUTION)
        if (character.resistanceTests.contains(AbilityScoreEnum.CONSTITUTION.toString())) {
            forCon += character.proficiencyBonus
        }
        chrConRes?.text = Utils.getValueStringWithSignal(forCon)

        val chrInt = viewOfLayout?.findViewById<TextView>(R.id.chrInt)
        val chrIntMod = viewOfLayout?.findViewById<TextView>(R.id.chrIntMod)
        val chrIntRes = viewOfLayout?.findViewById<TextView>(R.id.chrIntRes)
        chrIntMod?.text = (character.intelligence.toString())
        chrInt?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.INTELLIGENCE)))
        var forInt = character.getMod(AbilityScoreEnum.INTELLIGENCE)
        if (character.resistanceTests.contains(AbilityScoreEnum.INTELLIGENCE.toString())) {
            forInt += character.proficiencyBonus
        }
        chrIntRes?.text = Utils.getValueStringWithSignal(forInt)

        val chrWis = viewOfLayout?.findViewById<TextView>(R.id.chrWis)
        val chrWisMod = viewOfLayout?.findViewById<TextView>(R.id.chrWisMod)
        val chrWisRes = viewOfLayout?.findViewById<TextView>(R.id.chrWisRes)
        chrWisMod?.text = (character.wisdom.toString())
        chrWis?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.WISDOM)))
        var forWis = character.getMod(AbilityScoreEnum.WISDOM)
        if (character.resistanceTests.contains(AbilityScoreEnum.WISDOM.toString())) {
            forWis += character.proficiencyBonus
        }
        chrWisRes?.text = Utils.getValueStringWithSignal(forWis)

        val chrCar = viewOfLayout?.findViewById<TextView>(R.id.chrCar)
        val chrCarMod = viewOfLayout?.findViewById<TextView>(R.id.chrCarMod)
        val chrCarRes = viewOfLayout?.findViewById<TextView>(R.id.chrCarRes)
        chrCarMod?.text = (character.charisma.toString())
        chrCar?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.CHARISMA)))
        var forCar = character.getMod(AbilityScoreEnum.CHARISMA)
        if (character.resistanceTests.contains(AbilityScoreEnum.CHARISMA.toString())) {
            forCar += character.proficiencyBonus
        }
        chrCarRes?.text = Utils.getValueStringWithSignal(forCar)

        val chrProficiencia = viewOfLayout?.findViewById<TextView>(R.id.chrProficiencia)
        chrProficiencia?.text =
            (Utils.getValueStringWithSignal(character.proficiencyBonus))

        val chrSabPassiva = viewOfLayout?.findViewById<TextView>(R.id.chrSabPassiva)
        chrSabPassiva?.text =
            (Utils.getValueStringWithSignal(character.getPassivePerception()))

        return viewOfLayout
    }

}