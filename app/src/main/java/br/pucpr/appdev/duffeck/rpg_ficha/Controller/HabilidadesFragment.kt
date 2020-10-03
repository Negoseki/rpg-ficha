package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.DataStore
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class HabilidadesFragment : Fragment() {
    var chrForMod: EditText? = null
    var chrDesMod: EditText? = null
    var chrConMod: EditText? = null
    var chrIntMod: EditText? = null
    var chrWisMod: EditText? = null
    var chrCarMod: EditText? = null
    var chrProficiencia: EditText? = null
    var chrName: TextView? = null
    var chrClassLvl: TextView? = null
    var chrExp: TextView? = null
    var chrAntecedente: TextView? = null
    var chrNomeJogador: TextView? = null
    var chrFor: TextView? = null
    var chrForRes: TextView? = null
    var chrDes: TextView? = null
    var chrDesRes: TextView? = null
    var chrCon: TextView? = null
    var chrConRes: TextView? = null
    var chrInt: TextView? = null
    var chrIntRes: TextView? = null
    var chrWis: TextView? = null
    var chrWisRes: TextView? = null
    var chrCar: TextView? = null
    var chrCarRes: TextView? = null
    var chrSabPassiva: TextView? = null
    var character: CharacterSheet? = null
    var chkForRes: MaterialCheckBox? = null
    var chkDesRes: MaterialCheckBox? = null
    var chkConRes: MaterialCheckBox? = null
    var chkIntRes: MaterialCheckBox? = null
    var chkWisRes: MaterialCheckBox? = null
    var chkCarRes: MaterialCheckBox? = null
    var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        (context as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_editar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.mnuEditar) {
            isEditMode = !isEditMode
            enableEditText(isEditMode)
            if (isEditMode) {
                item.setIcon(
                    ContextCompat.getDrawable(
                        (context as MainActivity),
                        R.drawable.ic_baseline_save_24
                    )
                )
            } else {
                DataStore.editItem(character!!)
                Toast.makeText(context, "Informações salvas com sucesso", Toast.LENGTH_SHORT).show()
                item.setIcon(
                    ContextCompat.getDrawable(
                        (context as MainActivity),
                        R.drawable.ic_baseline_edit_24
                    )
                )
            }
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    fun menuIconColor(menuItem: MenuItem, color: Int) {
        val drawable = menuItem.icon
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (context as MainActivity).toggleBottomNavigation(true)
        (context as MainActivity).toggleToolbar(true)
        (context as MainActivity).toolbar.title = "Habilidades"

        val preferences: SharedPreferences =
            (context as MainActivity).getSharedPreferences(
                "chaveSheet",
                Context.MODE_PRIVATE
            )
        val chave = preferences.getString("chaveSheet", "Teste")

        val viewOfLayout = inflater.inflate(R.layout.fragment_habilidades, container, false)

        character = DataStore.getItem(chave.toString())

        chrForMod = viewOfLayout.findViewById(R.id.chrForMod)
        chrDesMod = viewOfLayout.findViewById(R.id.chrDesMod)
        chrConMod = viewOfLayout.findViewById(R.id.chrConMod)
        chrIntMod = viewOfLayout.findViewById(R.id.chrIntMod)
        chrWisMod = viewOfLayout.findViewById(R.id.chrWisMod)
        chrProficiencia = viewOfLayout.findViewById(R.id.chrProficiencia)
        chrName = viewOfLayout.findViewById(R.id.chrName)
        chrClassLvl = viewOfLayout.findViewById(R.id.chrClassLvl)
        chrExp = viewOfLayout.findViewById(R.id.chrExp)
        chrAntecedente = viewOfLayout.findViewById(R.id.chrAntecedente)
        chrNomeJogador = viewOfLayout.findViewById(R.id.chrNomeJogador)
        chrFor = viewOfLayout.findViewById(R.id.chrFor)
        chrForRes = viewOfLayout.findViewById(R.id.chrForRes)
        chrDes = viewOfLayout.findViewById(R.id.chrDes)
        chrDesRes = viewOfLayout.findViewById(R.id.chrDesRes)
        chrCon = viewOfLayout.findViewById(R.id.chrCon)
        chrConRes = viewOfLayout.findViewById(R.id.chrConRes)
        chrInt = viewOfLayout.findViewById(R.id.chrInt)
        chrIntRes = viewOfLayout.findViewById(R.id.chrIntRes)
        chrWis = viewOfLayout.findViewById(R.id.chrWis)
        chrWisRes = viewOfLayout.findViewById(R.id.chrWisRes)
        chrCarMod = viewOfLayout.findViewById(R.id.chrCarMod)
        chrCar = viewOfLayout.findViewById(R.id.chrCar)
        chrCarRes = viewOfLayout.findViewById(R.id.chrCarRes)
        chrSabPassiva = viewOfLayout.findViewById(R.id.chrSabPassiva)

        chkForRes = viewOfLayout.findViewById(R.id.chkForRes)
        chkDesRes = viewOfLayout.findViewById(R.id.chkDesRes)
        chkConRes = viewOfLayout.findViewById(R.id.chkConRes)
        chkIntRes = viewOfLayout.findViewById(R.id.chkIntRes)
        chkWisRes = viewOfLayout.findViewById(R.id.chkWisRes)
        chkCarRes = viewOfLayout.findViewById(R.id.chkCarRes)

        chkForRes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!character!!.resistanceTests.contains(AbilityScoreEnum.STRENGTH.toString())) {
                    character!!.resistanceTests.add(AbilityScoreEnum.STRENGTH.toString())
                }
            } else {
                character!!.resistanceTests.remove(AbilityScoreEnum.STRENGTH.toString())
            }
            calcularValores(character!!)
        }

        chkDesRes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!character!!.resistanceTests.contains(AbilityScoreEnum.DEXTERITY.toString())) {
                    character!!.resistanceTests.add(AbilityScoreEnum.DEXTERITY.toString())
                }
            } else {
                character!!.resistanceTests.remove(AbilityScoreEnum.DEXTERITY.toString())
            }
            calcularValores(character!!)
        }

        chkConRes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!character!!.resistanceTests.contains(AbilityScoreEnum.CONSTITUTION.toString())) {
                    character!!.resistanceTests.add(AbilityScoreEnum.CONSTITUTION.toString())
                }
            } else {
                character!!.resistanceTests.remove(AbilityScoreEnum.CONSTITUTION.toString())
            }
            calcularValores(character!!)
        }

        chkIntRes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!character!!.resistanceTests.contains(AbilityScoreEnum.INTELLIGENCE.toString())) {
                    character!!.resistanceTests.add(AbilityScoreEnum.INTELLIGENCE.toString())
                }
            } else {
                character!!.resistanceTests.remove(AbilityScoreEnum.INTELLIGENCE.toString())
            }
            calcularValores(character!!)
        }

        chkWisRes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!character!!.resistanceTests.contains(AbilityScoreEnum.WISDOM.toString())) {
                    character!!.resistanceTests.add(AbilityScoreEnum.WISDOM.toString())
                }
            } else {
                character!!.resistanceTests.remove(AbilityScoreEnum.WISDOM.toString())
            }
            calcularValores(character!!)
        }

        chkCarRes?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!character!!.resistanceTests.contains(AbilityScoreEnum.CHARISMA.toString())) {
                    character!!.resistanceTests.add(AbilityScoreEnum.CHARISMA.toString())
                }
            } else {
                character!!.resistanceTests.remove(AbilityScoreEnum.CHARISMA.toString())
            }
            calcularValores(character!!)
        }

        chrForMod
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    s?.let {
                        if (!s.isEmpty()) {
                            character?.strength = Integer.parseInt(it.toString())
                        } else {
                            character?.strength = 0
                        }
                        calcularValores(character!!)
                    }
                }
            })
        chrDesMod
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    s?.let {
                        if (!s.isEmpty()) {
                            character?.dexterity = Integer.parseInt(it.toString())
                        } else {
                            character?.dexterity = 0
                        }
                        calcularValores(character!!)
                    }
                }
            })
        chrConMod
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    s?.let {
                        if (!s.isEmpty()) {
                            character?.constitution = Integer.parseInt(it.toString())
                        } else {
                            character?.constitution = 0
                        }
                        calcularValores(character!!)
                    }
                }
            })
        chrIntMod
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    s?.let {
                        if (!s.isEmpty()) {
                            character?.intelligence = Integer.parseInt(it.toString())
                        } else {
                            character?.intelligence = 0
                        }
                        calcularValores(character!!)
                    }
                }
            })
        chrWisMod
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    s?.let {
                        if (!s.isEmpty()) {
                            character?.wisdom = Integer.parseInt(it.toString())
                        } else {
                            character?.wisdom = 0
                        }
                        calcularValores(character!!)
                    }
                }
            })
        chrCarMod
            ?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    s?.let {
                        if (!s.isEmpty()) {
                            character?.charisma = Integer.parseInt(it.toString())
                        } else {
                            character?.charisma = 0
                        }
                        calcularValores(character!!)
                    }
                }
            })

        chrProficiencia!!.addTextChangedListener {
            if (chrProficiencia!!.text.isNotEmpty() && !chrProficiencia!!.text.toString()
                    .equals("+")
            ) {
                character?.proficiencyBonus = Integer.parseInt(it.toString())
            } else {
                character?.proficiencyBonus = 0
            }
            calcularValores(character!!)
        }

        enableEditText(false)
        loadCharacterInfo()
        calcularValores(character!!)

        return viewOfLayout
    }

    fun calcularValores(character: CharacterSheet) {
        var forRes = character.getAbiltyScoreModifier(AbilityScoreEnum.STRENGTH)
        chrFor?.text = (Utils.getValueStringWithSignal(forRes))
        if (character.resistanceTests.contains(AbilityScoreEnum.STRENGTH.toString())) {
            forRes += character.proficiencyBonus
        }
        chrForRes?.text = Utils.getValueStringWithSignal(forRes)

        var forDes = character.getAbiltyScoreModifier(AbilityScoreEnum.DEXTERITY)
        chrDes?.text = (Utils.getValueStringWithSignal(forDes))
        if (character.resistanceTests.contains(AbilityScoreEnum.DEXTERITY.toString())) {
            forDes += character.proficiencyBonus
        }
        chrDesRes?.text = Utils.getValueStringWithSignal(forDes)

        var forCon = character.getAbiltyScoreModifier(AbilityScoreEnum.CONSTITUTION)
        chrCon?.text = (Utils.getValueStringWithSignal(forCon))
        if (character.resistanceTests.contains(AbilityScoreEnum.CONSTITUTION.toString())) {
            forCon += character.proficiencyBonus
        }
        chrConRes?.text = Utils.getValueStringWithSignal(forCon)

        var forInt = character.getAbiltyScoreModifier(AbilityScoreEnum.INTELLIGENCE)
        chrInt?.text = (Utils.getValueStringWithSignal(forInt))
        if (character.resistanceTests.contains(AbilityScoreEnum.INTELLIGENCE.toString())) {
            forInt += character.proficiencyBonus
        }
        chrIntRes?.text = Utils.getValueStringWithSignal(forInt)

        var forWis = character.getAbiltyScoreModifier(AbilityScoreEnum.WISDOM)
        chrWis?.text = (Utils.getValueStringWithSignal(forWis))
        if (character.resistanceTests.contains(AbilityScoreEnum.WISDOM.toString())) {
            forWis += character.proficiencyBonus
        }
        chrWisRes?.text = Utils.getValueStringWithSignal(forWis)

        var forCar = character.getAbiltyScoreModifier(AbilityScoreEnum.CHARISMA)
        chrCar?.text = (Utils.getValueStringWithSignal(forCar))
        if (character.resistanceTests.contains(AbilityScoreEnum.CHARISMA.toString())) {
            forCar += character.proficiencyBonus
        }
        chrCarRes?.text = Utils.getValueStringWithSignal(forCar)

        chrSabPassiva?.text =
            (Utils.getValueStringWithSignal(character.getPassivePerception()))
    }

    fun loadCharacterInfo() {
        val df = DecimalFormat("###.##")
        character?.let {
            chrName?.text = (it.name)

            val textoClasses = it.characterClasses.map { it.toString() }
            chrClassLvl?.text = (textoClasses.joinToString())

            chrExp
                ?.text = it.experiencePoints.toString()

            chrAntecedente?.text = (it.antecedent)

            chrNomeJogador?.text = (it.playerName)

            chrForMod?.setText(it.strength.toString())

            chrDesMod?.setText(it.dexterity.toString())

            chrConMod?.setText(it.constitution.toString())

            chrIntMod?.setText(it.intelligence.toString())

            chrWisMod?.setText(it.wisdom.toString())

            chrCarMod?.setText(it.charisma.toString())

            chrProficiencia
                ?.setText(Utils.getValueStringWithSignal(it.proficiencyBonus))

            chkForRes?.isChecked = it.resistanceTests.contains(AbilityScoreEnum.STRENGTH.toString())
            chkDesRes?.isChecked =
                it.resistanceTests.contains(AbilityScoreEnum.DEXTERITY.toString())
            chkConRes?.isChecked =
                it.resistanceTests.contains(AbilityScoreEnum.CONSTITUTION.toString())
            chkIntRes?.isChecked =
                it.resistanceTests.contains(AbilityScoreEnum.INTELLIGENCE.toString())
            chkWisRes?.isChecked = it.resistanceTests.contains(AbilityScoreEnum.WISDOM.toString())
            chkCarRes?.isChecked = it.resistanceTests.contains(AbilityScoreEnum.CHARISMA.toString())
        }
    }

    fun enableEditText(isEnabled: Boolean) {
        chrForMod!!.isEnabled = isEnabled
        chrDesMod!!.isEnabled = isEnabled
        chrConMod!!.isEnabled = isEnabled
        chrIntMod!!.isEnabled = isEnabled
        chrWisMod!!.isEnabled = isEnabled
        chrCarMod!!.isEnabled = isEnabled
        chrProficiencia!!.isEnabled = isEnabled
        chkForRes!!.isEnabled = isEnabled
        chkDesRes!!.isEnabled = isEnabled
        chkConRes!!.isEnabled = isEnabled
        chkIntRes!!.isEnabled = isEnabled
        chkWisRes!!.isEnabled = isEnabled
        chkCarRes!!.isEnabled = isEnabled
    }
}