package com.example.planeat.database

import androidx.room.*
import com.example.planeat.entities.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Upsert
    suspend fun upsertIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient ORDER BY ingredientName ASC")
    fun getIngredientsOrderedByIngredientsName(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredient ORDER BY expirationDate ASC")
    fun getIngredientsOrderedByExpirationDate(): Flow<List<Ingredient>>

}