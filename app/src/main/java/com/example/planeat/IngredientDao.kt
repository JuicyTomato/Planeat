package com.example.planeat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
                        //valuta se usare upsert o sia insert che update
    @Upsert             //misto tra update ed inserimento... Quindi se già esistesse l'ingrediente, aggiorna
    suspend fun upsertInsertIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)        //suspend per core routines

    @Query("SELECT * FROM Ingredient ORDER BY ingredientName ASC")
    fun getIngredientsByName(): Flow<List<Ingredient>>      //Flow ci avverte se ci fosse qualche cambiamento (si aggiorna)

    @Query("SELECT * FROM Ingredient ORDER BY expireDate DESC")
    fun getIngredientsByExpirationDate(): Flow<List<Ingredient>>

}