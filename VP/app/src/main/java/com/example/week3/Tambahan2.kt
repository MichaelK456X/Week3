package com.example.week3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Tambahan2() {
    var spotWisata by rememberSaveable { mutableStateOf("Fjellheisen Cable Car") }
    var pengalaman by rememberSaveable { mutableStateOf("Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...") }
    var catatan by rememberSaveable { mutableStateOf("") }
    var kepuasan by rememberSaveable { mutableStateOf("Luar Biasa ★") }

    val fieldColors = TextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedContainerColor = Color(0xFF1E2638),
        unfocusedContainerColor = Color(0xFF1E2638),
        focusedLabelColor = Color(0xFF90A4AE),
        unfocusedLabelColor = Color(0xFF90A4AE),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
    val fieldShape = RoundedCornerShape(12.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F141C))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF287ED2))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "LOG PERJALANAN",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Tromsø, Norway ❄",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Ekspedisi Aurora Borealis", fontSize = 13.sp, color = Color.White)
                }
            }

            TextField(
                value = spotWisata,
                onValueChange = { spotWisata = it },
                label = {
                    Text(
                        "SPOT WISATA FAVORIT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = fieldShape,
                colors = fieldColors
            )

            TextField(
                value = pengalaman,
                onValueChange = { pengalaman = it },
                label = {
                    Text(
                        "APA YANG PALING KAMU NIKMATI?",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                minLines = 3,
                modifier = Modifier.fillMaxWidth(),
                shape = fieldShape,
                colors = fieldColors
            )

            TextField(
                value = catatan,
                onValueChange = { catatan = it },
                label = {
                    Text(
                        "CATATAN TAMBAHAN / PERLENGKAPAN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                placeholder = {
                    Text(
                        "(Ketik perlengkapan ekstra di sini...)",
                        color = Color(0xFF546E7A)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = fieldShape,
                colors = fieldColors
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)) {
                Text(
                    "TINGKAT KEPUASAN:",
                    fontSize = 11.sp,
                    color = Color(0xFF78909C),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Biasa", "Seru", "Luar Biasa ★").forEach { opsi ->
                        val active = kepuasan == opsi
                        Box(
                            modifier = Modifier
                                .background(
                                    if (active) Color(0xFF1976D2) else Color(0xFF1E2638),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { kepuasan = opsi }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                opsi,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Text("Status: Draft Tersimpan", fontSize = 12.sp, color = Color(0xFF78909C))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp)
        ) {
            Button(
                onClick = { },
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("+", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun tambahan2Preview() {
    Tambahan2()
}
