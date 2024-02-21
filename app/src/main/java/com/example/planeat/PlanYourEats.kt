package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PlanYourEats: AppCompatActivity() {
    private lateinit var buttonNext: Button
    private lateinit var buttonPreview: Button


    @SuppressLint("SetTextI18n", "CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan_your_eats)
        val contDate: Int = 8


        buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonPreview = findViewById<Button>(R.id.buttonPreview)
        val backButton = findViewById<Button>(R.id.backButton)



        //formato data
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val oneCalendar = Calendar.getInstance()

        val oneTextView: TextView = findViewById(R.id.date1)
        oneCalendar.add(Calendar.DAY_OF_YEAR, 0) // Aggiungi un giorno
        val oneDate = oneCalendar.time
        oneTextView.text = dateFormat.format(oneDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(oneDate)}"

        //+1
        val twoCalendar = Calendar.getInstance()
        val twoTextView: TextView = findViewById(R.id.date2)
        twoCalendar.add(Calendar.DAY_OF_YEAR, 1) // Aggiungi un giorno
        val twoDate = twoCalendar.time
        twoTextView.text = dateFormat.format(twoDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(twoDate)}"

        //+2
        val threeCalendar = Calendar.getInstance()
        val threeTextView: TextView = findViewById(R.id.date3)
        threeCalendar.add(Calendar.DAY_OF_YEAR, 2) // Aggiungi un giorno
        val threeDate = threeCalendar.time
        threeTextView.text = dateFormat.format(threeDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(threeDate)}"

        //+3
        val fourCalendar = Calendar.getInstance()
        val fourTextView: TextView = findViewById(R.id.date4)
        fourCalendar.add(Calendar.DAY_OF_YEAR, 3) // Aggiungi un giorno
        val fourDate = fourCalendar.time
        fourTextView.text = dateFormat.format(fourDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(threeDate)}"

        //+4
        val fiveCalendar = Calendar.getInstance()
        val fiveTextView: TextView = findViewById(R.id.date5)
        fiveCalendar.add(Calendar.DAY_OF_YEAR, 4) // Aggiungi un giorno
        val fiveDate = fiveCalendar.time
        fiveTextView.text = dateFormat.format(fiveDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(fiveDate)}"

        //+5
        val sixCalendar = Calendar.getInstance()
        val sixTextView: TextView = findViewById(R.id.date6)
        sixCalendar.add(Calendar.DAY_OF_YEAR, 5) // Aggiungi un giorno
        val sixDate = sixCalendar.time
        sixTextView.text = dateFormat.format(sixDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(sixDate)}"

        //+6
        val sevenCalendar = Calendar.getInstance()
        val sevenTextView: TextView = findViewById(R.id.date7)
        sevenCalendar.add(Calendar.DAY_OF_YEAR, 6) // Aggiungi un giorno
        val sevenDate = sevenCalendar.time
        sevenTextView.text = dateFormat.format(sevenDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(sevenDate)}"

        //+7
        val eightCalendar = Calendar.getInstance()
        val eightTextView: TextView = findViewById(R.id.date8)
        eightCalendar.add(Calendar.DAY_OF_YEAR, 7) // Aggiungi un giorno
        val eightDate = eightCalendar.time
        eightTextView.text = dateFormat.format(eightDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(eightDate)}"


        val dateViewText: TextView = findViewById(R.id.dateView)
        dateViewText.text = "${dateFormat.format(oneDate).uppercase(Locale.getDefault())} - ${dateFormat.format(eightDate).uppercase(Locale.getDefault())}"

        buttonNext.setOnClickListener {

            //I
            val oneTextView: TextView = findViewById(R.id.date1)
            oneCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val oneDate = oneCalendar.time
            oneTextView.text = dateFormat.format(oneDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(oneDate)}"

            //II
            val twoTextView: TextView = findViewById(R.id.date2)
            twoCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val twoDate = twoCalendar.time
            twoTextView.text = dateFormat.format(twoDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(twoDate)}"

            //III
            val threeTextView: TextView = findViewById(R.id.date3)
            threeCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val threeDate = threeCalendar.time
            threeTextView.text = dateFormat.format(threeDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(threeDate)}"

            //IV
            val fourTextView: TextView = findViewById(R.id.date4)
            fourCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val fourDate = fourCalendar.time
            fourTextView.text = dateFormat.format(fourDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(threeDate)}"

            //V
            val fiveTextView: TextView = findViewById(R.id.date5)
            fiveCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val fiveDate = fiveCalendar.time
            fiveTextView.text = dateFormat.format(fiveDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(fiveDate)}"

            //VI
            val sixTextView: TextView = findViewById(R.id.date6)
            sixCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val sixDate = sixCalendar.time
            sixTextView.text = dateFormat.format(sixDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(sixDate)}"

            //VII
            val sevenTextView: TextView = findViewById(R.id.date7)
            sevenCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val sevenDate = sevenCalendar.time
            sevenTextView.text = dateFormat.format(sevenDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(sevenDate)}"

            //VII
            val eightTextView: TextView = findViewById(R.id.date8)
            eightCalendar.add(Calendar.DAY_OF_YEAR, contDate) // Aggiungi un giorno
            val eightDate = eightCalendar.time
            eightTextView.text = dateFormat.format(eightDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(eightDate)}"

            val dateViewText: TextView = findViewById(R.id.dateView)
            dateViewText.text = "${dateFormat.format(oneDate).uppercase(Locale.getDefault())} - ${dateFormat.format(eightDate).uppercase(Locale.getDefault())}"

        }

        buttonPreview.setOnClickListener {
            //I
            val oneTextView: TextView = findViewById(R.id.date1)
            oneCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val oneDate = oneCalendar.time
            oneTextView.text = dateFormat.format(oneDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(oneDate)}"

            //II
            val twoTextView: TextView = findViewById(R.id.date2)
            twoCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val twoDate = twoCalendar.time
            twoTextView.text = dateFormat.format(twoDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(twoDate)}"

            //III
            val threeTextView: TextView = findViewById(R.id.date3)
            threeCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val threeDate = threeCalendar.time
            threeTextView.text = dateFormat.format(threeDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(threeDate)}"

            //IV
            val fourTextView: TextView = findViewById(R.id.date4)
            fourCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val fourDate = fourCalendar.time
            fourTextView.text = dateFormat.format(fourDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(threeDate)}"

            //V
            val fiveTextView: TextView = findViewById(R.id.date5)
            fiveCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val fiveDate = fiveCalendar.time
            fiveTextView.text = dateFormat.format(fiveDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(fiveDate)}"

            //VI
            val sixTextView: TextView = findViewById(R.id.date6)
            sixCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val sixDate = sixCalendar.time
            sixTextView.text = dateFormat.format(sixDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(sixDate)}"

            //VII
            val sevenTextView: TextView = findViewById(R.id.date7)
            sevenCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val sevenDate = sevenCalendar.time
            sevenTextView.text = dateFormat.format(sevenDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(sevenDate)}"

            //VII
            val eightTextView: TextView = findViewById(R.id.date8)
            eightCalendar.add(Calendar.DAY_OF_YEAR, -contDate) // Aggiungi un giorno
            val eightDate = eightCalendar.time
            eightTextView.text = dateFormat.format(eightDate).uppercase(Locale.getDefault()) + "\n${dayOfWeekFormat.format(eightDate)}"

            val dateViewText: TextView = findViewById(R.id.dateView)
            dateViewText.text = "${dateFormat.format(oneDate).uppercase(Locale.getDefault())} - ${dateFormat.format(eightDate).uppercase(Locale.getDefault())}"

        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}