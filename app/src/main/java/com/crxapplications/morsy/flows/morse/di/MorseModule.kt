package com.crxapplications.morsy.flows.morse.di

import com.crxapplications.morsy.flows.morse.data.repository.PromptsRepositoryImpl
import com.crxapplications.morsy.flows.morse.domain.repository.PromptsRepository
import com.crxapplications.morsy.flows.morse.domain.usecase.GetPromptsHistoryUseCase
import com.crxapplications.morsy.flows.morse.domain.usecase.GetPromptsHistoryUseCaseImpl
import com.crxapplications.morsy.flows.morse.domain.usecase.SavePromptUseCase
import com.crxapplications.morsy.flows.morse.domain.usecase.SavePromptUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MorseModule {
    @Binds
    abstract fun bindSavePromptUseCase(
        savePromptUseCaseImpl: SavePromptUseCaseImpl,
    ): SavePromptUseCase

    @Binds
    abstract fun bindGetPromptsHistoryUseCase(
        getPromptsHistoryUseCaseImpl: GetPromptsHistoryUseCaseImpl,
    ): GetPromptsHistoryUseCase

    @Binds
    abstract fun bindPromptsRepository(
        promptsRepositoryImpl: PromptsRepositoryImpl,
    ): PromptsRepository
}