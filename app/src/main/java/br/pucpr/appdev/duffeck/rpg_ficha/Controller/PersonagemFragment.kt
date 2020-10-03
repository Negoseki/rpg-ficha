package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
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


class PersonagemFragment : Fragment() {

    private var columnCount = 1
    private var lista: RecyclerView? = null
    private var spinner: ProgressBar? = null ;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as MainActivity).toggleBottomNavigation(false)
        (context as MainActivity).toggleToolbar(false)
        val view = inflater.inflate(R.layout.fragment_personagem_list, container, false)
        lista = view.findViewById(R.id.list)
        spinner = view.findViewById(R.id.progressBar1);
        val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    e?.let {
                        val view = lista?.findChildViewUnder(e.x, e.y)
                        view?.let {
                            val position = lista?.getChildAdapterPosition(view)
                            val item = DataStore.ITEMS[position!!]
                            val bundle = bundleOf("position" to position)
                            val preferences: SharedPreferences =
                                (context as MainActivity).getSharedPreferences(
                                    "chaveSheet",
                                    Context.MODE_PRIVATE
                                )
                            with(preferences.edit()) {
                                putString("chaveSheet", item.key)
                                commit()
                            }

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

        spinner?.setVisibility(View.VISIBLE);
        DataStore.getAllItems().subscribe {
            with(lista!!) {
                spinner?.setVisibility(View.GONE);
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    PersonagemRecyclerViewAdapter(
                        it
                    )
            }
        }
        // Set the adapter


        val botao = view.findViewById<FloatingActionButton>(R.id.btnAdd)

        botao.setOnClickListener {
            findNavController().navigate(
                R.id.action_personagemFragment_to_cadastrarFicha
            )
        }

        return view
    }

}