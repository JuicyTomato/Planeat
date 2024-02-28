package com.example.planeat

import com.example.planeat.entities.IngredientFridge

data class IngredientState(
    val ingredientFridges: List<IngredientFridge> = emptyList(),
    val ingredientName: String = "",
    val expirationDate: String = "",
    val isAddingIngredient: Boolean = false,
    val sortType: SortType = SortType.NAME
)