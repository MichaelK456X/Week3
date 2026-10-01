package com.example.week3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class RpsState { INITIAL, PICK, REVEAL, FINISHED }
enum class Move(val emoji: String, val label: String) {
    ROCK("✊", "Rock"), PAPER("✋", "Paper"), SCISSORS("✌️", "Scissor")
}

@Composable
fun Soal4() {
    var gameState by remember { mutableStateOf(RpsState.INITIAL) }
    var myScore by remember { mutableStateOf(0) }
    var cpuScore by remember { mutableStateOf(0) }
    var bestScore by remember { mutableStateOf(0) }

    var myMove by remember { mutableStateOf(Move.ROCK) }
    var cpuMove by remember { mutableStateOf(Move.ROCK) }
    var roundResult by remember { mutableStateOf("") }

    val targetPoints = 3
    val movesList = remember { mutableStateListOf<Move>() }

    fun setupRound() {
        movesList.clear()
        movesList.addAll(Move.values().toList().shuffled())
    }

    LaunchedEffect(gameState) {
        if (gameState == RpsState.REVEAL) {
            delay(700)
            if (myScore >= targetPoints || cpuScore >= targetPoints) {
                gameState = RpsState.FINISHED
                val currentRecord = myScore - cpuScore
                if (currentRecord > bestScore) bestScore = currentRecord
            } else {
                setupRound()
                gameState = RpsState.PICK
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (gameState) {
            RpsState.INITIAL -> {
                Text(
                    "Rock • Paper • Scissors",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Best of 5 Match",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        myScore = 0; cpuScore = 0; setupRound(); gameState = RpsState.PICK
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5),
                        contentColor = Color.Black
                    )
                ) { Text("Start") }
            }

            RpsState.PICK, RpsState.REVEAL -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("🧑 $myScore — $cpuScore 🤖", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Best of 5", fontSize = 14.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    if (gameState == RpsState.PICK) "Pick your move!" else roundResult,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(if (gameState == RpsState.PICK) "❓" else myMove.emoji, fontSize = 48.sp)
                    Text(
                        "VS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                    Text(if (gameState == RpsState.PICK) "❓" else cpuMove.emoji, fontSize = 48.sp)
                }

                Spacer(modifier = Modifier.height(60.dp))

                if (gameState == RpsState.PICK) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        movesList.forEach { move ->
                            Button(
                                onClick = {
                                    myMove = move
                                    cpuMove = Move.values()[Random.nextInt(3)]
                                    roundResult = when {
                                        myMove == cpuMove -> "Draw"
                                        (myMove == Move.ROCK && cpuMove == Move.SCISSORS) ||
                                                (myMove == Move.PAPER && cpuMove == Move.ROCK) ||
                                                (myMove == Move.SCISSORS && cpuMove == Move.PAPER) -> {
                                            myScore++; "You Win!"
                                        }

                                        else -> {
                                            cpuScore++; "You Lose"
                                        }
                                    }
                                    gameState = RpsState.REVEAL
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFCFD8DC
                                    ), contentColor = Color.DarkGray
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    move.emoji + " " + move.label,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }

            RpsState.FINISHED -> {
                val wonMatch = myScore >= targetPoints
                Text(
                    if (wonMatch) "You Win the Match!" else "You Lose the Match",
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text("Final Score: 🧑 $myScore - $cpuScore 🤖", fontSize = 18.sp)
                Text(
                    "Best Score: $bestScore",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        myScore = 0; cpuScore = 0; setupRound(); gameState = RpsState.PICK
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(150.dp)
                ) { Text("Restart") }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { gameState = RpsState.INITIAL },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFCFD8DC),
                        contentColor = Color.DarkGray
                    ),
                    modifier = Modifier.width(150.dp)
                ) { Text("Exit") }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal4Preview() {
    Soal4()
}
