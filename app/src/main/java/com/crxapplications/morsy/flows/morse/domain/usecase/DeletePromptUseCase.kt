package com.crxapplications.morsy.flows.morse.domain.usecase

import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.toUiText
import com.crxapplications.morsy.flows.morse.domain.repository.PromptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeletePromptUseCase {
    suspend operator fun invoke(id: Long): Response<Int>
}

class DeletePromptUseCaseImpl @Inject constructor(
    private val promptsRepository: PromptsRepository,
) : DeletePromptUseCase {
    override suspend fun invoke(id: Long): Response<Int> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = promptsRepository.delete(id)
            Response.SuccessResponse(value = result)
        } catch (e: Exception) {
            Response.ErrorResponse(message = e.toUiText())
        }
    }
}