package com.crxapplications.morsy.core.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface AssetsFileService {
    suspend fun getJsonDataFromAsset(fileName: String): String?
}

class AssetsFileServiceImpl @Inject constructor(@ApplicationContext val context: Context) :
    AssetsFileService {
    override suspend fun getJsonDataFromAsset(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}