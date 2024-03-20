package com.example.planeat.menus.sharedMeals

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.planeat.R
import com.example.planeat.provaRoom.Ingredient
import com.example.planeat.provaRoom.Recipe
import com.example.planeat.provaRoom.RecipeDatabase
import com.example.planeat.provaRoom.RecipeGroup
import com.example.planeat.provaRoom.RecipeIngredient
import com.example.planeat.provaRoom.RecipeWithIngredientPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeActivity : AppCompatActivity() {

    private suspend fun getAllById(uid: Long): List<RecipeWithIngredientPair> {
        return withContext(Dispatchers.IO) {
            //ottieni il DAO
            val recipeDao = db.recipeDao()

            //accesso DB
            val recipes: List<RecipeWithIngredientPair> = recipeDao.getRecipeWithIngredientById(uid)

            //return contenuto
            recipes
        }
    }

    private suspend fun updateByRecipe(recipe: Recipe) {
        withContext(Dispatchers.IO) {
            //ottieni dao
            val recipeDao = db.recipeDao()

            //esegui l'operazione di aggiornamento
            recipeDao.updateRecipe(recipe)
        }
    }


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java, "database-name"
        ).build()
    }


    private val editTextPairsList = mutableListOf<Pair<EditText, EditText>>()
    private var once: Boolean = true        //serve che così anche se spammando editIcon, crei solamente una sola istanza

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_activity)

        //riceve id ricetta
        val buttonName = intent.getLongExtra("button_id", 0L)
        //penso nome del gruppo
        val nameShared = intent.getStringExtra("name_shared")
        //nome della ricetta
        val nM = intent.getStringExtra("meal_name")


        //se si arriva dal bottone +, allora si sta creando e quindi si sta editando.
        //Altrimenti si sta solo guardando
        var editState = intent.getBooleanExtra("edit_state", false)
        val editIcon = findViewById<ImageView>(R.id.editIcon)
        // Cambia colore editIcon
        setEditMode(editState, editIcon)


        //tutti editText + button
        val recipeNameEdit = findViewById<EditText>(R.id.recipeNameEdit)
        //val ingredientName = findViewById<EditText>(R.id.IngredientName)
        //val ingredientQuantity = findViewById<EditText>(R.id.IngredientQuantity)
        val preparationEdit = findViewById<EditText>(R.id.preparationEdit)
        val buttonAdder = findViewById<Button>(R.id.buttonAdder)


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val recipes: List<RecipeWithIngredientPair> = getAllById(buttonName)
                runOnUiThread {
                    showRecipes(recipes, recipeNameEdit, preparationEdit)
                }
            }
        }

        //appena entri rende invisibili o meno le views, se fossi o meno in modalità edit
        if(!editState) {
            setVisibleView(recipeNameEdit, preparationEdit, buttonAdder, true)
        }
        else {
            setVisibleView(recipeNameEdit, preparationEdit, buttonAdder, false)
        }


        //aggiunge gli editText per aggiungere ingredienti e quantità
        buttonAdder.setOnClickListener {
            //aggiunge nuove editText (sia nome ingrediente che quantità) per gli ingredienti
            addIngredientEditText()
        }



        //quando clicchi editIcon
        editIcon.setOnClickListener {
            editState = !editState
            setEditMode(editState, editIcon)

            //se editState fosse true vuol dire che è modalità editing, altrimenti solo visualizzazione
            //fatto al contrario: !editState, per chiarezza codice
            if (!editState) {
                //sia read che write
                //rende visibili le Views
                setVisibleView(recipeNameEdit, preparationEdit, buttonAdder, true)

                //mostra ricette
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val recipes: List<RecipeWithIngredientPair> = getAllById(buttonName)
                        runOnUiThread {
                            showRecipes(recipes, recipeNameEdit, preparationEdit)
                        }
                    }
                }

            } else {
                //solo read, non write
                //rende invisibili le Views
                setVisibleView(recipeNameEdit, preparationEdit, buttonAdder, false)

                //quando clicchi per disattivare allora salva, ma solo se è stato aggiunto almeno il nome
                if(recipeNameEdit.text.toString().isNotEmpty()) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val recipes: List<RecipeWithIngredientPair> = getAllById(buttonName)
                            Log.d("present", "NONO già ID")

                            if(recipes.isEmpty()) {
                                //salva la ricetta, nel caso...
                                if(once) {
                                    saveRecipe(recipeNameEdit, preparationEdit, nameShared.toString())
                                    //così crei una sola istanza di ricetta, e non tante quante volte spammi editIcon
                                    once = false
                                }
                                Log.d("present", "NO già ID")

                            }
                            else {
                                //...fa update allora del DB
                                updateByRecipe(Recipe(recipeNameEdit.text.toString(), preparationEdit.text.toString(), "", "Breakfast", buttonName))
                                Log.d("present", "presente già ID")
                            }
                        }
                    }
                }
            }
        }

        //SALVA CONTENUTO PRIMA DI USCIRE o SE RICLICCA SU EDIT
        val backButton = findViewById<Button>(R.id.backButton)

        //per tornare indietro (aggiungi con intent put extra, che se vieni dalla main view torni a quella, altrimenti al plan eats)
        backButton.setOnClickListener {
            val intent = Intent(this, SharedMeal::class.java)
            intent.putExtra("name_shared", nameShared)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    //cliccando sull'icona editIcon rende editable o non, le varie editText + bottone invisibile o non
    private fun setVisibleView(recipeNameEdit: EditText, preparationEdit: EditText, buttonAdder: Button, bool: Boolean){

        recipeNameEdit.isEnabled = bool
        //ingredientName.isEnabled = true
        //ingredientQuantity.isEnabled = true
        preparationEdit.isEnabled = bool
        buttonAdder.isVisible = bool
        buttonAdder.isEnabled = bool

        for (pair in editTextPairsList) {
            pair.first.isEnabled = bool
            pair.second.isEnabled = bool
        }

    }

    private fun setEditMode(editState: Boolean, editIcon: ImageView){
        if(editState){
            editIcon.setImageResource(R.drawable.edit)
        } else {
            editIcon.setImageResource(R.drawable.edit_gold)
        }
    }


    //roommizza e salva ricetta... Utilizzata sia in editIcon.setclick{} che in backButton.setClick{}
    private fun saveRecipe(mealNameText: EditText, mealPreparationText: EditText, nameShared: String){

        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                //for current id recipe
                val rec = Recipe(mealNameText.text.toString(), mealPreparationText.text.toString(), "", "Breakfast")
                // Aggiunge la ricetta
                //default not starred e shared groupz è null
                val insertedRecipeId = db.recipeDao().insertAll(rec)
                //potevo decidere di passare id, o rifare query al db per ottenere groupsh. Optato per seconda nonostante
                // la prima sia più easy (passare con putExtra), ma seconda era più comoda
                val idGroup = db.recipeDao().findByNameShared(nameShared)
                //salva anche nella tabella RecipeGroup
                db.recipeDao().insertRecipeGroup(RecipeGroup(insertedRecipeId, idGroup.idGroup))

                // Aggiunge gli ingredienti con l'id della ricetta appena inserita
                for (pair in editTextPairsList) {
                    val testoIngredient = pair.first.text.toString()
                    val testoQuantita = pair.second.text.toString()
                    val ing = Ingredient(testoIngredient, "gr")

                    //prende nome Ingrediente...
                    //...se id fosse già dentro nella tabella, allora aggiungi solo nella tabella RecipeIngredient (quella mezzana)
                    //altrimenti aggiungi ingrediente e metti in quella mezzana (tabella)
                    val existinIngredient = db.recipeDao().getAllIngredientsWhereName(testoIngredient)
                    Log.d("IDz", existinIngredient.toString())
                    if(existinIngredient.isNotEmpty()){
                        //inserisce nella tabella di mezzo e associa ingredienti con ricetta
                        //prendo della lista ultimo con idIngrediente... tanto dovrebbe essere sempre uno e al massimo poco importa perchè avrebbe
                        // lo stesso nome, quindi SBAM
                        db.recipeDao().insertRecipeIngredient(RecipeIngredient(insertedRecipeId, existinIngredient.last().idIngredient, testoQuantita.toInt()))
                    } else {
                        //aggiunge ingrediente + inserisce tabella di mezzo
                        val insertedIngredientId = db.recipeDao().insertIngredient(ing)
                        db.recipeDao().insertRecipeIngredient(RecipeIngredient(insertedRecipeId, insertedIngredientId, testoQuantita.toInt()))
                    }
                }


            }

            /*
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

             */

            // Pulisce la lista degli ingredienti
            editTextPairsList.clear()
        }

    }


    //Quella non modificata, originale
    /*
    private fun showRecipes(recipes: List<RecipeWithIngredient>, textView: TextView): String {
        val recipeText = StringBuilder()

        for (recipeWithIngredient in recipes) {
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

        val finalText = recipeText.toString()
        textView.text = finalText
        return finalText
    }
     */

    //Quella modificata
    //se ricetta già salvata, semplicemente la mostra... Da rifare
    @SuppressLint("SetTextI18n")
    private fun showRecipes(recipes: List<RecipeWithIngredientPair>, recipeNameEdit: EditText, preparationEdit: EditText){
        val recipeText = StringBuilder()

        for (recipeWithIngredient in recipes) {
            //Mostra nome nella giusta EditText
            val recipe = recipeWithIngredient.recipe.recipeName
            recipeNameEdit.setText(recipe)
            val ingredients = recipeWithIngredient.ingredients

            // lista ingredienti
            for (ingredient in ingredients) {
                val ingredientName = ingredient.nameIngredient
                //val quantity = ingredient.quantity
                val unityMes = ingredient.unityMes

                //recipeText.append("\t\t-$ingredientName\t$quantity $unityMes\n")
                recipeText.append("\t\t-$ingredientName\t $unityMes\n")

            }

            preparationEdit.setText(recipeWithIngredient.recipe.process)
        }

    }

    //TEXT WATCHER SU EDITTEXT1. Quando scrivi ingrediente ti seleziona quelli già salvati. Nel caso non lo avessi mai
    //salvato, allora lo salva
    //aggiunge le editText premendo il button +
    private fun addIngredientEditText(){
        val linearIngredients = findViewById<LinearLayout>(R.id.linearIngredients)
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.orientation = LinearLayout.HORIZONTAL

        //AutoCompleteTextView al posto che editText... Semplicemente cambiato
        //ingredienti
        val editText1 = AutoCompleteTextView(this)
        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        editText1.layoutParams = params1
        editText1.hint = "Insert first ingredient"

        //aggiunge textWatcher alla AutoCompleteTextView
        //così compaiono ingredienti mentre scrivi
        textWatcher(editText1)

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
        linearIngredients?.addView(linearLayout)
        linearLayout.addView(editText1)
        linearLayout.addView(editText2)
    }

    //così compaiono ingredienti mentre scrivi nella text degli ingredienti
    private fun textWatcher(autoCompleteTextView: AutoCompleteTextView){

        // Crea un TextWatcher per l'AutoCompleteTextView
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Non è necessario implementare questo metodo
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Non è necessario implementare questo metodo
            }

            override fun afterTextChanged(s: Editable?) {

                // recupera gli ingredienti dal database che corrispondono al testo inserito
                lifecycleScope.launch {
                    val matchingIngredients = withContext(Dispatchers.IO) {
                        db.recipeDao().getAllIngredientsBeginLike(s.toString())
                    }

                    val arrayIngredient = mutableListOf<String>()
                    for(ingredient in matchingIngredients){
                        arrayIngredient.add(ingredient.nameIngredient.toString())
                    }

                    //crea un adapter con gli ingredienti corrispondenti e impostalo sull'AutoCompleteTextView
                    val adapter = ArrayAdapter(this@RecipeActivity, android.R.layout.simple_dropdown_item_1line, arrayIngredient)
                    autoCompleteTextView.setAdapter(adapter)
                }
            }
        }

        //aggiungi il TextWatcher all'AutoCompleteTextView
        autoCompleteTextView.addTextChangedListener(textWatcher)

    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}