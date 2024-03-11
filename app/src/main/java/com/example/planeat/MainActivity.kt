package com.example.planeat

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.planeat.menus.Groups
import com.example.planeat.menus.ShoppingList
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class MainActivity : AppCompatActivity() {

    //per la funzione MoveBulbToButton
    lateinit var bulbImageView: ImageView



    @RequiresApi(Build.VERSION_CODES.O)
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

        //bottoni grossi per planEats
        val buttonMain1 = findViewById<Button>(R.id.mainSquare1)
        val buttonMain2 = findViewById<Button>(R.id.mainSquare2)

        buttonMain1.setOnClickListener{
            val intent = Intent(this, PlanningEats::class.java)
            intent.putExtra("date", exportDate(calendar, -1))       //-1 perchè prende calendar, che è data di domani
            intent.putExtra("roomDate", roomDate(calendar))
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        buttonMain2.setOnClickListener{
            val intent = Intent(this, PlanningEats::class.java)
            intent.putExtra("date", exportDate(calendar, 0))
            intent.putExtra("roomDate", roomDate(calendar))
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }


        val seDateTextView = findViewById<TextView>(R.id.seDateTextView)

        val context: Context = applicationContext

        val stringListPlanDS = StringListPlanDS(context)
        val stringListFlow: Flow<List<String>> = stringListPlanDS.getStringList()
        var nearestFutureDate: String? = null
        // Raccogli il flusso dei dati solo una volta
        lifecycleScope.launch {
            stringListFlow.collect { stringList ->
                val sortedStringList = stringList.sorted()
                for (date in sortedStringList) {
                    if (date >= LocalDate.now().toString()) {
                        nearestFutureDate = date
                        break
                    }

                }
                runOnUiThread {
                    seDateTextView.text = formatDateMain(nearestFutureDate) ?: "No event"
                }
            }
        }

        //bottone se_square
        val seSquare = findViewById<Button>(R.id.seSquare)
        seSquare.setOnClickListener {
            //se non ci fossero eventi speciali
            if(seDateTextView.text != "No event") {
                val intent = Intent(this, PlanningEats::class.java)
                intent.putExtra("date", formatDate(nearestFutureDate))   //exportDate(calendar, 0)
                intent.putExtra("roomDate", nearestFutureDate)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }


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

    //funzione che modifica il giorno yyyy-MM-dd in JUN 15 martedì
    private fun formatDate(dateString: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

        val date = dateString?.let { inputFormat.parse(it) } ?: return null

        val calendar = Calendar.getInstance().apply {
            time = date
        }
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        return "${outputFormat.format(date).uppercase()} $dayOfWeek"
    }

    //(cambia solo \n) cagata, lo so, ma almeno viene visualizzato correttamewnte nella mainActivity
    private fun formatDateMain(dateString: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

        val date = dateString?.let { inputFormat.parse(it) } ?: return null

        val calendar = Calendar.getInstance().apply {
            time = date
        }
        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        return "${outputFormat.format(date).uppercase()} \n$dayOfWeek"
    }

    @SuppressLint("SetTextI18n")
    private fun exportDate(
        calendar: Calendar,
        contDate: Int
    ): String {
        calendar.add(Calendar.DAY_OF_YEAR, contDate)
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time).uppercase(Locale.getDefault())
        val dayOfWeek = dayOfWeekFormat.format(calendar.time)
        return "$formattedDate $dayOfWeek"
    }

    private fun roomDate(
        calendar: Calendar
    ): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time).uppercase(Locale.getDefault())
    }

}
