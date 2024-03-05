package com.example.planeat.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ricette")
class Recipe {
    @PrimaryKey(autoGenerate = true)
    private val id = 0
    private val nome: String? = null
    private val procedimento: String? = null    // Getter e Setter
    private val position: String = "Breakfast"  //Se metterlo in breakfast, launch o dinner
    private val date: String = ""               //where date = dataSelezionata
}


@Entity(tableName = "ingredienti")
class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private val id = 0
    private val idRicetta = 0 // Chiave esterna
    private val nome: String? = null
    private val quantita = 0.0
    private val unitaMisura: String? = null // Getter e Setter
}