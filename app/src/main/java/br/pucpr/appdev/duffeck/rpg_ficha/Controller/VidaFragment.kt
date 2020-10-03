package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import br.pucpr.appdev.duffeck.rpg_ficha.Helpers.Utils
import br.pucpr.appdev.duffeck.rpg_ficha.Model.CharacterSheet
import br.pucpr.appdev.duffeck.rpg_ficha.Model.DataStore
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class VidaFragment : Fragment() {

    var txtCABase: TextView? = null
    var txtCABaseBonus: EditText? = null
    var txtCATotal: TextView? = null
    var txtDadosVida: EditText? = null
    var txtDeslocamento: EditText? = null
    var txtIniciativa: TextView? = null
    var txtVidaAtual: EditText? = null
    var txtVidaTotal: EditText? = null
    var checkSavingT1: MaterialCheckBox? = null
    var checkSavingT2: MaterialCheckBox? = null
    var checkSavingT3: MaterialCheckBox? = null
    var checkFailT1: MaterialCheckBox? = null
    var checkFailT2: MaterialCheckBox? = null
    var checkFailT3: MaterialCheckBox? = null
    var character: CharacterSheet? = null
    var isEditMode: Boolean = false

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
        val view = inflater.inflate(R.layout.fragment_vida, container, false)
        (context as MainActivity).toolbar.title = "Vida e Armadura"

        val preferences: SharedPreferences =
            (context as MainActivity).getSharedPreferences(
                "chaveUser",
                Context.MODE_PRIVATE
            )
        val chave = preferences.getString("chaveUser", "Teste")
        character = DataStore.getItem(chave!!)
        character?.let {
            txtCABase = view.findViewById(R.id.txtCABase)
            txtCABaseBonus = view.findViewById(R.id.txtCABaseBonus)
            txtCATotal = view.findViewById(R.id.txtCATotal)
            txtDadosVida = view.findViewById(R.id.txtDadosVida)
            txtDeslocamento = view.findViewById(R.id.txtDeslocamento)
            txtIniciativa = view.findViewById(R.id.txtIniciativa)
            txtVidaAtual = view.findViewById(R.id.txtVidaAtual)
            txtVidaTotal = view.findViewById(R.id.txtVidaTotal)
            checkSavingT1 = view.findViewById(R.id.checkSavingT1)
            checkSavingT2 = view.findViewById(R.id.checkSavingT2)
            checkSavingT3 = view.findViewById(R.id.checkSavingT3)
            checkFailT1 = view.findViewById(R.id.checkFailT1)
            checkFailT3 = view.findViewById(R.id.checkFailT3)
            checkFailT2 = view.findViewById(R.id.checkFailT2)

            enableEditText(false)
            loadCharacterInfo()
        }

        return view
    }

    fun loadCharacterInfo() {
        val df = DecimalFormat("###.##")
        character?.let {
            txtCABase!!.text = (it.getCABase().toString())
            txtCABaseBonus!!.setText(it.bonusCA.toString())
            txtCATotal!!.text = ((it.getCABase() + it.bonusCA).toString())
            txtDadosVida!!.setText(it.dadosVida)
            txtDeslocamento!!.setText(df.format(it.deslocamento).toString())
            txtIniciativa!!.text = (Utils.getValueStringWithSignal(it.getIniciativa()))
            txtVidaAtual!!.setText(it.pvAtual.toString())
            txtVidaTotal!!.setText(it.pvTotal.toString())
            checkSavingT1!!.isChecked = it.savingThrows.throw1
            checkSavingT2!!.isChecked = it.savingThrows.throw2
            checkSavingT3!!.isChecked = it.savingThrows.throw3
            checkFailT1!!.isChecked = it.failThrows.throw1
            checkFailT3!!.isChecked = it.failThrows.throw3
            checkFailT2!!.isChecked = it.failThrows.throw2
            txtCABaseBonus!!.addTextChangedListener {
                val value = if (txtCABaseBonus!!.text.toString()
                        .isNotEmpty()
                ) txtCABaseBonus!!.text.toString().toInt() else 0
                txtCATotal!!.text =
                    ((character!!.getCABase() + value).toString())
            }
        }
    }

    fun enableEditText(isEnabled: Boolean) {
        txtCABaseBonus!!.isEnabled = isEnabled
        txtDadosVida!!.isEnabled = isEnabled
        txtDeslocamento!!.isEnabled = isEnabled
        txtVidaAtual!!.isEnabled = isEnabled
        txtVidaTotal!!.isEnabled = isEnabled
        checkSavingT1!!.isEnabled = isEnabled
        checkSavingT2!!.isEnabled = isEnabled
        checkSavingT3!!.isEnabled = isEnabled
        checkFailT1!!.isEnabled = isEnabled
        checkFailT3!!.isEnabled = isEnabled
        checkFailT2!!.isEnabled = isEnabled
    }

    fun toggleEdit() {
        isEditMode = !isEditMode
        enableEditText(isEditMode)

        if (!isEditMode) {
            character?.let {
                it.bonusCA = txtCABaseBonus!!.text.toString().toInt()
                it.dadosVida = txtDadosVida!!.text.toString()
                it.deslocamento = txtDeslocamento!!.text.toString().toDouble()
                it.pvAtual = txtVidaAtual!!.text.toString().toInt()
                it.pvTotal = txtVidaTotal!!.text.toString().toInt()
                it.savingThrows.throw1 = checkSavingT1!!.isChecked
                it.savingThrows.throw2 = checkSavingT2!!.isChecked
                it.savingThrows.throw3 = checkSavingT3!!.isChecked
                it.failThrows.throw1 = checkFailT1!!.isChecked
                it.failThrows.throw2 = checkFailT3!!.isChecked
                it.failThrows.throw3 = checkFailT2!!.isChecked
                DataStore.editItem(it)
                loadCharacterInfo()
            }
        }
    }
}