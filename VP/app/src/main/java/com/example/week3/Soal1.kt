package com.example.week3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class S1State { START, WAIT, GO, RESULT, FINAL }
enum class Kategori(val msg: String, val imgRes: Int) {
    FAST("DANG YOU ARE SO FAST BRO!", R.drawable.hormat),
    GOOD("YOUR REFLEX IS GOOD", R.drawable.jempol),
    MEH("MEH LIKE OTHER PERSON", R.drawable.meh),
    SNAIL("YOU LIKE A SNAIL BRO", R.drawable.noob)
}

@Composable
fun Soal1() {
    var state by remember { mutableStateOf(S1State.START) }
    var trial by remember { mutableIntStateOf(1) }
    val times = remember { mutableStateListOf<Long>() }
    var startTime by remember { mutableLongStateOf(0L) }

    val valid = times.filter { it > 0 }
    val avg = if (valid.isEmpty()) 999 else valid.average().toInt()

    val bg = when (state) {
        S1State.START -> Color(0xFF87CEEB)
        S1State.WAIT -> Color.LightGray
        S1State.GO -> Color(0xFF4CAF50)
        S1State.RESULT -> if (times.isNotEmpty() && times.last() == -1L) Color(0xFFEF4444) else Color(
            0xFF4CAF50
        )

        S1State.FINAL -> when {
            avg < 180 -> Color(0xFF22C55E); avg < 280 -> Color(0xFF3B82F6); avg < 450 -> Color(
                0xFFFF9800
            ); else -> Color(0xFFEF4444)
        }
    }

    LaunchedEffect(state) {
        if (state == S1State.WAIT) {
            delay(Random.nextLong(500L, 4500L))
            if (state == S1State.WAIT) {
                state = S1State.GO; startTime = System.currentTimeMillis()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .clickable {
                when (state) {
                    S1State.START -> state = S1State.WAIT
                    S1State.WAIT -> {
                        times.add(-1L); state = S1State.RESULT
                    }

                    S1State.GO -> {
                        times.add(System.currentTimeMillis() - startTime); state = S1State.RESULT
                    }

                    else -> {}
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically)
    ) {
        when (state) {
            S1State.START -> {
                Text(
                    "Reaction",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text("⚡", fontSize = 100.sp)
                Text(
                    "Test\n\nClick to Start",
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            S1State.WAIT -> {
                Text(
                    "Get Ready",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text("⚠️", fontSize = 50.sp)
                Text(
                    "Wait for green light..\n\n DONT CLICK YET!",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            S1State.GO -> {
                Text("GO!", fontSize = 35.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("🏃‍♂️", fontSize = 50.sp)
                Text(
                    "Click Now\n\nTAP AS FAST AS YOU CAN!",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            S1State.RESULT -> {
                val last = times.last()
                val isFail = last == -1L

                Text(
                    if (isFail) "FAIL!" else "Trial $trial Complete!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(if (isFail) "👎" else "✅", fontSize = 55.sp)
                Text(
                    if (isFail) "You clicked too early\nRead the Rules again brother" else "Time: ${last}ms",
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        if (trial < 3) {
                            trial++; state = S1State.WAIT
                        } else {
                            state = S1State.FINAL
                        }
                    }
                ) {
                    Text(if (trial < 3) "Continue to Trial ${trial + 1}" else "View Final Result")
                }

                InfoBox(times)
            }

            S1State.FINAL -> {
                val kat = when {
                    avg < 180 -> Kategori.FAST; avg < 280 -> Kategori.GOOD; avg < 450 -> Kategori.MEH; else -> Kategori.SNAIL
                }

                Text(
                    kat.msg,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(kat.imgRes),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = { times.clear(); trial = 1; state = S1State.START }
                ) {
                    Text("Click to try New Test")
                }

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Trial Results",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                    Text(
                        "1: ${fmt(times.getOrNull(0))}  |  2: ${fmt(times.getOrNull(1))}  |  3: ${
                            fmt(
                                times.getOrNull(2)
                            )
                        }", fontSize = 14.sp, color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Average Score",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                    Text(
                        "${if (valid.isEmpty()) "-" else "${avg}ms"}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun InfoBox(times: List<Long>) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Trial Results", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(
            "1: ${fmt(times.getOrNull(0))}  |  2: ${fmt(times.getOrNull(1))}  |  3: ${
                fmt(
                    times.getOrNull(
                        2
                    )
                )
            }", fontSize = 14.sp, color = Color.Black
        )
    }
}

private fun fmt(t: Long?) = if (t == null) "-" else if (t == -1L) "Fail" else "${t}ms"

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1mulai() {
    Soal1()
}
