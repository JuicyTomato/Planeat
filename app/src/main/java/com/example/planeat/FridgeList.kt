package com.example.planeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class FridgeList : ComponentActivity() {    //cambia in: AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "Contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                 override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fridge_list)
        setContent {
            //RoomGuideAndroidTheme {
                val state by viewModel.state.collectAsState()
                ContactScreen(state = state, onEvent = viewModel::onEvent)
            //}
        }

    }
}
