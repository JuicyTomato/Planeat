package com.example.planeat.provaRoom

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


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
    //    @ColumnInfo(name = "starred") val starred: Boolean     //se è nelle ricette salvate
    //    @ColumnInfo(name = "group") val group: String?         //in che gruppo è, se pasta, se secondi...

){
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
}

@Entity
class Ingredient(

    @ColumnInfo(name = "name") val nameIngredient: String?,
    @ColumnInfo(name = "quantity") val quantity: Int?,
    @ColumnInfo(name = "unity_mes") val unityMes: String?,
    @ColumnInfo(name = "recipe_id") val recipeID: Long           //foreign key su Recipe su uid (sulla primary key)

){
    @PrimaryKey(autoGenerate = true) var idR: Long = 0
}


//one-to-many relationship
data class RecipeWithIngredient(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "uid",
        entityColumn = "recipe_id"
    )
    val ingredients: List<Ingredient>
)