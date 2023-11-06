package com.crxapplications.morsy.flows.morse.domain.usecase

import com.crxapplications.morsy.R
import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.UiText
import com.crxapplications.morsy.core.helper.toUiText
import com.crxapplications.morsy.flows.morse.domain.model.Prompt
import com.crxapplications.morsy.flows.morse.domain.repository.PromptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetPromptsHistoryUseCase {
    suspend operator fun invoke(): Response<List<Prompt>>
}

class GetPromptsHistoryUseCaseImpl @Inject constructor(
    private val promptsRepository: PromptsRepository,
) : GetPromptsHistoryUseCase {
    override suspend fun invoke(): Response<List<Prompt>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Response.SuccessResponse(
                value = promptsRepository.getAll().sortedByDescending { it.date }
            )
        } catch (e: Exception) {
            Response.ErrorResponse(
                message = e.toUiText()
            )
        }
    }

}