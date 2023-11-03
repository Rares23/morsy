package com.crxapplications.morsy.flows.morse.presentation.page

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        if (state is ConverterState.LoadingState) {
            converterViewModel.addEvent(ConverterEvent.ConvertTextEvent(text = text))
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hello", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                when (state) {
                    is ConverterState.LoadingState -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ConverterState.DataLoadedState -> {
                        MorseCodeView(
                            code = (state as ConverterState.DataLoadedState).code,
                            currentPlayedIndexes = playedIndexes
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                (state as? ConverterState.DataLoadedState)?.let {
                    if (it.isPlaying) {
                        converterViewModel.addEvent(ConverterEvent.StopEvent)
                    } else {
                        converterViewModel.addEvent(ConverterEvent.PlayEvent)
                    }
                }
            }) {
                if (state is ConverterState.DataLoadedState) {
                    val drawable = if ((state as ConverterState.DataLoadedState).isPlaying) {
                        R.drawable.ic_stop
                    } else {
                        R.drawable.ic_play
                    }
                    Icon(
                        painter = painterResource(
                            id = drawable
                        ),
                        contentDescription = stringResource(id = R.string.play_code_icd)
                    )
                }
            }
        }
    )
}