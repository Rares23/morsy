package com.crxapplications.morsy.core.service

import android.content.SharedPreferences
import javax.inject.Inject

interface SharedPreferencesService {
    fun setSavePromptsState(state: Boolean)
    fun getSavePromptsState(): Boolean

    fun setSoundState(state: Boolean)
    fun setFlashState(state: Boolean)

    fun getSoundState(): Boolean
    fun getFlashState(): Boolean

    fun setFrequency(value: Float)
    fun getFrequency(): Float
}

class SharedPreferencesServiceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferencesService {
    companion object {
        const val SAVE_PROMPTS = "SAVE_PROMPTS"
        const val SOUND_ENABLED = "SOUND_ENABLED"
        const val FLASH_ENABLED = "FLASH_ENABLED"
        const val FREQUENCY = "FREQUENCY"
    }

    override fun setSavePromptsState(state: Boolean) {
        sharedPreferences.edit().putBoolean(SAVE_PROMPTS, state).apply()
    }

    override fun getSavePromptsState(): Boolean = sharedPreferences.getBoolean(SAVE_PROMPTS, true)
    override fun setSoundState(state: Boolean) {
        sharedPreferences.edit().putBoolean(SOUND_ENABLED, state).apply()
    }

    override fun setFlashState(state: Boolean) {
        sharedPreferences.edit().putBoolean(FLASH_ENABLED, state).apply()
    }

    override fun getSoundState(): Boolean = sharedPreferences.getBoolean(SOUND_ENABLED, true)


    override fun getFlashState(): Boolean = sharedPreferences.getBoolean(FLASH_ENABLED, true)
    override fun setFrequency(value: Float) {
        sharedPreferences.edit().putFloat(FREQUENCY, value).apply()
    }

    override fun getFrequency(): Float = sharedPreferences.getFloat(FREQUENCY, 0.5f)


}