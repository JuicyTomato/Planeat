package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Groups: AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)

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