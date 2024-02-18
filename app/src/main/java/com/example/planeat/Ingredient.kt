package com.example.planeat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Ingredient (
    val ingredientName: String,
    val expireDate: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 //default è 0
)