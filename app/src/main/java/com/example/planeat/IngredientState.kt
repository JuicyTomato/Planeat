package com.example.planeat

import com.example.planeat.entities.Ingredient

data class IngredientState(
    val ingredients: List<Ingredient> = emptyList(),
    val ingredientName: String = "",
    val expirationDate: String = "",
    val isAddingIngredient: Boolean = false,
    val sortType: SortType = SortType.NAME
)