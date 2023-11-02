package com.crxapplications.morsy.flows.morse.domain.usecase

import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.toUiText
import com.crxapplications.morsy.flows.morse.domain.model.Prompt
import com.crxapplications.morsy.flows.morse.domain.repository.PromptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject


interface SavePromptUseCase {
    suspend operator fun invoke(text: String): Response<Prompt>
}

class SavePromptUseCaseImpl @Inject constructor(
    private val promptsRepository: PromptsRepository,
) : SavePromptUseCase {
    override suspend fun invoke(text: String): Response<Prompt> = withContext(Dispatchers.IO) {
        return@withContext try {
            Response.SuccessResponse(promptsRepository.insert(text = text))
        } catch (e: Exception) {
            Response.ErrorResponse(e.toUiText())
        }
    }
}