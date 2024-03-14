package com.example.planeat.menus.sharedMeals

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.planeat.R
import com.example.planeat.menus.Shared
import com.example.planeat.provaRoom.Ingredient
import com.example.planeat.provaRoom.Recipe
import com.example.planeat.provaRoom.RecipeDatabase
import com.example.planeat.provaRoom.RecipeWithIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedMeal : AppCompatActivity() {

    private suspend fun getRecipeNameFromDatabase(type: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            //ottieni il DAO
            val recipeDao = db.recipeDao()

            //accesso DB
            val recipes: List<Recipe> = recipeDao.getNameWhereGroup(type)

            //return contenuto
            recipes
        }
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java, "database-name"
        ).build()
    }


    private lateinit var nameMeal: String
    private var recipeName: String? = ""
    private lateinit var alertDialog: AlertDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared_meal)
        val context: Context = applicationContext

        val scrollView = findViewById<ScrollView>(R.id.scrollViewRecipe)
        val gridLayout = scrollView.findViewById<GridLayout>(R.id.gridRecipe)
        val addButton = findViewById<Button>(R.id.addRecipeShared)

        //nome del gruppo
        val nameShared = intent.getStringExtra("name_shared")
        //asssegno a recipeName che essendo globale posso usarla nella funzione setupButtonAction
        // senza passarla come argomento
        recipeName = nameShared


        addButton.setOnClickListener {


            //NO ALERT DIALOG IN CUI INSERISCI NOME. Clicchi +, poi ti porta direttamente a RecipeActivity
            // inserisci tutti i valori e BOOM, tornando indietro hai già nome nel coso
           /* val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(R.layout.alert_dialog_planning)
            alertDialogBuilder.setPositiveButton("SAVE") { dialog, _ ->

                //nome piatto
                val nameRecipe = findViewById<EditText>(R.id.sharedName)
                nameMeal = nameRecipe.text.toString()

                if (nameMeal.isEmpty()) {
                    Toast.makeText(context, "At least one letter to name the group", Toast.LENGTH_SHORT).show()
                } else {
                    val nM = nameRecipe?.text.toString()
                    addingRecipe(nM, gridLayout)
                    val intent = Intent(this, RecipeActivity::class.java)
                    intent.putExtra("meal_name", nM)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                }


                dialog.dismiss()
            }
            //global variable
            alertDialog = alertDialogBuilder.create()
            alertDialog.show()


            */
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
                val recipes: List<Recipe> = getRecipeNameFromDatabase(nameShared.toString())
                runOnUiThread {
                    showRecipesByGroup(recipes, gridLayout)
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


    //FINE



    //visualizza i nomi delle ricette già salvate
    private fun showRecipesByGroup(recipes: List<Recipe>, gridLayout: GridLayout){
        for (recipeName in recipes) {
            val recipe = recipeName.recipeName
            val id = recipeName.uid
            runOnUiThread {
                addingRecipe(recipe.toString(), gridLayout, id)
                Log.d("ID", id.toString())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}