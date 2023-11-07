package com.crxapplications.morsy.core.service

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


interface CameraService {
    suspend fun handleFlash(enable: Boolean)
}

class CameraServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : CameraService {
    override suspend fun handleFlash(enable: Boolean) {
        (context.getSystemService(Context.CAMERA_SERVICE) as CameraManager?)?.let { camManager ->
            try {
                val cameraId = camManager.cameraIdList[0]
                camManager.setTorchMode(cameraId, enable)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }
}