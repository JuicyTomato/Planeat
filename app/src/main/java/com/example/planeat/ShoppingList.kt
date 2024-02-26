package com.example.planeat

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class ShoppingList : AppCompatActivity() {

    lateinit var bulbImageView: ImageView
    private lateinit var editTextIngredient: EditText
    private lateinit var textViewIngredientsList: TextView
    private val ingredientsList = mutableListOf<String>()
    private lateinit var linearList: LinearLayout


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_list)

        bulbImageView = findViewById(R.id.bulb)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenuBar)
        bottomNavigationView.selectedItemId = R.id.shoppingListIcon
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.homeIcon -> {
                    moveBulbToView(bulbImageView)
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
                R.id.groupPeople -> {
                    //moveBulbToView(bulbImageView)
                    val intent = Intent(this, Groups::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    //finish()  //già presente in onStop()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        editTextIngredient = findViewById(R.id.editTextIngredient)
        textViewIngredientsList = findViewById(R.id.editViewIngredientsList)
        linearList = findViewById(R.id.linearList)

        //Quando si preme sulla textView (sulla lista delle parole) esce la tastiera per aggiungere ingredienti
        textViewIngredientsList.setOnClickListener {
            // Imposta un OnClickListener per l'intero layout dell'Activity
            // Quando l'utente fa clic sullo schermo, seleziona la EditText per l'inserimento
            textViewIngredientsList.isFocusableInTouchMode = true

            editTextIngredient.requestFocus()
            showKeyboard() // Opzionale: mostra la tastiera virtuale
        }

        textViewIngredientsList.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Salva le modifiche quando si perde il focus dalla textViewIngredientsList
                saveChangesToIngredientsList()
            }
        }

        //serve per far in modo che il editText segua la lista
        editTextIngredient.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addIngredientToList()
                true
            } else {
                //monitora le modifiche con TextWatcher
                editTextIngredient.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        //aggiorna la editText con il testo dalla editText altra
                        textViewIngredientsList.text = s.toString()
                    }
                })
                false
            }
        }

        //serve a togliere hint ediText quando c'è qualcosa nell'elenco
        textViewIngredientsList.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Verifica se la TextView contiene elementi
                if (s.isNullOrEmpty()) {
                    // Se la TextView è vuota, reimposta il testo della hint nella EditText
                    editTextIngredient.hint = getString(R.string.hintEditTextShoppingList)
                } else {
                    // Se la TextView contiene elementi, rimuovi il testo della hint nella EditText
                    editTextIngredient.hint = "add here"
                }
            }
        })

    }


// cambiata in quella sotto...
    private fun addIngredientToList() {
        val ingredientText = editTextIngredient.text.toString().trim()
        if (ingredientText.isNotEmpty()) {
            ingredientsList.add("• $ingredientText")
            updateIngredientsTextView()
            editTextIngredient.text.clear()
        }
    }


/*  //Al posto che creare tante edit text per modificare ogni ingrediente (per questione room), dividi tutto il testo e ROOMmizza
    private fun addIngredientToList() {
        val ingredientText = editTextIngredient.text.toString().trim()
        if (ingredientText.isNotEmpty()) {
            // Creazione dinamica del nuovo EditText
            val newEditText = EditText(this)
            newEditText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            newEditText.setText(ingredientText)
            linearList.addView(newEditText)

            // Aggiungi l'ingrediente alla lista e aggiorna la TextView
            ingredientsList.add("• $ingredientText")
            updateIngredientsTextView()

            // Cancella il testo dalla EditText originale
            editTextIngredient.text.clear()
        }
    }

 */

    private fun updateIngredientsTextView() {
        val ingredientsText = ingredientsList.joinToString("\n")
        textViewIngredientsList.text = ingredientsText
    }

    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextIngredient, InputMethodManager.SHOW_IMPLICIT)
    }

    //Per movimento bulb... Essendo che si  ricarica anche il menu, non è molto incisivo
    private fun moveBulbToView(view: View) {
        // Calcola la destinazione X dell'immagine in base al centro della vista
        val destinationX = view.x + (view.width / 2) - (bulbImageView.width / 2)// - 512

        // Crea un animatore per spostare l'immagine alla destinazione X calcolata
        val animator = ObjectAnimator.ofFloat(bulbImageView, "translationX", destinationX)
        animator.duration = 800
        animator.start()
    }

    private fun saveChangesToIngredientsList() {
        // Aggiorna la lista degli ingredienti con il testo dalla textViewIngredientsList
        val text = textViewIngredientsList.text.toString()
        ingredientsList.clear()
        ingredientsList.addAll(text.split("\n"))
        // Aggiorna la textViewIngredientsList
        val ingredientsText = ingredientsList.joinToString("\n")
        textViewIngredientsList.text = ingredientsText
    }

    //Almeno non rimane aperta sotto consumando risorse... Non ho bisogno di tenerla aperta
    override fun onStop() {
        super.onStop()
        finish()
    }

}