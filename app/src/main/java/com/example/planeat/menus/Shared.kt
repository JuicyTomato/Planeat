package com.example.planeat.menus

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.planeat.MainActivity
import com.example.planeat.R
import com.example.planeat.StringListSharedDS
import com.example.planeat.menus.sharedMeals.SharedMeal
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Shared: AppCompatActivity() {
    private lateinit var alertDialog: AlertDialog
    private lateinit var sharedNameText: String

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared)


//--------------

        val scrollView = findViewById<ScrollView>(R.id.scrollView3)
        val gridLayout = scrollView.findViewById<GridLayout>(R.id.griddone)
        val addButton = findViewById<Button>(R.id.pulsantone)

        val context: Context = applicationContext
        val stringListSharedDS = StringListSharedDS(context)

        lifecycleScope.launch {

            //rimozione di una stringa dalla lista... Fai long press
            /*
            val stringToRemove = "Stringa da rimuovere"
            stringListSharedDS.removeStringFromList(stringToRemove)
             */

            // Recupero della lista di stringhe come Flow
            val stringListFlow: Flow<List<String>> = stringListSharedDS.getStringList()

            stringListFlow.collect { stringList ->
                //rimuovi vecchi bottoni e aggiungi i nuovi

                //rimuove i bottoni vecchi
                gridLayout.removeAllViews()

                //aggiungi bottone + testo per ogni elemento della lista
                stringList.forEach { string ->
                    if(string.isNotBlank())
                    addingShared(string, gridLayout, addButton, stringListSharedDS)
                }

            }
        }



        addButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(R.layout.alert_dialog_shared)
            alertDialogBuilder.setNegativeButton("CLOSE") {dialog, _ ->
                dialog.dismiss()
            }

            alertDialogBuilder.setPositiveButton("SAVE") { dialog, _ ->


                val sharedName = alertDialog.findViewById<EditText?>(R.id.sharedName)
                //nome piatto
                sharedNameText = sharedName?.text.toString()

                if (sharedNameText.isEmpty()) {
                    Toast.makeText(context, "At least one letter to name the group", Toast.LENGTH_SHORT).show()
                } else {

                    addingShared(sharedNameText, gridLayout, addButton, stringListSharedDS)

                    //aggiungi a dataStore per il retrieve dei dati quando rientri nell'app
                    lifecycleScope.launch {
                        stringListSharedDS.addStringToList(sharedNameText)
                    }

                    dialog.dismiss()
                }

            }

            //global variable
            alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }


//--------------

        //menu bar
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

                //shared
                R.id.personIcon -> return@setOnItemSelectedListener true
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
    }

    private fun addingShared(name: String, gridLayout: GridLayout, addButton: Button, stringListSharedDS: StringListSharedDS){
        val newButton = Button(this)
        //ID inutilizzato
        //newButton.id = View.generateViewId()
        newButton.layoutParams = GridLayout.LayoutParams().apply {
            width = GridLayout.LayoutParams.WRAP_CONTENT
            height = GridLayout.LayoutParams.WRAP_CONTENT
            setMargins(30, 10, 30, 30)
        }


        //aggiunge icona ed altre caratteristiche
        val icon = ContextCompat.getDrawable(this, R.drawable.default_square)
        //rimuove background
        newButton.background = icon
        newButton.text = name

        gridLayout.addView(newButton)
        newButton.setOnClickListener {
            //testo bottone per activity successiva
            val buttonText = newButton.text.toString()
            //moveBulbToView(bulbImageView)
            val intent = Intent(this, SharedMeal::class.java)
            startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }


        //RICORDA!!!!!!----------------------------------------------------------------------------------------------------------------------------------------------------
        //rimuovi anche tutto contenuto dello SHARED
        val buttonBin = findViewById<Button>(R.id.buttonBin)
        //rende invisibile addButton per visibile bin
        newButton.setOnLongClickListener {
            addButton.visibility = View.INVISIBLE
            buttonBin.visibility = View.VISIBLE

            //rimuovi quadrante
            buttonBin.setOnClickListener {
                lifecycleScope.launch {
                    stringListSharedDS.removeStringFromList(name)
                }
                addButton.visibility = View.VISIBLE
                buttonBin.visibility = View.INVISIBLE
            }

            true
        }

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}