package com.example.planeat.provaRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*  //originale
@Entity
data class Recipe(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
)
*/

@Entity
class Recipe(
    @ColumnInfo(name = "recipe_name") val recipeName: String?,
    @ColumnInfo(name = "process") val process: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "position") val position: String

){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}


/*
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
 */