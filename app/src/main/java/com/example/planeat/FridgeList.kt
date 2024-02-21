package com.example.planeat

import android.app.ActivityOptions
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class FridgeList : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            IngredientDatabase::class.java,
            "Ingredient.db"
        ).build()
    }

    private val viewModel by viewModels<IngredientViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return IngredientViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        setContent {
            //RoomGuideAndroidTheme {
                val state by viewModel.state.collectAsState()
                IngredientScreen(state = state, onEvent = viewModel::onEvent)
            //}
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
