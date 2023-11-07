package com.crxapplications.morsy.flows.morse.presentation.viewmodel

sealed class ConverterEvent {
    data class ConvertTextEvent(val text: String): ConverterEvent()
    object PlayEvent: ConverterEvent()
    object StopEvent: ConverterEvent()
    object ToggleSoundState: ConverterEvent()
    object ToggleFlashState: ConverterEvent()
    data class ChangeFrequencyEvent(val value: Float) : ConverterEvent()
}