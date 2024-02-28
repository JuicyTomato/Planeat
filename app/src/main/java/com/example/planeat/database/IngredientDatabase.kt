package com.example.planeat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.planeat.entities.IngredientFridge

@Database(
    entities = [IngredientFridge::class],
    version = 1
)
abstract class IngredientDatabase: RoomDatabase() {

    abstract val dao: IngredientDao
}



//Al posto che migrare da un DB all'altro (tanto non ho dati da salvare, qualora ci fossero per esempio, un futuro
// aggiornamento, devi attuare migrazione. Vedi questa repo: https://androidessence.com/mastering-room-database-migrations).
//Comunque per resettare: vai su view > tool Windows >device Explorer > data > data > com.example.planeat > databases > elimina tutto nella cartella (tanto crea in automatico tutto i necessario)