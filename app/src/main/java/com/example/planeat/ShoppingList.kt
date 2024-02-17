package com.example.planeat

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingList : AppCompatActivity() {

    lateinit var bulbImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_list)

        bulbImageView = findViewById(R.id.bulb)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenuBar)
        bottomNavigationView.selectedItemId = R.id.shoppingListIcon
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.homeIcon -> {
                    moveBulbToView(findViewById(R.id.homeIcon))
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )
                    //finish()//già presente in onStop()
                    return@setOnItemSelectedListener true
                }

                R.id.exploreIcon -> {}

                R.id.shoppingListIcon -> return@setOnItemSelectedListener true

                R.id.personIcon -> {}
            }
            false
        }
    }


    //Almeno non rimane aperta sotto consumando risorse... Non ho bisogno di tenerla aperta
    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun moveBulbToView(view: View) {
        // Calcola la destinazione X dell'immagine in base al centro della vista
        val destinationX = view.x + (view.width / 2) - (bulbImageView.width / 2) - 512

        // Crea un animatore per spostare l'immagine alla destinazione X calcolata
        val animator = ObjectAnimator.ofFloat(bulbImageView, "translationX", destinationX)
        animator.duration = 500
        animator.start()
    }

}