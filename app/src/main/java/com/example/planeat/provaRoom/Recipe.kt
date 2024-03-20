package com.example.planeat.provaRoom

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

//tabella RECIPE
@Entity
data class Recipe(
    @ColumnInfo(name = "recipe_name") val recipeName: String?,  //nome ricetta
    @ColumnInfo(name = "process") val process: String?,         //processo per fare ricetta ("metti acqua, butta pasta...")
    @ColumnInfo(name = "date") val date: String?,               //data creazione recipe così nel load del giorno ho ricette corrette
    @ColumnInfo(name = "position") val position: String,        //se è in breakfast, launch o dinner
    @PrimaryKey(autoGenerate = true) val idRecipe: Long = 0              //spostato su, prima al posto di constructor
){
    //Se primary non inserita la genera casuale
    constructor(id: Long, recipeName: String?, process: String?, date: String?, position: String)
            : this(idRecipe = id, recipeName = recipeName, process = process, date = date, position = position)
}

//tabella INGREDIENT
@Entity
data class Ingredient(
    @ColumnInfo(name = "ingredient_name") val nameIngredient: String?,      //nome ingrediente
    @ColumnInfo(name = "unity_mes") val unityMes: String?,       //unità di misura della quantità (ad ora fissata a 'gr')
    ){
    @PrimaryKey(autoGenerate = true) var idIngredient: Long = 0
}

//tabella GROUP
@Entity
data class GroupSh(
    @ColumnInfo(name = "group_name") val nameGroup: String?,          //nome del gruppo (Shared)
){
    @PrimaryKey(autoGenerate = true) var idGroup: Long = 0          //chiave primaria group (shared)
}

//tabella nel mezzo di Recipe e Group -> essendo una many-to-many
@Entity(primaryKeys = ["idRecipe", "idGroup"])
class RecipeGroup(
    val idRecipe: Long,
    val idGroup: Long
)


//tabella nel mezzo di Recipe e Ingredient -> essendo una many-to-many
@Entity(primaryKeys = ["idRecipe", "idIngredient"])
class RecipeIngredient(
    val idRecipe: Long,
    val idIngredient: Long,
    val quantity: Int                   //quantità ingrediente
)


//many-to-many relationship         //prima scritto come one-to-may relationship
//tra recipe e Ingredient
data class RecipeWithIngredientPair(
    @Embedded
    val recipe: Recipe,
    @Relation(
        parentColumn = "idRecipe",
        entity = Ingredient::class,
        entityColumn = "idIngredient",
        associateBy = Junction(
            value = RecipeIngredient::class,
            parentColumn = "idRecipe",
            entityColumn = "idIngredient"
        )
    )
    val ingredients: List<Ingredient>
)

//many-to-many relationship
//tra recipe e group
data class RecipeWithGroupPair(
    @Embedded
    val groupSh: GroupSh,
    @Relation(
        parentColumn = "idGroup",
        entity = Recipe::class,
        entityColumn = "idRecipe",
        associateBy = Junction(
            value = RecipeGroup::class,
            parentColumn = "idGroup",
            entityColumn = "idRecipe"
        )
    )
    val recipes: List<Recipe>
)