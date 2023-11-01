package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromptFormViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<PromptFormState>(PromptFormState.LoadingState)
    val state: StateFlow<PromptFormState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            //TODO: load prompts history

            _state.emit(
                PromptFormState.DataLoadedState(
                    promptInputValue = "",
                )
            )
        }
    }

    fun addEvent(event: PromptFormEvent) {
        when (event) {
            is PromptFormEvent.OnTextChangeEvent -> onTextChangeEvent(event)
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
}