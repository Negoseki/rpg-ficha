package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Attack
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterClass
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.DataStore
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.AbilityScoreEnum
import br.pucpr.appdev.duffeck.rpg_ficha.Model.Enum.CharacterClassEnum
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class AtaqueFragment : Fragment() {
    var btnAdd: FloatingActionButton? = null
    var layExtraAtaques: LinearLayout? = null
    var txtProeficiencia: TextView? = null
    var txtFor: TextView? = null
    var txtDex: TextView? = null
    var txtAtaqueNome: EditText? = null
    var txtAtaqueBonus: EditText? = null
    var txtAtaqueDano: EditText? = null
    var txtAtaqueDescricao: EditText? = null

    var listExtraAtaques: MutableList<View> = mutableListOf()
    var isEditMode: Boolean = false
    var character: CharacterSheet? = null


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
            this.toggleEdit()
            if (isEditMode) {
                item.setIcon(
                    ContextCompat.getDrawable(
                        (context as MainActivity),
                        R.drawable.ic_baseline_save_24
                    )
                )
            } else {
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (context as MainActivity).toggleBottomNavigation(true)
        (context as MainActivity).toggleToolbar(true)
        val view = inflater.inflate(R.layout.fragment_ataque, container, false)
        (context as MainActivity).toolbar.title = "Ataque"

        val preferences: SharedPreferences =
            (context as MainActivity).getSharedPreferences(
                "chaveUser",
                Context.MODE_PRIVATE
            )
        val chave = preferences.getString("chaveUser", "Teste")
        character = DataStore.getItem(chave!!)

        character?.let {
            btnAdd = view.findViewById(R.id.layExtraAtaques)
            layExtraAtaques = view.findViewById(R.id.layExtraAtaques)
            txtProeficiencia = view.findViewById(R.id.txtProeficiencia)
            txtFor = view.findViewById(R.id.layExtraAtaques)
            txtDex = view.findViewById(R.id.layExtraAtaques)
            txtAtaqueNome = view.findViewById(R.id.layExtraAtaques)
            txtAtaqueBonus = view.findViewById(R.id.layExtraAtaques)
            txtAtaqueDano = view.findViewById(R.id.layExtraAtaques)
            txtAtaqueDescricao = view.findViewById(R.id.layExtraAtaques)
            loadCharacterInfo()
            enableEditText(false)
        }

        return view

    }

    fun enableEditText(isEnabled: Boolean) {
        txtProeficiencia!!.isEnabled = isEnabled
        txtAtaqueNome!!.isEnabled = isEnabled
        txtAtaqueBonus!!.isEnabled = isEnabled
        txtAtaqueDano!!.isEnabled = isEnabled
        txtAtaqueDescricao!!.isEnabled = isEnabled
        btnAdd!!.visibility = if (isEnabled) View.VISIBLE else View.GONE
        listExtraAtaques.forEach {
            val extraAtaqueNome = it.findViewById<EditText>(R.id.txtAtaqueNome)
            val extraAtaqueBonus = it.findViewById<EditText>(R.id.txtAtaqueBonus)
            val extraAtaqueDano = it.findViewById<EditText>(R.id.txtAtaqueDano)
            val extraAtaqueDescricao = it.findViewById<EditText>(R.id.txtAtaqueDescricao)
            extraAtaqueNome.isEnabled = isEnabled
            extraAtaqueBonus.isEnabled = isEnabled
            extraAtaqueDano.isEnabled = isEnabled
            extraAtaqueDescricao.isEnabled = isEnabled
        }
    }

    fun toggleEdit() {
        isEditMode = !isEditMode
        enableEditText(isEditMode)

        if (!isEditMode) {
            character?.let {
                it.attacks = mutableListOf()
                var attack = Attack()
                attack.name = txtAtaqueNome!!.text.toString()
                attack.description = txtAtaqueDescricao!!.text.toString()
                attack.damage = txtAtaqueDano!!.text.toString()
                attack.bonus = txtAtaqueBonus!!.text.toString().toInt()
                it.attacks.add(attack)

                for (v in listExtraAtaques) {
                    var attack = Attack()
                    attack.name = v.findViewById<EditText>(R.id.txtAtaqueNome).text.toString()
                    attack.description =
                        v.findViewById<EditText>(R.id.txtAtaqueDescricao).text.toString()
                    attack.damage = v.findViewById<EditText>(R.id.txtAtaqueDano).text.toString()
                    attack.bonus =
                        v.findViewById<EditText>(R.id.txtAtaqueBonus).text.toString().toInt()
                    it.attacks.add(attack)
                }

                DataStore.editItem(it)
                Toast.makeText(context, "Informações salvas com sucesso", Toast.LENGTH_SHORT).show()
                loadCharacterInfo()
            }
        }
    }

    fun loadCharacterInfo() {
        val df = DecimalFormat("###.##")
        character?.let {

            txtProeficiencia!!.text = it.proficiencyBonus.toString()
            txtFor!!.text = it.getAbiltyScoreModifier(AbilityScoreEnum.STRENGTH).toString()
            txtDex!!.text = it.getAbiltyScoreModifier(AbilityScoreEnum.DEXTERITY).toString()
            txtAtaqueNome!!.setText(it.attacks.first().name)
            txtAtaqueBonus!!.setText(it.attacks.first().bonus.toString())
            txtAtaqueDano!!.setText(it.attacks.first().damage)
            txtAtaqueDescricao!!.setText(it.attacks.first().description)
            it.attacks.forEach { e ->
                if (e != it.attacks.first()) {
                    addExtraFieldOnClick(e)
                }
            }

        }
    }

    fun addExtraFieldOnClick(attack: Attack? = null) {
        val viewExtraFields = layoutInflater.inflate(R.layout.fragment_cadastrar_ficha_fields, null)
        layExtraAtaques!!.addView(viewExtraFields)
        listExtraAtaques.add(viewExtraFields)
        viewExtraFields.findViewById<ImageButton>(R.id.removeExtraClass)
            .setOnClickListener { removeExtraField(listExtraAtaques.indexOf(viewExtraFields)) }

        attack?.let {
            val extraAtaqueNome = viewExtraFields.findViewById<EditText>(R.id.txtAtaqueNome)
            val extraAtaqueBonus = viewExtraFields.findViewById<EditText>(R.id.txtAtaqueBonus)
            val extraAtaqueDano = viewExtraFields.findViewById<EditText>(R.id.txtAtaqueDano)
            val extraAtaqueDescricao =
                viewExtraFields.findViewById<EditText>(R.id.txtAtaqueDescricao)

            extraAtaqueNome.setText(it.name)
            extraAtaqueBonus.setText(it.bonus.toString())
            extraAtaqueDano.setText(it.damage)
            extraAtaqueDescricao.setText(it.description)
        }
    }

    fun removeExtraField(index: Int) {
        layExtraAtaques!!.removeView(listExtraAtaques[index])
        listExtraAtaques.removeAt(index)
    }


}