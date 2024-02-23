package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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



//PER ROOM AGGIUNGI ALLA DATA L'ANNO, FAI GETTEXT() + YEAR



        buttonNext = findViewById<Button>(R.id.buttonNext)
        buttonPreview = findViewById<Button>(R.id.buttonPreview)
        val backButton = findViewById<Button>(R.id.backButton)


        //FAI DIVENTARE DATE COME MENU... Naah, non farlo
        //formato data
        val oneCalendar = Calendar.getInstance()

        val oneDate = oneCalendar.time

        //+1
        val twoCalendar = Calendar.getInstance()
        val twoDate = twoCalendar.time

        //+2
        val threeCalendar = Calendar.getInstance()
        val threeDate = threeCalendar.time

        //+3
        val fourCalendar = Calendar.getInstance()
        val fourDate = fourCalendar.time

        //+4
        val fiveCalendar = Calendar.getInstance()
        val fiveDate = fiveCalendar.time

        //+5
        val sixCalendar = Calendar.getInstance()
        val sixDate = sixCalendar.time

        //+6
        val sevenCalendar = Calendar.getInstance()
        val sevenDate = sevenCalendar.time

        //+7
        val eightCalendar = Calendar.getInstance()
        val eightDate = eightCalendar.time

        //per barra sopra
        val lastCalendar = Calendar.getInstance()
        val lastDate = eightCalendar.time
        lastCalendar.add(Calendar.DAY_OF_YEAR, 7)
        val dateViewText: TextView = findViewById(R.id.dateView)
        updateDateViewText(dateViewText, oneCalendar, lastCalendar)



        val gridLayout = findViewById<GridLayout>(R.id.grillNotGrid)
        val button = Button(this)
        createButtons(button, updateTextView(oneCalendar, 0))
        gridLayout.addView(button)
        button.setOnClickListener{
            //robe
        }

        val button2 = Button(this)
        createButtons(button2, updateTextView(twoCalendar, 1))
        gridLayout.addView(button2)

        val button3 = Button(this)
        createButtons(button3, updateTextView(threeCalendar, 2))
        gridLayout.addView(button3)

        val button4 = Button(this)
        createButtons(button4, updateTextView(fourCalendar, 3))
        gridLayout.addView(button4)

        val button5 = Button(this)
        createButtons(button5, updateTextView(fiveCalendar, 4))
        gridLayout.addView(button5)

        val button6 = Button(this)
        createButtons(button6, updateTextView(sixCalendar, 5))
        gridLayout.addView(button6)

        val button7 = Button(this)
        createButtons(button7, updateTextView(sevenCalendar, 6))
        gridLayout.addView(button7)

        val button8 = Button(this)
        createButtons(button8, updateTextView(eightCalendar, 7))
        gridLayout.addView(button8)



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
            createButtons(button, updateTextView(oneCalendar, contDate))
            //II
            createButtons(button2, updateTextView(twoCalendar, contDate))
            //III
            createButtons(button3, updateTextView(threeCalendar, contDate))
            //IV
            createButtons(button4, updateTextView(fourCalendar, contDate))
            //V
            createButtons(button5, updateTextView(fiveCalendar, contDate))
            //VI
            createButtons(button6, updateTextView(sixCalendar, contDate))
            //VII
            createButtons(button7, updateTextView(sevenCalendar, contDate))
            //VIII
            createButtons(button8, updateTextView(eightCalendar, contDate))

            //barra sopra
            updateDateViewText(dateViewText, oneCalendar, eightCalendar)
        }
        //Bottone per tornare indietro agli scorsi 8 giorni
        buttonPreview.setOnClickListener {
            //I
            createButtons(button, updateTextView(oneCalendar, -contDate))
            //II
            createButtons(button2, updateTextView(twoCalendar, -contDate))
            //III
            createButtons(button3, updateTextView(threeCalendar, -contDate))
            //IV
            createButtons(button4, updateTextView(fourCalendar, -contDate))
            //V
            createButtons(button5, updateTextView(fiveCalendar, -contDate))
            //VI
            createButtons(button6, updateTextView(sixCalendar, -contDate))
            //VII
            createButtons(button7, updateTextView(sevenCalendar, -contDate))
            //VIII
            createButtons(button8, updateTextView(eightCalendar, -contDate))

            //barra sopra
            updateDateViewText(dateViewText, oneCalendar, eightCalendar)

        }




        //torna alla MainActivity
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    private fun createButtons(button: Button, text: String){
        button.id = View.generateViewId()
        button.layoutParams = GridLayout.LayoutParams().apply {
            width = GridLayout.LayoutParams.WRAP_CONTENT
            height = GridLayout.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Occupa una colonna
            rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            setMargins(25, 25, 25, 25) // Imposta i margini del pulsante
        }
        val icon = ContextCompat.getDrawable(this, R.drawable.default_square)
        button.background = icon
        button.text = text
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextView(
        calendar: Calendar,
        contDate: Int
    ): String {
        calendar.add(Calendar.DAY_OF_YEAR, contDate)
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time).uppercase(Locale.getDefault())
        val dayOfWeek = dayOfWeekFormat.format(calendar.time)
        return "$formattedDate\n$dayOfWeek"
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