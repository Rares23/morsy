package com.crxapplications.morsy.flows.morse.presentation.page

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.crxapplications.morsy.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.crxapplications.morsy.core.helper.keyboardAsState
import com.crxapplications.morsy.core.helper.toString
import com.crxapplications.morsy.flows.morse.domain.model.Prompt
import com.crxapplications.morsy.flows.morse.presentation.view.HistoryPromptCard
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.PromptFormEvent
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.PromptFormState
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.PromptFormViewModel
import com.crxapplications.morsy.ui.theme.Grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptFormPage(
    promptFormViewModel: PromptFormViewModel = hiltViewModel(),
    openConverterPage: (String) -> Unit,
) {
    val context = LocalContext.current

    val state by promptFormViewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val isKeyboardShown by keyboardAsState()

    if (!isKeyboardShown) {
        focusManager.clearFocus()
    }

    var text = ""
    var promptsList: List<Prompt> = emptyList()

    (state as? PromptFormState.DataLoadedState)?.let {
        text = it.promptInputValue
        promptsList = it.promptsHistory
    }

    LaunchedEffect(Unit) {
        promptFormViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message.toString(context), Toast.LENGTH_LONG).show()
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                if (state is PromptFormState.LoadingState) {
                    return@Surface Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                LazyColumn {
                    item {
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                promptFormViewModel.addEvent(PromptFormEvent.OnTextChangeEvent(text = it))
                            },
                            label = { Text(text = stringResource(id = R.string.message_input_label)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                            ),
                            maxLines = 10,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .padding(all = 16.dp)
                        )
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = stringResource(id = R.string.message_info_icd),
                                tint = Grey
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.message_info_label),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Grey
                            )
                        }
                    }
                    items(
                        count = promptsList.size,
                    ) { index ->
                        HistoryPromptCard(
                            prompt = promptsList[index]
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            val showGenerateButton = state is PromptFormState.DataLoadedState &&
                    (state as? PromptFormState.DataLoadedState)?.promptInputValue?.isEmpty() != true
            AnimatedVisibility(
                visible = showGenerateButton,
                enter = slideInVertically(initialOffsetY = { it * 2 }),
                exit = slideOutVertically(targetOffsetY = { it * 2 })
            ) {
                FloatingActionButton(onClick = {
                    // Clear focus and hide keyboard
                    focusManager.clearFocus()

                    // Submit the new prompt for conversion
                    promptFormViewModel.addEvent(
                        PromptFormEvent.SubmitNewPromptEvent(
                            text = text,
                            openConverter = openConverterPage
                        )
                    )
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_generate),
                        contentDescription = stringResource(id = R.string.generate_icd)
                    )
                }
            }
        },
    )
}