package com.crxapplications.morsy.flows.morse.presentation.page

import android.view.ViewTreeObserver
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.crxapplications.morsy.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.crxapplications.morsy.core.helper.keyboardAsState
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.PromptFormEvent
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.PromptFormState
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.PromptFormViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PromptFormPage(
    promptFormViewModel: PromptFormViewModel = hiltViewModel(),
    convertOnPress: (Int?, String?) -> Unit,
) {

    val state by promptFormViewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val isKeyboardShown by keyboardAsState()

    if(!isKeyboardShown) {
        focusManager.clearFocus()
    }

    val text = (state as? PromptFormState.DataLoadedState)?.promptInputValue ?: ""

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column {
                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            promptFormViewModel.addEvent(PromptFormEvent.OnTextChangeEvent(text = it))
                        },
                        label = { Text(text = "Messsage") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                        ),
                        maxLines = 10,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(all = 16.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            val showGenerateButton =
                (state as? PromptFormState.DataLoadedState)?.promptInputValue?.isEmpty() != true

            if (showGenerateButton) {
                FloatingActionButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_generate),
                        contentDescription = stringResource(id = R.string.generate_icd)
                    )
                }
            }
        },
    )
}