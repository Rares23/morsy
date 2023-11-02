package com.crxapplications.morsy.flows.morse.presentation.viewmodel

sealed class PromptFormEvent {
    data class OnTextChangeEvent(
        val text: String,
    ) : PromptFormEvent()

    data class SubmitNewPromptEvent(
        val text: String,
        val openConverter: (String) -> Unit,
    ) : PromptFormEvent()
}