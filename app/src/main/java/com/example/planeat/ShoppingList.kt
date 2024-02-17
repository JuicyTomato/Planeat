package com.example.planeat

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingList : AppCompatActivity() {

    lateinit var bulbImageView: ImageView

    //prova

    private lateinit var editTextIngredient: EditText
    private lateinit var textViewIngredientsList: TextView
    private val ingredientsList = mutableListOf<String>()
    private lateinit var linearList: LinearLayout

    //fine prova





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_list)

        bulbImageView = findViewById(R.id.bulb)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenuBar)
        bottomNavigationView.selectedItemId = R.id.shoppingListIcon
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.homeIcon -> {
                    //moveBulbToView(findViewById(R.id.homeIcon))
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



        editTextIngredient = findViewById(R.id.editTextIngredient)
        textViewIngredientsList = findViewById(R.id.textViewIngredientsList)
        linearList = findViewById(R.id.linearList)



        textViewIngredientsList.setOnClickListener {
            // Imposta un OnClickListener per l'intero layout dell'Activity
                // Quando l'utente fa clic sullo schermo, seleziona la EditText per l'inserimento
            editTextIngredient.requestFocus()
            showKeyboard() // Opzionale: mostra la tastiera virtuale


            //inizio prova
            highlightWord(textViewIngredientsList, editTextIngredient.text.toString())
            //fine prova

        }


        //serve per far in modo che il editText segua la lista
        editTextIngredient.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addIngredientToList()
                true
            } else {
                // Aggiungi un TextWatcher alla EditText per monitorare le modifiche
                editTextIngredient.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        // Aggiorna la TextView con il testo dalla EditText
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
                    editTextIngredient.hint = null
                }
            }
        })





    }



    private fun addIngredientToList() {
        val ingredientText = editTextIngredient.text.toString().trim()
        if (ingredientText.isNotEmpty()) {
            ingredientsList.add("• $ingredientText")
            updateIngredientsTextView()
            editTextIngredient.text.clear()
        }
    }

    private fun updateIngredientsTextView() {
        val ingredientsText = ingredientsList.joinToString("\n")
        textViewIngredientsList.text = ingredientsText
    }

    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextIngredient, InputMethodManager.SHOW_IMPLICIT)
    }



    //inizio prova

    private fun highlightWord(textView: TextView, word: String) {
        val text = textView.text.toString()
        val spannable = SpannableStringBuilder(text)

        // Cerca la posizione della parola nell'intero testo
        var startIndex = text.indexOf(word)
        while (startIndex != -1) {
            val endIndex = startIndex + word.length
            // Aggiungi uno Span alla parola per evidenziarla con sfondo blu
            spannable.setSpan(
                BackgroundColorSpan(Color.BLUE),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            // Cerca la successiva occorrenza della parola
            startIndex = text.indexOf(word, endIndex)
        }

        // Imposta il testo modificato nella TextView
        textView.text = spannable
    }

//fine prova






    //Almeno non rimane aperta sotto consumando risorse... Non ho bisogno di tenerla aperta
    override fun onStop() {
        super.onStop()
        finish()
    }

    //Per movimento bulb... Penso che toglierò animazioni e terrò solamente bulb
    private fun moveBulbToView(view: View) {
        // Calcola la destinazione X dell'immagine in base al centro della vista
        val destinationX = view.x + (view.width / 2) - (bulbImageView.width / 2) - 512

        // Crea un animatore per spostare l'immagine alla destinazione X calcolata
        val animator = ObjectAnimator.ofFloat(bulbImageView, "translationX", destinationX)
        animator.duration = 500
        animator.start()
    }

}