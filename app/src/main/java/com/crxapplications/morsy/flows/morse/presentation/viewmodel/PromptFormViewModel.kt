package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.UiText
import com.crxapplications.morsy.flows.morse.domain.model.Prompt
import com.crxapplications.morsy.flows.morse.domain.usecase.GetPromptsHistoryUseCase
import com.crxapplications.morsy.flows.morse.domain.usecase.SavePromptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PromptFormViewModel @Inject constructor(
    private val savePromptUseCase: SavePromptUseCase,
    private val getPromptsHistoryUseCase: GetPromptsHistoryUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<PromptFormState>(PromptFormState.LoadingState)
    val state: StateFlow<PromptFormState> = _state.asStateFlow()

    private val _toastMessage = MutableSharedFlow<UiText>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.emit(PromptFormState.LoadingState)

            when (val response = getPromptsHistoryUseCase.invoke()) {
                is Response.SuccessResponse -> {
                    _state.emit(
                        PromptFormState.DataLoadedState(
                            promptInputValue = "",
                            promptsHistory = response.value
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    _state.emit(
                        PromptFormState.DataLoadedState(
                            promptInputValue = "",
                        )
                    )

                    _toastMessage.emit(response.message)
                }
            }
        }
    }

    fun addEvent(event: PromptFormEvent) {
        when (event) {
            is PromptFormEvent.OnTextChangeEvent -> onTextChangeEvent(event)
            is PromptFormEvent.SubmitNewPromptEvent -> onSubmitNewPromptEvent(event)
        }
    }

    private fun onTextChangeEvent(event: PromptFormEvent.OnTextChangeEvent) {
        viewModelScope.launch {
            when (val state = state.value) {
                is PromptFormState.DataLoadedState -> {
                    _state.emit(
                        state.copy(
                            promptInputValue = event.text
                        )
                    )
                }

                else -> {}
            }

        }
    }

    private fun onSubmitNewPromptEvent(event: PromptFormEvent.SubmitNewPromptEvent) {
        viewModelScope.launch {
            // save the new prompt
            when (val state = state.value) {
                is PromptFormState.DataLoadedState -> {
                    when (val response = savePromptUseCase.invoke(event.text)) {
                        is Response.SuccessResponse -> {
                            // Add the new prompt into the history list
                            _state.emit(
                                state.copy(
                                    promptInputValue = "",
                                    promptsHistory = state.promptsHistory.plus(
                                        response.value
                                    )
                                )
                            )

                            // Open the converter with the specific text
                            event.openConverter(event.text)
                        }

                        is Response.ErrorResponse -> {
                            _toastMessage.emit(response.message)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}