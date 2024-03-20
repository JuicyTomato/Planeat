package com.example.planeat.menus.sharedMeals

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.planeat.R
import com.example.planeat.menus.Shared
import com.example.planeat.provaRoom.GroupSh
import com.example.planeat.provaRoom.Recipe
import com.example.planeat.provaRoom.RecipeDatabase
import com.example.planeat.provaRoom.RecipeWithGroupPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedMeal : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java, "database-name"
        ).build()
    }


    private lateinit var nameMeal: String
    private var recipeName: String? = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared_meal)

        val scrollView = findViewById<ScrollView>(R.id.scrollViewRecipe)
        val gridLayout = scrollView.findViewById<GridLayout>(R.id.gridRecipe)
        val addButton = findViewById<Button>(R.id.addRecipeNew)

        //nome del gruppo
        val nameShared = intent.getStringExtra("name_shared")
        //assegno a recipeName che essendo globale posso usarla nella funzione setupButtonAction
        // senza passarla come argomento
        recipeName = nameShared


        addButton.setOnClickListener {

            //clicchi +, poi ti porta direttamente a RecipeActivity
            // inserisci tutti i valori e BOOM, tornando indietro hai già nome nel coso

            val intent = Intent(this, RecipeActivity::class.java)
            //manda nome del gruppo, quindi quando si tornada RecipeActivity compaiono la lista di tutte le ricette
            //essendo che dovrebbe esserci nessuno gruppo con lo stesso nome
            intent.putExtra("name_shared", recipeName)
            //manda colore editIcon. Se true arriva da + button, altrimenti da recipe già presente
            intent.putExtra("edit_state", false)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        }


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val recipes: List<RecipeWithGroupPair> = db.recipeDao().getNameWhereGroup(nameShared.toString())
                lateinit var recipeList: List<Recipe>
                //aspetta che aggiungo nella tebella a metà
                for(re in recipes){
                    recipeList = re.recipes

                    Log.d("reci", re.toString())
                }

                runOnUiThread {
                    showRecipesByGroup(recipeList, gridLayout)
                }
            }
        }


        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, Shared::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    //la creazione pragmatica del bottone
    private fun createButton(context: Context, name: String): Button {
        val newButton = Button(context)
        newButton.layoutParams = GridLayout.LayoutParams().apply {
            width = GridLayout.LayoutParams.WRAP_CONTENT
            height = GridLayout.LayoutParams.WRAP_CONTENT
            setMargins(0, 10, 0, 0)
        }
        val icon = ContextCompat.getDrawable(context, R.drawable.add_recipe_shared)
        newButton.background = icon
        newButton.text = name
        return newButton
    }

    //assegno un listener ad ogni istanza del bottone, e invio ID e nome del gruppo (il nome perchè cliccando
    // su back da RecipeActivity, non visualizzavo più nulla essendo che ottenevo la lista delle ricette solamente
    // dal nome del gruppo, che dovrebbero essere tutti diversi i nomi)
    private fun setupButtonAction(button: Button, id: Long? = null) {
        button.setOnClickListener {
            val intent = Intent(button.context, RecipeActivity::class.java)
            intent.putExtra("button_id", id)
            //manda nome del gruppo, quindi quando si tornada RecipeActivity compaiono la lista di tutte le ricette
            //essendo che dovrebbe esserci nessuno gruppo con lo stesso nome
            intent.putExtra("name_shared", recipeName)
            //manda colore editIcon. Se true arriva da + button, altrimenti da recipe già presente
            intent.putExtra("edit_state", true)
            button.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(button.context as Activity).toBundle())
        }
    }


    //aggiunge il bottone al gridLayout
    private fun addingRecipe(name: String, gridLayout: GridLayout, id: Long? = null){
        val newButton = createButton(this, name)
        setupButtonAction(newButton, id)
        gridLayout.addView(newButton)
    }



    //visualizza i nomi delle ricette già salvate
    private fun showRecipesByGroup(recipes: List<Recipe>, gridLayout: GridLayout){
        for (recipeName in recipes) {
            val recipe = recipeName.recipeName
            val id = recipeName.idRecipe
            runOnUiThread {
                addingRecipe(recipe.toString(), gridLayout, id)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}