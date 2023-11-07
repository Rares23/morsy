package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import com.crxapplications.morsy.flows.morse.domain.model.LetterCode

data class ConverterState(
    val text: String,
    val code: List<LetterCode>,
    val isPlaying: Boolean,
    val soundEnabled: Boolean,
    val flashEnabled: Boolean,
    val frequency: Float,
    val isLoading: Boolean,
)