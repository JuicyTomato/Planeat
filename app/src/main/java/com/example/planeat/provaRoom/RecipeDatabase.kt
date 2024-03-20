package com.example.planeat.provaRoom

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class, Ingredient::class, GroupSh::class, RecipeIngredient::class, RecipeGroup::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}