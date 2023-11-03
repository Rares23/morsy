package com.crxapplications.morsy.flows.morse.presentation.viewmodel

sealed class ConverterEvent {
    data class ConvertTextEvent(val text: String): ConverterEvent()
    object PlayEvent: ConverterEvent()
    object StopEvent: ConverterEvent()
}