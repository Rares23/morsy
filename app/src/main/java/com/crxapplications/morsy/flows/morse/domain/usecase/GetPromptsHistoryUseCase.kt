package com.crxapplications.morsy.flows.morse.domain.usecase

import com.crxapplications.morsy.flows.morse.domain.model.Prompt

interface GetPromptsHistoryUseCase {
    suspend operator fun invoke(): List<Prompt>
}
class GetPromptsHistoryUseCaseImpl : GetPromptsHistoryUseCase {
    override suspend fun invoke(): List<Prompt> {
        TODO("Not yet implemented")
    }

}