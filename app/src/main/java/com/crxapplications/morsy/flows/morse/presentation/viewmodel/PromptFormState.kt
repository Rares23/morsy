package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import com.crxapplications.morsy.flows.morse.domain.model.Prompt

sealed class PromptFormState {
    object LoadingState : PromptFormState()

    data class DataLoadedState(
        val promptInputValue: String,
        val promptsHistory: List<Prompt> = emptyList(),
    ) : PromptFormState()
}