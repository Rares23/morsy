package com.crxapplications.morsy.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import androidx.room.Room
import com.crxapplications.morsy.core.data.database.MorsyDatabase
import com.crxapplications.morsy.flows.morse.data.dao.PromptsDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

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
}