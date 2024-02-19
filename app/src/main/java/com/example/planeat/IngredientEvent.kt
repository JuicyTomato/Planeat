package com.example.planeat
sealed interface IngredientEvent {
    object SaveIngredient: IngredientEvent
    data class SetIngredientName(val ingredientName: String): IngredientEvent
    data class SetExpirationDate(val expirationDate: String): IngredientEvent
    //elimina phone number
    data class SetPhoneNumber(val phoneNumber: String): IngredientEvent
    object ShowDialog: IngredientEvent
    object HideDialog: IngredientEvent
    data class SortIngredient(val sortType: SortType): IngredientEvent
    data class DeleteIngredient(val ingredient: Ingredient): IngredientEvent
}