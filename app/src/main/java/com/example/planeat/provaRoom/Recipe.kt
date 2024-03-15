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
    @ColumnInfo(name = "recipe_name") val recipeName: String?,  //nome ricetta
    @ColumnInfo(name = "process") val process: String?,         //processo per fare ricetta ("metti acqua, butta pasta...")
    @ColumnInfo(name = "date") val date: String?,               //data creazione recipe così nel load del giorno ho ricette corrette
    @ColumnInfo(name = "position") val position: String,        //se è in breakfast, launch o dinner
    @ColumnInfo(name = "groupz") val group: String?,
    //@ColumnInfo(name = "starred") val starred: Int,             //se è nelle ricette salvate
    //eliminata perchè inutile... BAsta utilizare solo groupz
    //    @ColumnInfo(name = "future") val future: String?      //uno string per il futuro... Così mi evito migrations strane... Nah forse non lo faccio
    @PrimaryKey(autoGenerate = true) val uid: Long = 0              //spostato su, prima al posto di constructor
){
    //Se primary non inserita la genera casuale
    constructor(id: Long, recipeName: String?, process: String?, date: String?, position: String, group: String?)
            : this(uid = id, recipeName = recipeName, process = process, date = date, position = position, group = group)
}

@Entity
class Ingredient(
    @ColumnInfo(name = "name") val nameIngredient: String?,      //nome ingrediente
    @ColumnInfo(name = "quantity") val quantity: Int?,           //quantità ingrediente
    @ColumnInfo(name = "unity_mes") val unityMes: String?,       //unità di misura della quantità (ad ora fissata a 'gr')
    @ColumnInfo(name = "recipe_id") val recipeID: Long           //foreign key su Recipe su uid (sulla primary key)

){
    @PrimaryKey(autoGenerate = true) var idR: Long = 0
}


//many-to-many relationship         //prima scritto come one-to-may relationship
data class RecipeWithIngredient(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "uid",
        entityColumn = "recipe_id"
    )
    val ingredients: List<Ingredient>
)