package com.example.planeat.menus.sharedMeals

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.planeat.R

class RecipeActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_activity)


        val backButton = findViewById<Button>(R.id.backButton)

        //per tornare indietro (aggiungi con intent put extra, che se vieni dalla main view torni a quella, altrimenti al plan eats)
        backButton.setOnClickListener {
            val intent = Intent(this, SharedMeal::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }




    override fun onStop() {
        super.onStop()
        finish()
    }

}