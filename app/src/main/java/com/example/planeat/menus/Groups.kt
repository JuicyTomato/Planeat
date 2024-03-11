package com.example.planeat.menus

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.planeat.MainActivity
import com.example.planeat.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Groups: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)


//--------------

        val scrollView = findViewById<ScrollView>(R.id.scrollView3)
        val gridLayout = scrollView.findViewById<GridLayout>(R.id.griddone)
        val addButton = findViewById<Button>(R.id.pulsantone)

        var buttonCounter = 0

        addButton.setOnClickListener {
            buttonCounter++
            val newButton = Button(this)
            newButton.id = View.generateViewId()
            newButton.layoutParams = GridLayout.LayoutParams().apply {
                width = GridLayout.LayoutParams.WRAP_CONTENT
                height = GridLayout.LayoutParams.WRAP_CONTENT
                setMargins(0, 0, 0, 0)
            }


            //rimuove background
            newButton.background = null

            //aggiunge icona ed altre caratteristiche
            val icon = ContextCompat.getDrawable(this, R.drawable.default_square)

            newButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
            gridLayout.addView(newButton)

            newButton.setOnClickListener {
                // Esegui azioni quando viene cliccato questo pulsante
                Toast.makeText(this, "ID del pulsante: ${newButton.id}", Toast.LENGTH_SHORT).show()
            }
        }





//--------------

        //menu bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenuBar)
        bottomNavigationView.selectedItemId = R.id.homeIcon
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.homeIcon -> {
                    //moveBulbToView(bulbImageView)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )
                    return@setOnItemSelectedListener true}
                R.id.exploreIcon -> {}

                R.id.shoppingListIcon -> {
                    //moveBulbToView(bulbImageView)
                    val intent = Intent(this, ShoppingList::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    //finish()  //già presente in onStop()
                    return@setOnItemSelectedListener true
                }

                R.id.personIcon -> {}
                R.id.groupPeople -> {}
            }
            false
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}