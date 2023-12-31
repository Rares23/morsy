package com.crxapplications.morsy.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.graphics.Camera
import androidx.room.Room
import com.crxapplications.morsy.core.data.database.MorsyDatabase
import com.crxapplications.morsy.core.service.AssetsFileService
import com.crxapplications.morsy.core.service.AssetsFileServiceImpl
import com.crxapplications.morsy.core.service.CameraService
import com.crxapplications.morsy.core.service.CameraServiceImpl
import com.crxapplications.morsy.core.service.SharedPreferencesService
import com.crxapplications.morsy.core.service.SharedPreferencesServiceImpl
import com.crxapplications.morsy.core.service.SoundPlayerService
import com.crxapplications.morsy.core.service.SoundPlayerServiceImpl
import com.crxapplications.morsy.flows.morse.data.dao.PromptsDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "com.crxapplications.morsy", Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideAssetManager(application: Application): AssetManager = application.assets

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MorsyDatabase = Room.databaseBuilder(
        context, MorsyDatabase::class.java, "morsy"
    ).build()

    @Singleton
    @Provides
    fun providePromptsDao(
        database: MorsyDatabase,
    ): PromptsDao = database.promptDao()

    @Singleton
    @Provides
    fun provideAssetsFileService(@ApplicationContext context: Context): AssetsFileService =
        AssetsFileServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideSoundPlayerService(@ApplicationContext context: Context): SoundPlayerService =
        SoundPlayerServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideSharedPreferencesService(sharedPreferences: SharedPreferences): SharedPreferencesService =
        SharedPreferencesServiceImpl(sharedPreferences = sharedPreferences)

    @Singleton
    @Provides
    fun provideCameraService(@ApplicationContext context: Context): CameraService =
        CameraServiceImpl(
            context = context
        )

}