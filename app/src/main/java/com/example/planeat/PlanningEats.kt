package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.planeat.provaRoom.Ingredient
import com.example.planeat.provaRoom.RecipeDatabase
import com.example.planeat.provaRoom.Recipe
import com.example.planeat.provaRoom.RecipeWithIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


class PlanningEats : AppCompatActivity() {


    // Funzione sospesa per eseguire operazioni di accesso al database
    suspend fun getRecipeFromDatabase(dateRoom: String): List<RecipeWithIngredient> {
        return withContext(Dispatchers.IO) {
            // Ottieni il DAO (Data Access Object)
            val recipeDao = db.recipeDao()

            // Esegui l'operazione di accesso al database
            val recipes: List<RecipeWithIngredient> = recipeDao.getAllWhereDate(dateRoom)


            // Restituisci i risultati ottenuti dall'operazione di accesso al database
            recipes
        }
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java, "database-name"
        ).build()
    }


    private lateinit var alertDialog: AlertDialog
    private lateinit var viewBLD: TextView
    private lateinit var breakfastID: TextView
    private lateinit var launchID: TextView
    private lateinit var dinnerID: TextView
    private val editTextPairsList = mutableListOf<Pair<EditText, EditText>>()
    private var mealOutput = StringBuilder()
    private var mealNameText: String = ""
    private var mealPreparationText: String = ""
    private var viewBLDRoom: String = "Breakfast"


    @SuppressLint("MissingInflatedId", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.planning_eats)

        val backButton = findViewById<Button>(R.id.backButton)

        val testoRicevuto = intent.getStringExtra("date")

        val textView = findViewById<TextView>(R.id.provolone)
        textView.text = testoRicevuto       //Data da conforntare senza giorno settimana ma con anno


        //ottiene date: String, per WHERE in query (così ottinei cibo per quel giorno)
        val dateRoom = intent.getStringExtra("roomDate")

        //per visualizzare ricette nella corretta editText
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val recipes: List<RecipeWithIngredient> = getRecipeFromDatabase(dateRoom.toString())
                runOnUiThread {
                    showRecipesByPosition(recipes, "Breakfast", findViewById(R.id.breakfastID))
                    showRecipesByPosition(recipes, "Launch", findViewById(R.id.launchID))
                    showRecipesByPosition(recipes, "Dinner", findViewById(R.id.dinnerID))
                }
            }
        }

        //per tornare indietro (aggiungi con intent put extra, che se vieni dalla main view torni a quella, altrimenti al plan eats)
        backButton.setOnClickListener {
            val intent = Intent(this, PlanYourEats::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    //funzione da activity, ho messo linearLayout come bottone

    fun onClickBreakfast(view: View) {
        viewBLD = findViewById<TextView>(R.id.breakfastID)
        viewBLDRoom = "Breakfast"
        showDialog()
    }

    fun onClickLaunch(view: View) {
        viewBLD = findViewById<TextView>(R.id.launchID)
        viewBLDRoom = "Launch"
        showDialog()
    }

    fun onClickDinner(view: View) {
        viewBLD = findViewById<TextView>(R.id.dinnerID)
        viewBLDRoom = "Dinner"
        showDialog()
    }

    //Ogni volta che si aggiunge un ingrediente, c'è il tasto EDIT.svg che permette di editare la editText
    @SuppressLint("InflateParams")
    fun showDialog() {

        //mostra Alertdialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(R.layout.alert_dialog_planning)
        alertDialogBuilder.setPositiveButton("SAVE") { dialog, _ ->

            //nome piatto
            val mealNameEditText = alertDialog.findViewById<EditText?>(R.id.mealName)
            mealNameText = mealNameEditText?.text.toString()

            val mealPreparationEditText = alertDialog.findViewById<EditText?>(R.id.mealPreparation)
            mealPreparationText = mealPreparationEditText?.text.toString()

            //chiamata della stampa su textView
            customOutputOnTextView(mealOutput, viewBLD, viewBLDRoom)

            dialog.dismiss()
        }

        //global variable
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        //bottone che aggiunge sia ingredienti che quantità
        alertDialog.findViewById<Button>(R.id.addIngredientAlert)?.setOnClickListener {
            val linearLayoutAdder = alertDialog.findViewById<LinearLayout>(R.id.linearLayoutAdder)
            val linearLayout = LinearLayout(this)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linearLayout.orientation = LinearLayout.HORIZONTAL

            //ingredienti
            val editText1 = EditText(this)
            val params1 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            editText1.layoutParams = params1
            editText1.hint = "Insert first ingredient"

            //quantità
            val editText2 = EditText(this)
            val params2 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            editText2.layoutParams = params2
            editText2.hint = "Insert quantity"
            editText2.gravity = Gravity.BOTTOM

            //aggiunti alla lista
            val editTextPair = Pair(editText1, editText2)
            editTextPairsList.add(editTextPair)


            //aggiunge alla view
            linearLayoutAdder?.addView(linearLayout)
            linearLayout.addView(editText1)
            linearLayout.addView(editText2)


        }
    }

    //PER AGGIUNGERE
    private fun customOutputOnTextView(
        meal: java.lang.StringBuilder,
        view: TextView,
        viewBLDRoom: String
    ) {
        lifecycleScope.launch {
            val dateRoom = intent.getStringExtra("roomDate")

            val recipeId = withContext(Dispatchers.IO) {
                // Aggiunge la ricetta
                val insertedRecipeId = db.recipeDao()
                    .insertAll(Recipe(mealNameText, mealPreparationText, dateRoom, viewBLDRoom))

                // Aggiunge gli ingredienti con l'id della ricetta appena inserita
                for (pair in editTextPairsList) {
                    val testoIngredient = pair.first.text.toString()
                    val testoQuantita = pair.second.text.toString()
                    db.recipeDao().insertIngredient(
                        Ingredient(
                            testoIngredient,
                            testoQuantita.toInt(),
                            "gr",
                            insertedRecipeId
                        )
                    )
                }

                insertedRecipeId
            }

            // Aggiorna la TextView con i dettagli della ricetta
            runOnUiThread {
                mealOutput.append(view.text.toString())
                mealOutput.append("\n• $mealNameText\n")

                for (pair in editTextPairsList) {
                    val testoIngredient = pair.first.text.toString()
                    val testoQuantita = pair.second.text.toString()
                    mealOutput.append("\t\t- $testoIngredient")
                    mealOutput.append(" ")
                    mealOutput.append(testoQuantita + "\n")
                }

                mealOutput.append("\nPreparation:\n$mealPreparationText\n")
                view.text = meal.toString()
            }

            // Pulisce la lista degli ingredienti
            editTextPairsList.clear()
        }
        mealOutput.clear()
    }


    //in base alla "posizione" delle ricette, vengono aggiunti nella editText corretta
    private fun showRecipesByPosition(
        recipes: List<RecipeWithIngredient>,
        position: String,
        textView: TextView
    ) {
        val filteredRecipes =
            recipes.filter { it.recipe.position.equals(position, ignoreCase = true) }
        val recipeText = StringBuilder()

        for (recipeWithIngredient in filteredRecipes) {
            val recipe = recipeWithIngredient.recipe.recipeName
            recipeText.append("\n• $recipe\n")
            val ingredients = recipeWithIngredient.ingredients

            // lista ingredienti
            for (ingredient in ingredients) {
                val ingredientName = ingredient.nameIngredient
                val quantity = ingredient.quantity
                val unityMes = ingredient.unityMes
                recipeText.append("\t\t-$ingredientName\t$quantity $unityMes\n")
            }

            recipeText.append("\nPreparation:\n")
            recipeText.append(recipeWithIngredient.recipe.process + "\n")
        }

        textView.text = recipeText.toString()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}