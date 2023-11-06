package com.crxapplications.morsy.flows.morse.domain.usecase

import com.crxapplications.morsy.core.helper.Response
import com.crxapplications.morsy.core.helper.toUiText
import com.crxapplications.morsy.flows.morse.domain.model.LetterCode
import com.crxapplications.morsy.flows.morse.domain.model.Symbol
import com.crxapplications.morsy.flows.morse.domain.repository.CodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ConvertToMorseCodeUseCase {
    suspend operator fun invoke(text: String): Response<List<LetterCode>>
}

class ConvertToMorseCodeUseCaseImpl @Inject constructor(private val codeRepository: CodeRepository) :
    ConvertToMorseCodeUseCase {
    override suspend operator fun invoke(text: String): Response<List<LetterCode>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val codeMap = codeRepository.getCodeMap()

                val result = ArrayList<LetterCode>()

                text.forEach { character ->
                    if (character == ' ') {
                        result.add(LetterCode(letter = " ", code = listOf(Symbol.SPACE)))
                    } else {
                        val code = codeMap[character.uppercase()]
                        if (!code.isNullOrEmpty()) {
                            result.add(LetterCode(letter = character.uppercase(), code = code))
                        }
                    }
                }

                Response.SuccessResponse(
                    value = result
                )
            } catch (e: Exception) {
                Response.ErrorResponse(message = e.toUiText())
            }
        }
}