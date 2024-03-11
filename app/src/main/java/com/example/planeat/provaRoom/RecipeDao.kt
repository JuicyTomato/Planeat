package com.example.planeat.provaRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Transaction
    @Query("SELECT * FROM recipe WHERE date = :type")
    fun getAllWhereDate(type: String): List<RecipeWithIngredient>

    /*
    //per printare solo quelli starred nel gruppo specifico
    @Transaction
    @Query("SELECT * FROM recipe WHERE starred = true AND group == :type")  //controlla se giusto
    fun getAllWhereStarred(type: String): List<RecipeWithIngredient>
     */

    @Query("SELECT * FROM recipe WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Recipe>

    @Query("SELECT * FROM recipe WHERE recipe_name LIKE :first AND " +
            "process LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Recipe

    @Insert
    fun insertAll(recipes: Recipe): Long

    @Insert
    fun insertIngredient(vararg ingredients:Ingredient)

    @Delete
    fun delete(recipe: Recipe)
}