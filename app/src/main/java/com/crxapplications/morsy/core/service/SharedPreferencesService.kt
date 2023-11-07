package com.crxapplications.morsy.core.service

import android.content.SharedPreferences
import javax.inject.Inject

interface SharedPreferencesService {
    fun setSavePromptsState(state: Boolean)
    fun getSavePromptsState(): Boolean
}

class SharedPreferencesServiceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferencesService {
    companion object {
        const val SAVE_PROMPTS = "SAVE_PROMPTS"
    }

    override fun setSavePromptsState(state: Boolean) {
        sharedPreferences.edit().putBoolean(SAVE_PROMPTS, state).apply()
    }

    override fun getSavePromptsState(): Boolean = sharedPreferences.getBoolean(SAVE_PROMPTS, true)

}