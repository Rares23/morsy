package com.crxapplications.morsy.core.service

import android.content.Context
import android.media.MediaPlayer
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


    override suspend fun playSound(soundRaw: Int) = withContext(Dispatchers.IO){
        // TODO: Implement media player without interruptions
        val player: MediaPlayer = MediaPlayer.create(context, soundRaw)

        try {
            player.start()
        } catch (e: Exception ){
            e.printStackTrace()
        }
    }

}