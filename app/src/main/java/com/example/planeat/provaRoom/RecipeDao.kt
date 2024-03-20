package com.example.planeat.provaRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface RecipeDao {

    //il get è sui PAIR
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM groupsh")
    fun getAllShared(): List<GroupSh>

    //ottieni tutti ingredienti
    @Query("SELECT * FROM ingredient")
    fun getAllIngredients(): List<Ingredient>

    //nel textWatcher in RecipeActivity che, quando aggiungi un ingrediente, ti mostra quelli già presenti
    @Query("SELECT * FROM ingredient WHERE ingredient_name LIKE :begin || '%'")
    fun getAllIngredientsBeginLike(begin: String): List<Ingredient>

    //ottieni ingrediente da id
    @Query("SELECT * FROM ingredient WHERE ingredient_name = :type")
    fun getAllIngredientsWhereName(type: String): List<Ingredient>

    //ottieni ricette data la data inserita
    @Transaction
    @Query("SELECT * FROM recipe WHERE date = :type")
    fun getAllWhereDate(type: String): List<RecipeWithIngredientPair>

    //ottieni ricette dato il nome del gruppo inserito
    @Transaction
    @Query("SELECT * FROM groupsh WHERE group_name == :type")
    fun getNameWhereGroup(type: String): List<RecipeWithGroupPair>

    //ottieni ricette ed ingredienti dato id
    @Transaction
    @Query("SELECT * FROM recipe WHERE idRecipe == :type")
    fun getRecipeWithIngredientById(type: Long): List<RecipeWithIngredientPair>

    @Query("SELECT * FROM recipe WHERE idRecipe IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Recipe>

    @Query("SELECT * FROM recipe WHERE recipe_name LIKE :first AND " +
            "process LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Recipe

    @Query("SELECT * FROM GroupSh WHERE group_name = :name")
    fun findByNameShared(name: String): GroupSh


    //insert RECIPE-INGREDIENT IDs  +  quantity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipeIngredient(join: RecipeIngredient)

    //insert RECIPE-GROUP IDs
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipeGroup(join: RecipeGroup)

    //inserisce Recipe
    @Insert
    fun insertAll(recipes: Recipe): Long

    //update ricetta... Ma perchè allora si chiama updateIngredient
    @Update
    fun updateRecipe(recipe: Recipe)

    //inserisci ingrediente
    @Insert
    fun insertIngredient(ingredients:Ingredient): Long

    @Insert
    fun insertShared(groupsh: GroupSh): Long

    @Delete
    fun deleteShared(groupsh: GroupSh)

    @Delete
    fun delete(recipe: Recipe)
}