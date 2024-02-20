package com.example.planeat

import android.widget.Button

sealed interface IngredientEvent {
    object SaveIngredient: IngredientEvent
    data class SetIngredientName(val ingredientName: String): IngredientEvent
    data class SetExpirationDate(val expirationDate: String): IngredientEvent
    //elimina phone number
    object ShowDialog: IngredientEvent
    object HideDialog: IngredientEvent
    data class SortIngredient(val sortType: SortType): IngredientEvent
    data class DeleteIngredient(val ingredient: Ingredient): IngredientEvent
}