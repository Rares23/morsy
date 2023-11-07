package com.crxapplications.morsy.flows.morse.presentation.page

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crxapplications.morsy.R
import com.crxapplications.morsy.core.helper.toString
import com.crxapplications.morsy.flows.morse.presentation.view.MorseCodeView
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.ConverterEvent
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.ConverterState
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.ConverterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterPage(
    text: String,
    converterViewModel: ConverterViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val state by converterViewModel.state.collectAsState()
    val playedIndexes by converterViewModel.playedIndex.collectAsState()

    LaunchedEffect(Unit) {
        converterViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message.toString(context), Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        if (state.isLoading) {
            converterViewModel.addEvent(ConverterEvent.ConvertTextEvent(text = text))
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                stringResource(id = R.string.converter_page_title),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ), navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        })
    }, content = { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (state.isLoading) {
                return@Surface Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = 16.dp
                    )
                    .fillMaxWidth(),

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            converterViewModel.addEvent(ConverterEvent.ToggleSoundState)
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(.5f)
                            .padding(start = 16.dp, end = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val iconDrawable = if (state.soundEnabled) {
                                R.drawable.ic_sound_on
                            } else {
                                R.drawable.ic_sound_off
                            }

                            Icon(
                                painter = painterResource(id = iconDrawable),
                                contentDescription = stringResource(id = R.string.sound_button),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            converterViewModel.addEvent(ConverterEvent.ToggleFlashState)
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(.5f)
                            .padding(start = 8.dp, end = 16.dp)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val iconDrawable = if (state.flashEnabled) {
                                R.drawable.ic_flash_on
                            } else {
                                R.drawable.ic_flash_off
                            }

                            Icon(
                                painter = painterResource(id = iconDrawable),
                                contentDescription = stringResource(id = R.string.flash_button_icd),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        stringResource(id = R.string.speed_slider_label),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Slider(value = state.frequency, onValueChange = { value ->
                        converterViewModel.addEvent(ConverterEvent.ChangeFrequencyEvent(value))
                    })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        stringResource(id = R.string.message_label),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(state.text, style = MaterialTheme.typography.titleLarge)
                }

                MorseCodeView(
                    code = state.code, currentPlayedIndexes = playedIndexes
                )
            }
        }
    }, floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
        FloatingActionButton(onClick = {
            if (state.isPlaying) {
                converterViewModel.addEvent(ConverterEvent.StopEvent)
            } else {
                converterViewModel.addEvent(ConverterEvent.PlayEvent)
            }
        }) {
            val drawable = if (state.isPlaying) {
                R.drawable.ic_stop
            } else {
                R.drawable.ic_play
            }
            Icon(
                painter = painterResource(
                    id = drawable
                ), contentDescription = stringResource(id = R.string.play_code_icd)
            )
        }
    })
}