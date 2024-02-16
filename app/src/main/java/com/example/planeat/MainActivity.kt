package com.example.planeat

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    //per la funzione MoveBulbToButton
    lateinit var bulbImageView: ImageView

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        //movimento bulb sui bottoni
        bulbImageView = findViewById(R.id.bulb)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)




        //per spostare sopra bulb ai bottoni
        button1.setOnClickListener {
            moveBulbToButton(button1)
        }
        button2.setOnClickListener {
            moveBulbToButton(button2)
        }
        button3.setOnClickListener {
            moveBulbToButton(button3)
            val intent = Intent(this, ShoppingList::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener {
            moveBulbToButton(button4)
        }



    }
    //Non serve a nulla, pensa altro modo
    //funzione per spostare bulb sopra ai bottoni
    private fun moveBulbToButton(button: Button) {
        //il 512, penso vada cambiato in base al dispositivo che si utilizza... (anche se aumenti lunghezza long_square)
        val destinationX = button.x + (button.width / 2) - (bulbImageView.width / 2) - 512
        val animator = ObjectAnimator.ofFloat(bulbImageView, "translationX", destinationX)
        animator.duration = 500
        animator.start()
    }
}
