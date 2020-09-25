package br.pucpr.appdev.duffeck.rpg_ficha.Controller

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.pucpr.appdev.duffeck.rpg_ficha.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navigationView = findViewById(R.id.navigationView);

        val navController = findNavController(R.id.fragNavHostFragment)
        val drawer: DrawerLayout = findViewById(R.id.layDrawer)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_habilidades, R.id.navigation_vida, R.id.navigation_ataque), drawer)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView?.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragNavHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun toggleBottomNavigation(enabled: Boolean) {
        val navView: BottomNavigationView = findViewById(R.id.navigationView)
        if (enabled) {
            navView.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
        } else {
            toolbar.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }
}