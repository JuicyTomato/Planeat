package com.example.planeat

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    //per la funzione MoveBulbToButton
    lateinit var bulbImageView: ImageView

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Metti comunque ID come valore univoco, poi estrai con QUERY: data *questa data*, buttami fuori tutto occorrente
        //OGGI TEXT VIEW
        val dateTextView: TextView = findViewById(R.id.dateTextView)
        //formato data
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val currentDate = Date()    //data di oggi

        //metti nella text view
        dateTextView.text = dateFormat.format(currentDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(currentDate)}"


        //DOMANI TEXTVIEW
        val tomorrowDateTextView: TextView = findViewById(R.id.tomorrowDateTextView)
        //ottieni giorno di domani
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1) // Aggiungi un giorno
        val tomorrowDate = calendar.time
        tomorrowDateTextView.text = dateFormat.format(tomorrowDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(tomorrowDate)}"


        //movimento bulb sui bottoni (dove necessario) + spostarsi tra activity
        bulbImageView = findViewById(R.id.bulb)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenuBar)
        bottomNavigationView.selectedItemId = R.id.homeIcon
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.homeIcon -> return@setOnItemSelectedListener true
                R.id.exploreIcon -> {}

                R.id.shoppingListIcon -> {
                    moveBulbToView(bulbImageView)
                    val intent = Intent(this, ShoppingList::class.java)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    //finish()  //già presente in onStop()
                    return@setOnItemSelectedListener true
                }

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


        //bottone frigo che sposta nell'activity frigo
        val buttonFridge = findViewById<Button>(R.id.buttonFridge)

        buttonFridge.setOnClickListener {
            val intent = Intent(this, FridgeList::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //bottone planYourEats che sposta nell'activity PlanYourEats
        val buttonPlanYourEats = findViewById<Button>(R.id.buttonPlanEats)

        buttonPlanYourEats.setOnClickListener {
            val intent = Intent(this, PlanYourEats::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    //Per movimento bulb... Essendo che si  ricarica anche il menu, non è molto incisivo
    private fun moveBulbToView(view: View) {
        //calcola la destinazione X dell'immagine in base al centro della vista
        val destinationX = view.x + (view.width / 2) - (bulbImageView.width / 2)// - 512

        //crea un animatore per spostare l'immagine alla destinazione X calcolata
        val animator = ObjectAnimator.ofFloat(bulbImageView, "translationX", destinationX)
        animator.duration = 800
        animator.start()
    }

    //Almeno non rimane aperta sotto consumando risorse... Non ho bisogno di tenerla aperta
    override fun onStop() {
        super.onStop()
        finish()
    }


}
