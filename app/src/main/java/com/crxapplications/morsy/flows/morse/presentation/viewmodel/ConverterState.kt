package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import com.crxapplications.morsy.flows.morse.domain.model.LetterCode

sealed class ConverterState {
    object LoadingState : ConverterState()
    data class DataLoadedState(
        val text: String,
        val code: List<LetterCode>,
        val isPlaying: Boolean,
    ) : ConverterState()
}