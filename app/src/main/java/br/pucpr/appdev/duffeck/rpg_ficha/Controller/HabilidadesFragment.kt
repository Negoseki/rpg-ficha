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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CHARACTER_CODE = "codigoCharacter"

/**
 * A simple [Fragment] subclass.
 * Use the [HabilidadesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HabilidadesFragment : Fragment() {
    private lateinit var viewOfLayout: View

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(CHARACTER_CODE)
        }
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
        (context as MainActivity).toolbar.title = "Habilidades"
        (context as MainActivity).toolbar.menu

        val preferences: SharedPreferences =
            (context as MainActivity).getSharedPreferences(
                "chaveSheet",
                Context.MODE_PRIVATE
            )
        val chave = preferences.getString("chaveSheet", "Teste")

        val viewOfLayout = inflater.inflate(R.layout.fragment_habilidades, container, false)

        //TODO: Remover mock do personagem
        /*val characterClasses = arrayListOf<CharacterClass>()
        characterClasses.add(CharacterClass("Barbeiro", 2))
        characterClasses.add(CharacterClass("Jardineiro", 5))
        val resistanceTests = arrayListOf<String>();
        resistanceTests.add(AbilityScoreEnum.STRENGTH.toString())
        resistanceTests.add(AbilityScoreEnum.CONSTITUTION.toString())

        val character = CharacterSheet(
            name = "Jão",
            characterClasses = characterClasses,
            experiencePoints = 1450,
            antecedent = "Advogado",
            playerName = "Juriscley",
            strength = 19,
            dexterity = 16,
            constitution = 13,
            intelligence = 10,
            wisdom = 7,
            charisma = 4,
            proficiencyBonus = 6,
            resistanceTests = resistanceTests
        )*/
        // fim do TODO

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
        if(character.resistanceTests.contains(AbilityScoreEnum.STRENGTH.toString())){
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
        if(character.resistanceTests.contains(AbilityScoreEnum.DEXTERITY.toString())){
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
        if(character.resistanceTests.contains(AbilityScoreEnum.CONSTITUTION.toString())){
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
        if(character.resistanceTests.contains(AbilityScoreEnum.INTELLIGENCE.toString())){
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
        if(character.resistanceTests.contains(AbilityScoreEnum.WISDOM.toString())){
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
        if(character.resistanceTests.contains(AbilityScoreEnum.CHARISMA.toString())){
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HabilidadesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            HabilidadesFragment().apply {
                arguments = Bundle().apply {
                    putString(CHARACTER_CODE, param1)
                }
            }
    }
}