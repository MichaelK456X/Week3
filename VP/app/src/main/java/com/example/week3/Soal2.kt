package com.example.week3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Soal2() {
    var coin by remember { mutableStateOf(0.0) }
    var baseValue by remember { mutableStateOf(1.0) }
    var upgradeCost by remember { mutableStateOf(10.0) }

    val interact = remember { MutableInteractionSource() }
    val isPressed by interact.collectIsPressedAsState()
    val canUpgrade = coin >= upgradeCost

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.kucing),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .background(Color.White.copy(0.25f), RoundedCornerShape(24.dp))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Your Coins",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${coin.toInt()}",
                    fontSize = 40.sp,
                    color = Color(0xFF00E676),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${baseValue.toInt()} coins per tap",
                    fontSize = 13.sp,
                    color = Color.White.copy(0.8f)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Tap the Cat!",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(if (isPressed) R.drawable.buka else R.drawable.tutup),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable(interact, null) { coin += baseValue }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(if (isPressed) "Meow!" else "Purr~", fontSize = 16.sp, color = Color.White)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Give Me Your Coin", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Next upgrade: +${(baseValue * 0.5).toInt()} coins per tap",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { coin -= upgradeCost; baseValue *= 1.5; upgradeCost *= 2 },
                    enabled = canUpgrade,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canUpgrade) Color(0xFF4CAF50) else Color(0xFFCCCCCC),
                        disabledContainerColor = Color(0xFFCCCCCC)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (canUpgrade) "Pay for ${upgradeCost.toInt()} coins" else "Find ${(upgradeCost - coin).toInt()} more coins",
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2mulai() {
    Soal2()
}
