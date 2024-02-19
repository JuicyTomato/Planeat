package com.example.planeat

data class IngredientState(
    val ingredients: List<Ingredient> = emptyList(),
    val ingredientName: String = "",
    val expirationDate: String = "",
    val phoneNumber: String = "",
    val isAddingIngredient: Boolean = false,
    val sortType: SortType = SortType.NAME
)