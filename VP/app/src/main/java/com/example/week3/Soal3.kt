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

enum class GameState { INITIAL, COUNTDOWN, RUNNING, GAMEOVER }
enum class ColorItem(val label: String, val color: Color) {
    RED("RED", Color.Red), BLUE("BLUE", Color(0xFF1E88E5)), GREEN("GREEN", Color(0xFF4CAF50)),
    ORANGE("ORANGE", Color(0xFFFF9800)), BLACK("BLACK", Color.Black)
}

@Composable
fun Soal3() {
    var gameState by remember { mutableStateOf(GameState.INITIAL) }
    var isColorMode by remember { mutableStateOf(true) }
    var score by remember { mutableIntStateOf(0) }
    var bestScore by remember { mutableIntStateOf(0) }
    var strikes by remember { mutableIntStateOf(0) }

    var countdownText by remember { mutableStateOf("3") }
    var timeLeft by remember { mutableIntStateOf(5) }

    var wordIdx by remember { mutableIntStateOf(0) }
    var inkIdx by remember { mutableIntStateOf(1) }
    var isLeftColor by remember { mutableStateOf(true) }

    fun nextQuestion() {
        isColorMode = Random.nextBoolean()
        wordIdx = Random.nextInt(5)
        inkIdx = (wordIdx + Random.nextInt(1, 5)) % 5
        isLeftColor = Random.nextBoolean()
        timeLeft = 5
    }

    LaunchedEffect(gameState) {
        if (gameState == GameState.COUNTDOWN) {
            for (text in listOf("3", "2", "1", "Start!")) {
                countdownText = text; delay(700)
            }
            nextQuestion()
            gameState = GameState.RUNNING
        }
    }

    LaunchedEffect(gameState, timeLeft) {
        if (gameState == GameState.RUNNING) {
            if (timeLeft > 0) {
                delay(1000); timeLeft--
            } else {
                strikes++; if (strikes >= 3) gameState = GameState.GAMEOVER else nextQuestion()
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
            GameState.INITIAL -> {
                Text(
                    "Welcome\nto\nColor Word Matching",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = { score = 0; strikes = 0; gameState = GameState.COUNTDOWN },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5),
                        contentColor = Color.Black
                    )
                ) { Text("Start Game") }
            }

            GameState.COUNTDOWN -> Text(countdownText, fontSize = 48.sp, color = Color.Gray)
            GameState.RUNNING -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Mode: ${if (isColorMode) "COLOR" else "TEXT"}",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Text(
                        "✅ $score  ❌ $strikes/3",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text("$timeLeft s", fontSize = 24.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    ColorItem.values()[wordIdx].label,
                    fontSize = 52.sp,
                    color = ColorItem.values()[inkIdx].color
                )
                Spacer(modifier = Modifier.height(80.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    listOf(true, false).forEach { isLeftBtn ->
                        val btnItem =
                            ColorItem.values()[if (isLeftBtn == isLeftColor) inkIdx else wordIdx]
                        Button(
                            onClick = {
                                if (isColorMode == (isLeftBtn == isLeftColor)) {
                                    score++; if (score > bestScore) bestScore = score
                                } else strikes++
                                if (strikes >= 3) gameState = GameState.GAMEOVER else nextQuestion()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCFD8DC),
                                contentColor = Color.DarkGray
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) { Text(btnItem.label, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                    }
                }
            }

            GameState.GAMEOVER -> {
                Text(
                    "Game Over!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Your Score: $score",
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Text("Best Score: $bestScore", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = { score = 0; strikes = 0; gameState = GameState.COUNTDOWN },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(160.dp)
                ) { Text("Restart Game") }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { gameState = GameState.INITIAL },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(160.dp)
                ) { Text("Exit") }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal3Preview() {
    Soal3()
}
