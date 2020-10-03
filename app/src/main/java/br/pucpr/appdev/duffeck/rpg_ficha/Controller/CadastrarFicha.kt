package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterClass
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.DataStore
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.CharacterClassEnum
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.RaceEnum
import br.pucpr.appdev.duffeck.rpg_ficha.R
import kotlinx.android.synthetic.main.activity_main.*

class CadastrarFicha : Fragment() {
    var txtNomeJogador: EditText? = null
    var txtNomePersonagem: EditText? = null
    var txtAntecedentes: EditText? = null
    var txtExperiencia: EditText? = null
    var txtClasseNivel: EditText? = null
    var spinRace: Spinner? = null
    var spinClasse: Spinner? = null
    var addExtraClass: ImageButton? = null
    var extraFieldsLayout: MutableList<View> = mutableListOf()
    var layExtraFields: LinearLayout? = null
    var character: CharacterSheet = CharacterSheet()

    val spinRaceMap = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_editar, menu)
        menu.getItem(0).setIcon(
            ContextCompat.getDrawable(
                (context as MainActivity),
                R.drawable.ic_baseline_save_24
            )
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()
        if (id == R.id.mnuEditar) {
            saveCharacterSheet()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastrar_ficha, container, false)
        (context as MainActivity).toggleToolbar(true)
        (context as MainActivity).toolbar.title = "Cadastrar uma Ficha"
        txtNomeJogador = view.findViewById(R.id.txtNomeJogador)
        txtNomePersonagem = view.findViewById(R.id.txtNomePersonagem)
        txtAntecedentes = view.findViewById(R.id.txtAntecedentes)
        txtExperiencia = view.findViewById(R.id.txtExperiencia)
        layExtraFields = view.findViewById(R.id.layExtraFields)
        spinRace = view.findViewById(R.id.spinRace)
        setSpinRaceAdapter()

        txtClasseNivel = view.findViewById(R.id.txtClasseNivel)
        spinClasse = view.findViewById(R.id.spinClasse)
        setSpinClassAdapter(spinClasse!!)

        addExtraClass = view.findViewById(R.id.addExtraClass)
        addExtraClass!!.setOnClickListener { addExtraFieldOnClick() }

        return view
    }

    fun setSpinRaceAdapter() {
        var spinValues = RaceEnum.values().map { v -> v.raceName }
        var adapter = ArrayAdapter<String>(
            context as MainActivity,
            android.R.layout.simple_spinner_item,
            spinValues
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinRace!!.adapter = adapter
    }

    fun setSpinClassAdapter(spinClass: Spinner) {
        var spinValues = CharacterClassEnum.values().map { v -> v.className }
        var adapter = ArrayAdapter<String>(
            context as MainActivity,
            android.R.layout.simple_spinner_item,
            spinValues
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinClass.adapter = adapter
    }

    fun addExtraFieldOnClick() {
        val viewExtraFields = layoutInflater.inflate(R.layout.fragment_cadastrar_ficha_fields, null)
        layExtraFields!!.addView(viewExtraFields)
        extraFieldsLayout.add(viewExtraFields)
        viewExtraFields.findViewById<ImageButton>(R.id.removeExtraClass)
            .setOnClickListener { removeExtraField(extraFieldsLayout.indexOf(viewExtraFields)) }
        setSpinClassAdapter(viewExtraFields.findViewById(R.id.spinClasse))
    }

    fun removeExtraField(index: Int) {
        layExtraFields!!.removeView(extraFieldsLayout[index])
        extraFieldsLayout.removeAt(index)
    }

    fun saveCharacterSheet() {
        character.playerName = txtNomeJogador!!.text.toString()
        character.name = txtNomePersonagem!!.text.toString()
        character.antecedent = txtAntecedentes!!.text.toString()
        character.experiencePoints =
            if (txtExperiencia!!.text.toString().isNotEmpty()) txtExperiencia!!.text.toString()
                .toInt() else 0
        character.race = RaceEnum.values()[spinRace!!.selectedItemPosition].value

        character.characterClasses = mutableListOf()
        var characterClass = CharacterClass()
        characterClass.name = CharacterClassEnum.values()[spinClasse!!.selectedItemPosition].value
        characterClass.level = txtClasseNivel!!.text.toString().toInt()

        character.characterClasses.add(characterClass)

        for (v in extraFieldsLayout) {
            val characterClassRow = CharacterClass()
            characterClassRow.name =
                CharacterClassEnum.values()[
                        v.findViewById<Spinner>(R.id.spinClasse)!!.selectedItemPosition].value
            characterClassRow.level =
                v.findViewById<EditText>(R.id.txtClasseNivel).text.toString().toInt()
            character.characterClasses.add(characterClassRow)
        }

        DataStore.addItem(character)
        findNavController().navigate(
            R.id.action_cadastrarFicha_to_personagemFragment
        )
    }
}