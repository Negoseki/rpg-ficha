package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.pucpr.appdev.duffeck.rpg_ficha.Model.DataStore
import br.pucpr.appdev.duffeck.rpg_ficha.R
import br.pucpr.appdev.duffeck.rpg_ficha.View.PersonagemRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PersonagemFragment : Fragment() {

    private var columnCount = 1
    private var lista: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).toggleBottomNavigation(false)
        val view = inflater.inflate(R.layout.fragment_personagem_list, container, false)
        lista = view.findViewById<RecyclerView>(R.id.list)
        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    e?.let {
                        val view = lista?.findChildViewUnder(e.x, e.y)
                        view?.let {
                            val position = lista?.getChildAdapterPosition(view)
                            val item = DataStore.ITEMS[position!!]
                            val bundle = bundleOf("position" to position)
                            findNavController().navigate(
                                R.id.action_personagemFragment_to_habilidadesFragment,
                                bundle
                            )
                        }
                    }

                    return super.onSingleTapConfirmed(e)
                }
            })

        lista?.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                var child = lista?.findChildViewUnder(e.x, e.y)
                return (child != null && gestureDetector.onTouchEvent(e))
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                // @TODO Change data store
                adapter =
                    PersonagemRecyclerViewAdapter(
                        arrayListOf()
                    )
            }
        }

        val botao = view.findViewById<FloatingActionButton>(R.id.btnAdd)

        botao.setOnClickListener {
            val database = Firebase.database.reference
            val chave = database.child("personagens").push().key
            database.child("personagens").child(chave!!).setValue("eiiiita")

        }

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PersonagemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }


}