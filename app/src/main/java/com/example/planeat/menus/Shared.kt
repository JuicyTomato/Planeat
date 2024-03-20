package com.example.planeat.menus

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.room.Room
import com.example.planeat.MainActivity
import com.example.planeat.R
import com.example.planeat.menus.sharedMeals.SharedMeal
import com.example.planeat.provaRoom.GroupSh
import com.example.planeat.provaRoom.RecipeDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Shared: AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java, "database-name"
        ).build()
    }

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


        lifecycleScope.launch {

            //rimozione di una stringa dalla lista... Fai long press
            /*
            val stringToRemove = "Stringa da rimuovere"
            stringListSharedDS.removeStringFromList(stringToRemove)
             */

            withContext(Dispatchers.IO) {
                //recupero della lista di shared
                val stringListShared = db.recipeDao().getAllShared()

                //rimuove i bottoni vecchi
                gridLayout.removeAllViews()
                //aggiungi bottone + testo per ogni elemento della lista
                for (shared in stringListShared) {
                    addingShared(
                        shared.nameGroup.toString(),
                        gridLayout,
                        addButton)
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
                //nome gruppo
                sharedNameText = sharedName?.text.toString()

                if (sharedNameText.isEmpty()) {
                    Toast.makeText(applicationContext, "At least one letter to name the group", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val stringListShared = db.recipeDao().getAllShared()

                            if(stringListShared.any{it.nameGroup == sharedNameText}) {
                                withContext(Dispatchers.Main) {
                                Toast.makeText(applicationContext, "Already a Group with that name", Toast.LENGTH_SHORT).show()
                                 }
                            } else {
                               addingShared(sharedNameText, gridLayout, addButton)
                                //aggiungi al db per il retrieve dei dati quando rientri nell'app
                                db.recipeDao().insertShared(GroupSh(sharedNameText))
                            }
                        }
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

    private fun addingShared(name: String, gridLayout: GridLayout, addButton: Button){
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

        runOnUiThread {
            gridLayout.addView(newButton)
        }
        newButton.setOnClickListener {
            //testo bottone per activity successiva
            val buttonText = newButton.text.toString()
            //moveBulbToView(bulbImageView)
            val intent = Intent(this, SharedMeal::class.java)
            intent.putExtra("name_shared", newButton.text)
            startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }


        //RICORDA!!!!!!---------------------------------------------------------------------------------------------------------------------------------------------------
        //Quando elimini gruppo, rimuovi anche tutto contenuto dello SHARED, cioè ogni recipe dello shared eliminato
        //2 GRUPPI NON STESSO NOME

        val buttonBin = findViewById<Button>(R.id.buttonBin)
        //bottone per annullare "cancellazione"
        val buttonCloseX = findViewById<Button>(R.id.closeX)

        //rende invisibile addButton per visibile bin
        newButton.setOnLongClickListener {
            addButton.visibility = View.INVISIBLE
            buttonBin.visibility = View.VISIBLE
            buttonCloseX.visibility = View.VISIBLE

            //rimuovi quadrante
            buttonBin.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val groupshDelete = db.recipeDao().findByNameShared(name)
                        db.recipeDao().deleteShared(groupshDelete)

                        runOnUiThread {
                            addButton.visibility = View.VISIBLE
                            buttonBin.visibility = View.INVISIBLE
                            buttonCloseX.visibility = View.INVISIBLE
                        }
                    }
                }
                //per riaggiornare contenuto gruppi nella activity. Trova altra soluzione, ma altrimenti se elimino
                // non si aggiornavano
                finish()
                startActivity(intent)

            }
            buttonCloseX.setOnClickListener {

                addButton.visibility = View.VISIBLE
                buttonBin.visibility = View.INVISIBLE
                buttonCloseX.visibility = View.INVISIBLE

            }

            true
        }

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}