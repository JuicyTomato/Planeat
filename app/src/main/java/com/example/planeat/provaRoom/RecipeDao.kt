package com.example.planeat.provaRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Transaction
    @Query("SELECT * FROM recipe WHERE date = :type")
    fun getAllWhereDate(type: String): List<RecipeWithIngredient>


    //dubbio... Ha senso starred? se tanto aggiungo al gruppo... Quindi se avessi gruppoShared != null
    // allora vuol dire che appartiene al gruppo segnato. Altrimenti no... Quindi starred non ha senso

    @Transaction
    @Query("SELECT * FROM recipe WHERE groupz == :type")
    fun getNameWhereGroup(type: String): List<Recipe>

    @Transaction
    @Query("SELECT * FROM recipe WHERE uid == :type")
    fun getRecipeWithIngredientById(type: Long): List<RecipeWithIngredient>


    @Query("SELECT * FROM recipe WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Recipe>

    @Query("SELECT * FROM recipe WHERE recipe_name LIKE :first AND " +
            "process LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Recipe

    @Insert
    fun insertAll(recipes: Recipe): Long

    @Update
    fun updateIngredient(recipe: Recipe)

    //penso anche questo come @Upsert
    @Insert
    fun insertIngredient(vararg ingredients:Ingredient)

    @Delete
    fun delete(recipe: Recipe)
}