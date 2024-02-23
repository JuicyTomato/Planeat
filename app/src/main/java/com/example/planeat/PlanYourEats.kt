package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PlanYourEats: AppCompatActivity() {
    private lateinit var buttonNext: Button
    private lateinit var buttonPreview: Button
    private var contDate: Int = 8


    @SuppressLint("SetTextI18n", "CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan_your_eats)


        buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonPreview = findViewById<Button>(R.id.buttonPreview)
        val backButton = findViewById<Button>(R.id.backButton)


        //FAI DIVENTARE DATE COME MENU... Naah, non farlo
        //formato data
        val oneCalendar = Calendar.getInstance()

        val oneTextView: TextView = findViewById(R.id.date1)
        val oneDate = oneCalendar.time

        //+1
        val twoCalendar = Calendar.getInstance()
        val twoTextView: TextView = findViewById(R.id.date2)
        val twoDate = twoCalendar.time

        //+2
        val threeCalendar = Calendar.getInstance()
        val threeTextView: TextView = findViewById(R.id.date3)
        val threeDate = threeCalendar.time

        //+3
        val fourCalendar = Calendar.getInstance()
        val fourTextView: TextView = findViewById(R.id.date4)
        val fourDate = fourCalendar.time

        //+4
        val fiveCalendar = Calendar.getInstance()
        val fiveTextView: TextView = findViewById(R.id.date5)
        val fiveDate = fiveCalendar.time

        //+5
        val sixCalendar = Calendar.getInstance()
        val sixTextView: TextView = findViewById(R.id.date6)
        val sixDate = sixCalendar.time

        //+6
        val sevenCalendar = Calendar.getInstance()
        val sevenTextView: TextView = findViewById(R.id.date7)
        val sevenDate = sevenCalendar.time

        //+7
        val eightCalendar = Calendar.getInstance()
        val eightTextView: TextView = findViewById(R.id.date8)
        val eightDate = eightCalendar.time

        //I
        updateTextView(oneTextView, oneCalendar, oneDate, 0)
        //II
        updateTextView(twoTextView, twoCalendar, twoDate, 1)
        //III
        updateTextView(threeTextView, threeCalendar, threeDate, 2)
        //IV
        updateTextView(fourTextView, fourCalendar, fourDate, 3)
        //V
        updateTextView(fiveTextView, fiveCalendar, fiveDate, 4)
        //VI
        updateTextView(sixTextView, sixCalendar, sixDate, 5)
        //VII
        updateTextView(sevenTextView, sevenCalendar, sevenDate, 6)
        //VIII
        updateTextView(eightTextView, eightCalendar, eightDate, 7)

        //barra sopra
        val dateViewText: TextView = findViewById(R.id.dateView)
        updateDateViewText(dateViewText, oneCalendar, eightCalendar)

        //Bottone per passare ai prossimi 8 giorni
        buttonNext.setOnClickListener {
            /*
            //Prima era così, se dovesse rompersi SIUM
            val twoTextView: TextView = findViewById(R.id.date2)
            twoCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val twoDate = twoCalendar.time
            twoTextView.text = dateFormat.format(twoDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(twoDate)}"
            */

            //I
            updateTextView(oneTextView, oneCalendar, oneDate, contDate)
            //II
            updateTextView(twoTextView, twoCalendar, twoDate, contDate)
            //III
            updateTextView(threeTextView, threeCalendar, threeDate, contDate)
            //IV
            updateTextView(fourTextView, fourCalendar, fourDate, contDate)
            //V
            updateTextView(fiveTextView, fiveCalendar, fiveDate, contDate)
            //VI
            updateTextView(sixTextView, sixCalendar, sixDate, contDate)
            //VII
            updateTextView(sevenTextView, sevenCalendar, sevenDate, contDate)
            //VIII
            updateTextView(eightTextView, eightCalendar, eightDate, contDate)

            //barra sopra
            updateDateViewText(dateViewText, oneCalendar, eightCalendar)
        }
        //Bottone per tornare indietro agli scorsi 8 giorni
        buttonPreview.setOnClickListener {
            //I
            updateTextView(oneTextView, oneCalendar, oneDate, -contDate)
            //II
            updateTextView(twoTextView, twoCalendar, twoDate, -contDate)
            //III
            updateTextView(threeTextView, threeCalendar, threeDate, -contDate)
            //IV
            updateTextView(fourTextView, fourCalendar, fourDate, -contDate)
            //V
            updateTextView(fiveTextView, fiveCalendar, fiveDate, -contDate)
            //VI
            updateTextView(sixTextView, sixCalendar, sixDate, -contDate)
            //VII
            updateTextView(sevenTextView, sevenCalendar, sevenDate, -contDate)
            //VIII
            updateTextView(eightTextView, eightCalendar, eightDate, -contDate)

            //barra sopra
            updateDateViewText(dateViewText, oneCalendar, eightCalendar)

        }

        //torna alla MainActivity
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateTextView(
        textView: TextView,
        calendar: Calendar,
        date: Date,
        contDate: Int
    ) {
        calendar.add(Calendar.DAY_OF_YEAR, contDate)
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time).uppercase(Locale.getDefault())
        val dayOfWeek = dayOfWeekFormat.format(calendar.time)
        textView.text = "$formattedDate\n$dayOfWeek"
    }

    fun updateDateViewText(
        textView: TextView,
        startDate: Calendar,
        lastDate: Calendar
    ) {
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val formattedFirstDate = dateFormat.format(startDate.time).uppercase(Locale.getDefault())
        val formattedLastDate = dateFormat.format(lastDate.time).uppercase(Locale.getDefault())
        textView.text = "$formattedFirstDate - $formattedLastDate"
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}