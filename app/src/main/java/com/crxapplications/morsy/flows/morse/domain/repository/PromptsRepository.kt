package com.crxapplications.morsy.flows.morse.domain.repository

import com.crxapplications.morsy.flows.morse.domain.model.Prompt


interface PromptsRepository {
    suspend fun getAll(): List<Prompt>
    suspend fun insert(text: String): Prompt
}