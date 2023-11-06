package com.crxapplications.morsy.core.service

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.core.content.ContextCompat.getSystemService
import com.crxapplications.morsy.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface SoundPlayerService {
    suspend fun playSound(soundRaw: Int)
}

class SoundPlayerServiceImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : SoundPlayerService {
    private var loaded = false
    private var soundPool: SoundPool? = null

    private var dotSoundId: Int = -1
    private var dashSoundId: Int = -1

    init {
        val audioAttrib = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        val builder = SoundPool.Builder()
        builder.setAudioAttributes(audioAttrib).setMaxStreams(5)
        soundPool = builder.build().apply {
            setOnLoadCompleteListener { _, _, _ ->
                loaded = true
            }

            dotSoundId = load(context, R.raw.dot, 1)
            dashSoundId = load(context, R.raw.dash, 1)
        }

    }

    override suspend fun playSound(soundRaw: Int) {
        withContext(Dispatchers.IO) {
            soundPool?.let {
                when (soundRaw) {
                    R.raw.dash -> {
                        it.play(dashSoundId, 1f, 1f, 1, 0, 1f)
                    }

                    R.raw.dot -> {
                        it.play(dotSoundId, 1f, 1f, 1, 0, 1f)
                    }

                    else -> {}
                }
            }

        }
    }
}