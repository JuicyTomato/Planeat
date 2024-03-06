package com.example.planeat.provaRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE date = :type")
    fun getAllWhereDate(type: String): List<Recipe>

    @Query("SELECT * FROM recipe WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Recipe>

    @Query("SELECT * FROM recipe WHERE recipe_name LIKE :first AND " +
            "process LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Recipe

    @Insert
    fun insertAll(vararg recipes: Recipe)

    @Delete
    fun delete(recipe: Recipe)
}