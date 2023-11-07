package com.crxapplications.morsy.flows.morse.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crxapplications.morsy.flows.morse.domain.model.LetterCode
import com.crxapplications.morsy.flows.morse.domain.model.Symbol

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MorseCodeView(
    code: List<LetterCode>,
    showLetter: Boolean = true,
    currentPlayedIndexes: Pair<Int, Int>,
) {
    Column{
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Top, modifier = Modifier.padding(top = 8.dp)
        ) {
            code.forEachIndexed { index, letterCode ->
                val fullyPlayed =
                    index < currentPlayedIndexes.first || index == currentPlayedIndexes.first
                            && currentPlayedIndexes.second == letterCode.code.size - 1

                val currentPlayedIndex = if (fullyPlayed) {
                    letterCode.code.size
                } else {
                    if (index == currentPlayedIndexes.first) {
                        currentPlayedIndexes.second
                    } else {
                        -1
                    }
                }

                if (letterCode.letter != " ") {
                    CodeComponent(
                        letterCode = letterCode,
                        showLetter = showLetter,
                        fullyPlayed = fullyPlayed,
                        currentPlayedIndex = currentPlayedIndex
                    )
                } else {
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }

}

@Composable
fun CodeComponent(
    letterCode: LetterCode,
    showLetter: Boolean,
    fullyPlayed: Boolean,
    currentPlayedIndex: Int,
) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
        ) {

            if (showLetter) {
                Text(
                    letterCode.letter,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (fullyPlayed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.4f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Row {
                letterCode.code.forEachIndexed { index, symbol ->
                    val color = if (index <= currentPlayedIndex) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                    }

                    val dotSize = 6
                    when (symbol) {
                        Symbol.DOT -> {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(dotSize.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }

                        Symbol.DASH -> {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(width = (dotSize * 3).dp, height = dotSize.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color)
                            )
                        }

                        else -> {
                            Box(
                                modifier = Modifier.size(width = 0.dp, height = dotSize.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}