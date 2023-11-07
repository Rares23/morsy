package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crxapplications.morsy.R
import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.UiText
import com.crxapplications.morsy.core.service.SoundPlayerService
import com.crxapplications.morsy.flows.morse.domain.model.Symbol
import com.crxapplications.morsy.flows.morse.domain.usecase.ConvertToMorseCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertToMorseCodeUseCase: ConvertToMorseCodeUseCase,
    private val soundPlayerService: SoundPlayerService,
) : ViewModel() {
    private var isPlaying = false

    private val _state = MutableStateFlow<ConverterState>(ConverterState.LoadingState)
    val state: StateFlow<ConverterState> = _state.asStateFlow()

    private val _playedIndex = MutableStateFlow(Pair(-1, -1))
    val playedIndex = _playedIndex.asStateFlow()

    private val _toastMessage = MutableSharedFlow<UiText>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun addEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.ConvertTextEvent -> convertText(event)
            ConverterEvent.StopEvent -> stop()
            ConverterEvent.PlayEvent -> play()
        }
    }

    private fun play() {
        viewModelScope.launch {
            isPlaying = true
            (state.value as? ConverterState.DataLoadedState)?.let { state ->
                _state.emit(
                    state.copy(
                        isPlaying = isPlaying
                    )
                )

                for (i in 0 until state.code.size) {
                    val letterCode = state.code[i]
                    for (j in 0 until letterCode.code.size) {
                        val symbol = letterCode.code[j]
                        _playedIndex.emit(Pair(i, j))

                        if (!isPlaying) {
                            _playedIndex.emit(Pair(-1, -1))
                            return@launch
                        }

                        when (symbol) {
                            Symbol.DOT -> {
                                soundPlayerService.playSound(R.raw.dot)
                                delay(140)
                            }

                            Symbol.DASH -> {
                                soundPlayerService.playSound(R.raw.dash)
                                delay(200)
                            }

                            Symbol.SPACE -> delay(620)
                        }
                    }
                    delay(200)
                }

                isPlaying = false
                _state.emit(
                    state.copy(
                        isPlaying = isPlaying
                    )
                )
            }
        }
    }

    private fun stop() {
        viewModelScope.launch {
            isPlaying = false
            (state.value as? ConverterState.DataLoadedState)?.let {
                _state.emit(it.copy(isPlaying = isPlaying))
            }

            _playedIndex.emit(Pair(-1, -1))
        }
    }

    private fun convertText(event: ConverterEvent.ConvertTextEvent) {
        viewModelScope.launch {
            when (val response = convertToMorseCodeUseCase(event.text)) {
                is Response.SuccessResponse -> {
                    _state.emit(
                        ConverterState.DataLoadedState(
                            text = event.text,
                            code = response.value,
                            isPlaying = isPlaying,
                        )
                    )
                }

                is Response.ErrorResponse -> {
                    _toastMessage.emit(response.message)
                }
            }
        }
    }
}