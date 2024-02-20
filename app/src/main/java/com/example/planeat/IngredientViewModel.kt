package com.example.planeat

import android.app.ActivityOptions
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class IngredientViewModel(
    private val dao: IngredientDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.NAME -> dao.getIngredientsOrderedByIngredientsName()
                SortType.DATE -> dao.getIngredientsOrderedByExpirationDate()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(IngredientState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            ingredients = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IngredientState())

    fun onEvent(event: IngredientEvent) {
        when(event) {
            is IngredientEvent.DeleteIngredient -> {
                viewModelScope.launch {
                    dao.deleteIngredient(event.ingredient)
                }
            }
            IngredientEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingIngredient = false
                ) }
            }
            IngredientEvent.SaveIngredient -> {
                val ingredientName = state.value.ingredientName
                val expirationDate = state.value.expirationDate

                if(ingredientName.isBlank() || expirationDate.isBlank()) {
                    return
                }

                val ingredient = Ingredient(
                    ingredientName = ingredientName,
                    expirationDate = expirationDate,
                )
                viewModelScope.launch {
                    dao.upsertIngredient(ingredient)
                }
                _state.update { it.copy(
                    isAddingIngredient = false,
                    ingredientName = "",
                    expirationDate = "",
                ) }
            }
            is IngredientEvent.SetIngredientName -> {
                _state.update { it.copy(
                    ingredientName = event.ingredientName
                ) }
            }
            is IngredientEvent.SetExpirationDate -> {
                _state.update { it.copy(
                    expirationDate = event.expirationDate
                ) }
            }
            IngredientEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingIngredient = true
                ) }
            }
            is IngredientEvent.SortIngredient -> {
                _sortType.value = event.sortType
            }
        }
    }
}