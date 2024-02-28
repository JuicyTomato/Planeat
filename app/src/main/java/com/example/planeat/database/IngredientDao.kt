package com.example.planeat.database

import androidx.room.*
import com.example.planeat.entities.IngredientFridge
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Upsert
    suspend fun upsertIngredient(ingredientFridge: IngredientFridge)

    @Delete
    suspend fun deleteIngredient(ingredientFridge: IngredientFridge)

    @Query("SELECT * FROM IngredientFridge ORDER BY ingredientName ASC")
    fun getIngredientsOrderedByIngredientsName(): Flow<List<IngredientFridge>>

    @Query("SELECT * FROM IngredientFridge ORDER BY expirationDate ASC")
    fun getIngredientsOrderedByExpirationDate(): Flow<List<IngredientFridge>>

}