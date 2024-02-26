package com.example.planeat

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.AlertDialog
import androidx.appcompat.app.AlertDialog




class PlanningEats : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.planning_eats)



        val backButton = findViewById<Button>(R.id.backButton)

        val testoRicevuto = intent.getStringExtra("date")

        val textView = findViewById<TextView>(R.id.provolone)
        textView.text = testoRicevuto

        backButton.setOnClickListener {
            val intent = Intent(this, PlanYourEats::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    //funzione da activity, ho messo linearLayout come bottone

    fun onClickBreakfast(view: View) {
        Toast.makeText(this, "Breakfast", Toast.LENGTH_SHORT).show()
        showDialog()
    }

    fun onClickLaunch(view: View) {
        Toast.makeText(this, "Launch", Toast.LENGTH_SHORT).show()
        showDialog()
    }
    fun onClickDinner(view: View) {
        Toast.makeText(this, "Dinner", Toast.LENGTH_SHORT).show()
        showDialog()
    }

    @SuppressLint("InflateParams")
    fun showDialog(){
        //mostra Alertdialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(R.layout.alert_dialog_planning)
        alertDialogBuilder.setPositiveButton("SAVE") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
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

            //aggiunge alla view
            linearLayoutAdder?.addView(linearLayout)
            linearLayout.addView(editText1)
            linearLayout.addView(editText2)


            // Bottone che aggiunge sia ingredienti che quantità
            alertDialog.findViewById<Button>(R.id.addIngredientAlert)?.setOnClickListener {
                val linearLayoutAdder = alertDialog.findViewById<LinearLayout>(R.id.linearLayoutAdder)
                val linearLayout = LinearLayout(this)
                linearLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                linearLayout.orientation = LinearLayout.HORIZONTAL

                // Ingredienti
                val editText1 = EditText(this)
                val params1 = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                editText1.layoutParams = params1
                editText1.hint = "Insert first ingredient"

                // Quantità
                val editText2 = EditText(this)
                val params2 = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                editText2.layoutParams = params2
                editText2.hint = "Insert quantity"
                editText2.gravity = Gravity.BOTTOM

                // Aggiunta delle EditText al layout
                linearLayoutAdder?.addView(linearLayout)
                linearLayout.addView(editText1)
                linearLayout.addView(editText2)

                //listener di editText1
                editText1.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        //non da implementare
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        //non da implementare
                    }

                    override fun afterTextChanged(s: Editable?) {
                        //input editText1
                        val inputText1 = s.toString()
                        //aggiunti a stringona enorme per visualizzare poi su viewText
                        //Roomizza
                    }
                })

                //listener di editText2
                editText2.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        //non da implementare
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        //non da implementare
                    }

                    override fun afterTextChanged(s: Editable?) {
                        //input editText2
                        val inputText2 = s.toString()
                        //in ROOM
                    }
                })
            }

        }

    }

}