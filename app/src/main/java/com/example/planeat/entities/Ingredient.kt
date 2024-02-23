package com.example.planeat.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    val ingredientName: String,
    val expirationDate: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


/*
@Entity()
data class Ingredient (
    val ingredientName: String,
    val expireDate: Date? = Date(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 //default è 0
)
 */