package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterClass
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import br.pucpr.appdev.duffeck.rpg_ficha.R

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


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (context as MainActivity).toggleBottomNavigation(true)
        val viewOfLayout = inflater.inflate(R.layout.fragment_habilidades, container, false)

        //TODO: Remover mock do personagem
        val characterClasses = arrayListOf<CharacterClass>()
        characterClasses.add(CharacterClass("Barbeiro", 2))
        characterClasses.add(CharacterClass("Jardineiro", 5))
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
            proficiencyBonus = 6
        )
        // fim do TODO

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
        chrForMod?.text = (character.strength.toString())
        chrFor?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.STRENGTH)))

        val chrDes = viewOfLayout?.findViewById<TextView>(R.id.chrDes)
        val chrDesMod = viewOfLayout?.findViewById<TextView>(R.id.chrDesMod)
        chrDesMod?.text = (character.dexterity.toString())
        chrDes?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.DEXTERITY)))

        val chrCon = viewOfLayout?.findViewById<TextView>(R.id.chrCon)
        val chrConMod = viewOfLayout?.findViewById<TextView>(R.id.chrConMod)
        chrConMod?.text = (character.constitution.toString())
        chrCon?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.CONSTITUTION)))

        val chrInt = viewOfLayout?.findViewById<TextView>(R.id.chrInt)
        val chrIntMod = viewOfLayout?.findViewById<TextView>(R.id.chrIntMod)
        chrIntMod?.text = (character.intelligence.toString())
        chrInt?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.INTELLIGENCE)))

        val chrWis = viewOfLayout?.findViewById<TextView>(R.id.chrWis)
        val chrWisMod = viewOfLayout?.findViewById<TextView>(R.id.chrWisMod)
        chrWisMod?.text = (character.wisdom.toString())
        chrWis?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.WISDOM)))

        val chrCar = viewOfLayout?.findViewById<TextView>(R.id.chrCar)
        val chrCarMod = viewOfLayout?.findViewById<TextView>(R.id.chrCarMod)
        chrCarMod?.text = (character.charisma.toString())
        chrCar?.text =
            (Utils.getValueStringWithSignal(character.getMod(AbilityScoreEnum.CHARISMA)))

        val chrProficiencia = viewOfLayout?.findViewById<TextView>(R.id.chrProficiencia)
        chrProficiencia?.text =
            (Utils.getValueStringWithSignal(character.proficiencyBonus))

        val chrSabPassiva = viewOfLayout?.findViewById<TextView>(R.id.chrSabPassiva)
        chrSabPassiva?.text =
            (Utils.getValueStringWithSignal(character.getPassivePerception()))
        chrSabPassiva
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