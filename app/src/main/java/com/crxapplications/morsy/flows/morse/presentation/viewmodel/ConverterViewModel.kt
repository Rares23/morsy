package com.crxapplications.morsy.flows.morse.presentation.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crxapplications.morsy.R
import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.UiText
import com.crxapplications.morsy.core.service.CameraService
import com.crxapplications.morsy.core.service.SharedPreferencesService
import com.crxapplications.morsy.core.service.SoundPlayerService
import com.crxapplications.morsy.flows.morse.domain.model.Symbol
import com.crxapplications.morsy.flows.morse.domain.usecase.ConvertToMorseCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
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
    private val cameraService: CameraService,
    private val sharedPreferencesService: SharedPreferencesService,
) : ViewModel() {
    companion object {
        const val DOT_FREQUENCY = 140L
        const val DASH_FREQUENCY = 300L
        const val SPACE_DELAY = 620L
        const val WORD_DELAY = 200L
    }

    private var playJob: Job? = null

    private val _state = MutableStateFlow(
        ConverterState(
            text = "",
            code = emptyList(),
            isPlaying = false,
            soundEnabled = false,
            flashEnabled = false,
            isLoading = true,
            frequency = .5f
        )
    )
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
            ConverterEvent.ToggleFlashState -> toggleFlashState()
            ConverterEvent.ToggleSoundState -> toggleSoundState()
            is ConverterEvent.ChangeFrequencyEvent -> changeFrequency(event)
        }
    }

    private fun changeFrequency(event: ConverterEvent.ChangeFrequencyEvent) {
        viewModelScope.launch {
            sharedPreferencesService.setFrequency(event.value)

            _state.emit(
                _state.value.copy(
                    frequency = event.value
                )
            )
        }
    }

    private fun toggleFlashState() {
        viewModelScope.launch {
            val newState = !sharedPreferencesService.getFlashState()
            sharedPreferencesService.setFlashState(newState)

            // make sure to turn off the flash
            if (!newState) {
                cameraService.handleFlash(false)
            }

            _state.emit(
                _state.value.copy(
                    flashEnabled = newState
                )
            )
        }
    }

    private fun toggleSoundState() {
        viewModelScope.launch {
            val newState = !sharedPreferencesService.getSoundState()
            sharedPreferencesService.setSoundState(newState)
            _state.emit(
                _state.value.copy(
                    soundEnabled = newState
                )
            )
        }
    }

    private fun play() {
        playJob?.cancel()

        playJob = viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    isPlaying = true
                )
            )

            for (i in 0 until _state.value.code.size) {
                val letterCode = _state.value.code[i]
                for (j in 0 until letterCode.code.size) {
                    val speed = 1f - _state.value.frequency

                    val symbol = letterCode.code[j]
                    _playedIndex.emit(Pair(i, j))

                    if (!_state.value.isPlaying) {
                        _playedIndex.emit(Pair(-1, -1))
                        return@launch
                    }

                    when (symbol) {
                        Symbol.DOT -> {
                            if (_state.value.soundEnabled) {
                                soundPlayerService.playSound(R.raw.dot)
                            }
                            if (_state.value.flashEnabled) {
                                cameraService.handleFlash(true)
                            }

                            val minDotFrequency = DOT_FREQUENCY / 2
                            delay((minDotFrequency + DOT_FREQUENCY * speed).toLong())

                            if (_state.value.flashEnabled) {
                                cameraService.handleFlash(false)
                            }
                        }

                        Symbol.DASH -> {
                            if (_state.value.soundEnabled) {
                                soundPlayerService.playSound(R.raw.dash)
                            }
                            if (_state.value.flashEnabled) {
                                cameraService.handleFlash(true)
                            }
                            val minDashFrequency = DASH_FREQUENCY / 2
                            delay(minDashFrequency + (DASH_FREQUENCY * speed).toLong())
                            if (_state.value.flashEnabled) {
                                cameraService.handleFlash(false)
                            }
                        }

                        Symbol.SPACE -> {
                            val minSpaceFrequency = SPACE_DELAY / 2
                            delay(minSpaceFrequency + (SPACE_DELAY * speed).toLong())
                        }
                    }
                }

                val minWordFrequency = WORD_DELAY / 2
                val speed = 1f - _state.value.frequency
                delay(minWordFrequency + (WORD_DELAY * speed).toLong())
            }

            _state.emit(
                state.value.copy(
                    isPlaying = false
                )
            )
        }

        playJob?.invokeOnCompletion {
            stop()
        }
    }

    private fun stop() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(isPlaying = false))
            _playedIndex.emit(Pair(-1, -1))
        }
    }

    private fun convertText(event: ConverterEvent.ConvertTextEvent) {
        viewModelScope.launch {
            when (val response = convertToMorseCodeUseCase(event.text)) {
                is Response.SuccessResponse -> {
                    _state.emit(
                        ConverterState(
                            text = event.text,
                            code = response.value,
                            isPlaying = false,
                            soundEnabled = sharedPreferencesService.getSoundState(),
                            flashEnabled = sharedPreferencesService.getFlashState(),
                            isLoading = false,
                            frequency = sharedPreferencesService.getFrequency()
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