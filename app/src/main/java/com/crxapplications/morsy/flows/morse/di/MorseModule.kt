package com.crxapplications.morsy.flows.morse.di

import com.crxapplications.morsy.flows.morse.data.repository.CodeRepositoryImpl
import com.crxapplications.morsy.flows.morse.data.repository.PromptsRepositoryImpl
import com.crxapplications.morsy.flows.morse.domain.repository.CodeRepository
import com.crxapplications.morsy.flows.morse.domain.repository.PromptsRepository
import com.crxapplications.morsy.flows.morse.domain.usecase.ConvertToMorseCodeUseCase
import com.crxapplications.morsy.flows.morse.domain.usecase.ConvertToMorseCodeUseCaseImpl
import com.crxapplications.morsy.flows.morse.domain.usecase.DeletePromptUseCase
import com.crxapplications.morsy.flows.morse.domain.usecase.DeletePromptUseCaseImpl
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

    @Binds
    abstract fun bindConvertToMorseCodeUseCase(convertToMorseCodeUseCaseImpl: ConvertToMorseCodeUseCaseImpl): ConvertToMorseCodeUseCase

    @Binds
    abstract fun bindCodeRepository(
        codeRepositoryImpl: CodeRepositoryImpl,
    ): CodeRepository

    @Binds
    abstract fun bindDeleteUseCase(
        deletePromptUseCaseImpl: DeletePromptUseCaseImpl,
    ): DeletePromptUseCase
}