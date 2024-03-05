package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.planeat.provaRoom.AppDatabase
import com.example.planeat.provaRoom.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlanningEats : AppCompatActivity() {





//PROVA ROOM        1/2

    // Funzione sospesa per eseguire operazioni di accesso al database
    suspend fun getUsersFromDatabase(): List<User> {
        return withContext(Dispatchers.IO) {
            // Ottieni il DAO (Data Access Object)
            val userDao = db.userDao()

            // Esegui l'operazione di accesso al database
            val users: List<User> = userDao.getAll()

            // Restituisci i risultati ottenuti dall'operazione di accesso al database
            users
        }
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    //FINE PROVA ROOM




    private lateinit var alertDialog: AlertDialog
    private lateinit var viewBLD: TextView
    private lateinit var breakfastID: TextView
    private val editTextPairsList = mutableListOf<Pair<EditText, EditText>>()
    private var mealOutput = StringBuilder()
    private var mealNameText: String = "CIAONE"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.planning_eats)


        //DATABASE

        //Inizio prova Room 2/2
        //Metti comunque ID come valore univoco, poi estrai con QUERY: data *questa data*, buttami fuori tutto occorrente
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //db.userDao().insertAll(User(2, "UU", "YEE"))
            }
        }



        //Fine prova Room



        val backButton = findViewById<Button>(R.id.backButton)

        val testoRicevuto = intent.getStringExtra("date")

        val textView = findViewById<TextView>(R.id.provolone)
        textView.text = testoRicevuto     //RIMETTILO


        //Prova ROOM INIZIO
        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                val users: List<User> = getUsersFromDatabase()
                val usersText = StringBuilder()
                for (user in users) {
                    usersText.append("${user.firstName}\n")
                    // Se hai altre informazioni sull'utente, puoi aggiungerle qui
                }
                // Imposta il testo dell'EditText con la lista degli utenti
                breakfastID = findViewById<TextView>(R.id.breakfastID)
                //Fai output da customOutputOnTextView
                breakfastID.text = usersText.toString()
            }
        }
        //Prova ROOM FINE



        backButton.setOnClickListener {
            val intent = Intent(this, PlanYourEats::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    //funzione da activity, ho messo linearLayout come bottone

    fun onClickBreakfast(view: View) {
        viewBLD = findViewById<TextView>(R.id.breakfastID)
        showDialog()
    }

    fun onClickLaunch(view: View) {
        viewBLD = findViewById<TextView>(R.id.launchID)
        showDialog()
    }

    fun onClickDinner(view: View) {
        viewBLD = findViewById<TextView>(R.id.dinnerID)
        showDialog()
    }

    //Ogni volta che si aggiunge un ingrediente, c'è il tasto EDIT.svg che permette di editare la editText
    @SuppressLint("InflateParams")
    fun showDialog() {

        //mostra Alertdialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(R.layout.alert_dialog_planning)
        alertDialogBuilder.setPositiveButton("SAVE") { dialog, _ ->

            //nome piatto
            val mealNameEditText = alertDialog.findViewById<EditText?>(R.id.mealName)
            mealNameText = mealNameEditText?.text.toString()

            //chiamata della stampa su textView
            customOutputOnTextView(mealOutput, viewBLD)

            dialog.dismiss()
        }

        //global variable
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        //bottone che aggiunge sia ingredienti che quantità
        alertDialog.findViewById<Button>(R.id.addIngredientAlert)?.setOnClickListener {
            val linearLayoutAdder = alertDialog.findViewById<LinearLayout>(R.id.linearLayoutAdder)
            val linearLayout = LinearLayout(this)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linearLayout.orientation = LinearLayout.HORIZONTAL

            //ingredienti
            val editText1 = EditText(this)
            val params1 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            editText1.layoutParams = params1
            editText1.hint = "Insert first ingredient"

            //quantità
            val editText2 = EditText(this)
            val params2 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            editText2.layoutParams = params2
            editText2.hint = "Insert quantity"
            editText2.gravity = Gravity.BOTTOM

            //aggiunti alla lista
            val editTextPair = Pair(editText1, editText2)
            editTextPairsList.add(editTextPair)

            //aggiunge alla view
            linearLayoutAdder?.addView(linearLayout)
            linearLayout.addView(editText1)
            linearLayout.addView(editText2)

        }
    }

    private fun customOutputOnTextView(meal: java.lang.StringBuilder, view: TextView) {

        mealOutput.append(view.text.toString())
        mealOutput.append("• $mealNameText\n")

        for (pair in editTextPairsList) {
            val testoIngredient = pair.first.text.toString()
            mealOutput.append("\t\t- $testoIngredient")
            val testoQuantita = pair.second.text.toString()
            mealOutput.append(" ")
            mealOutput.append(testoQuantita + "\n")
        }
        editTextPairsList.clear()

        mealOutput.append("\n")
        view.text = meal.toString()
        mealOutput.clear()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}