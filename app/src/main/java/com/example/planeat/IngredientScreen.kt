package com.example.planeat

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planeat.states.IngredientState


//se data corrente > data inserita, allora colorati di rosso
//cambiare font
//cambiare UI
//mettere back

@Composable
fun IngredientScreen(
    state: IngredientState,
    onEvent: (IngredientEvent) -> Unit
) {
    //importa qui i drawable
    val backRect: Painter = painterResource(id = R.drawable.background_rectangle)
    val backArrow: Painter = painterResource(id = R.drawable.back_triangle)
    val addCircle: Painter = painterResource(id = R.drawable.addcirclebutton)

    val context = LocalContext.current


    Scaffold(
        floatingActionButton = {
            Box(modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                Button(
                    modifier = Modifier.
                    background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)   //ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                                                        // per renderla smooth l'apertura e non dal basso... Se riesci falla laterale
                }) {
                    Icon(
                        painter = backArrow,
                        contentDescription = "back Arrow",
                        modifier = Modifier
                            .align(Alignment.Top),
                        tint = Color(0xFFBBBDC9)
                    )
                    Text(text = " back",
                        color = Color(0xFFBBBDC9),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.relay_inter_bold)),
                            fontSize = 18.sp
                        )
                    )

                }
                FloatingActionButton(
                    backgroundColor = Color.Transparent,
                    modifier = Modifier
                        .align(BottomEnd),
                    onClick = {
                        onEvent(IngredientEvent.ShowDialog)
                    }) {

                    Icon(
                        //imageVector = Icons.Default.Add,
                        painter = addCircle,
                        contentDescription = "Add Ingredient",
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color(0xFFBBBDC9)
                    )
                }
            }
        }

    ) { _ ->
        if(state.isAddingIngredient) {
            AddIngredientDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF1D1B20)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "FRIDGE",
                        color = Color(0xFFBBBDC9),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.relay_inter_bold)),
                            fontSize = 30.sp
                                ),
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF1D1B20))
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .background(color = Color(0xFF1D1B20))  //name e date sorting text backgorund
                                .clickable {
                                    onEvent(IngredientEvent.SortIngredient(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(IngredientEvent.SortIngredient(sortType))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Text(
                                text = sortType.name,
                                color = Color(0xFFBBBDC9),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.relay_inter_bold)),
                                    fontSize = 13.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            items(state.ingredientFridges) { contact ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF1D1B20))
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = contact.ingredientName,
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.relay_inter_medium)),
                                fontSize = 20.sp
                            )
                        )
                        Text(text = contact.expirationDate, fontSize = 12.sp, color = Color.White)
                    }
                    IconButton(onClick = {
                        onEvent(IngredientEvent.DeleteIngredient(contact))
                    }) {
                        Icon(
                            tint = Color(0xFFBBBDC9),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Ingredient"
                        )
                    }
                }
            }
        }
    }

}