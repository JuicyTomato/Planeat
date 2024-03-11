package com.example.planeat.menus.sharedMeals

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.planeat.R
import com.example.planeat.menus.Shared

class SharedMeal : AppCompatActivity() {




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared_meal)



        val scrollView = findViewById<ScrollView>(R.id.scrollViewRecipe)
        val gridLayout = scrollView.findViewById<GridLayout>(R.id.gridRecipe)
        val addButton = findViewById<Button>(R.id.addRecipeShared)


        addButton.setOnClickListener {
            addingRecipe("OK", gridLayout)
        }


        val backButton = findViewById<Button>(R.id.backButton)

        //per tornare indietro (aggiungi con intent put extra, che se vieni dalla main view torni a quella, altrimenti al plan eats)
        backButton.setOnClickListener {
            val intent = Intent(this, Shared::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }


    private fun addingRecipe(name: String, gridLayout: GridLayout){
        val newButton = Button(this)
        //ID inutilizzato
        //newButton.id = View.generateViewId()
        newButton.layoutParams = GridLayout.LayoutParams().apply {
            width = GridLayout.LayoutParams.WRAP_CONTENT
            height = GridLayout.LayoutParams.WRAP_CONTENT
            setMargins(0, 10, 0, 0)
        }


        //aggiunge icona ed altre caratteristiche
        val icon = ContextCompat.getDrawable(this, R.drawable.add_recipe_shared)
        //rimuove background
        newButton.background = icon
        newButton.text = name

        gridLayout.addView(newButton)
        newButton.setOnClickListener {
            val intent = Intent(this, RecipeActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }


    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}