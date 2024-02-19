package com.example.planeat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
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