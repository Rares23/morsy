package com.crxapplications.morsy.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
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
class AppModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "com.crxapplications.morsy",
            Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideAssetManager(application: Application): AssetManager = application.assets
}