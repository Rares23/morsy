package com.crxapplications.morsy.flows.morse.data.repository

import com.crxapplications.morsy.flows.morse.data.dao.PromptsDao
import com.crxapplications.morsy.flows.morse.data.entity.PromptEntity
import com.crxapplications.morsy.flows.morse.data.entity.toPrompt
import com.crxapplications.morsy.flows.morse.domain.model.Prompt
import com.crxapplications.morsy.flows.morse.domain.repository.PromptsRepository
import java.util.Date
import javax.inject.Inject

class PromptsRepositoryImpl @Inject constructor(
    private val promptsDao: PromptsDao,
) : PromptsRepository {
    override suspend fun getAll(): List<Prompt> =
        promptsDao.getAll().map {
            it.toPrompt()
        }.toList()

    override suspend fun insert(text: String): Prompt {
        return PromptEntity(
            text = text,
            createdAt = Date()
        ).let {
            val uid = promptsDao.insert(
                it
            )
            it.copy(uid = uid)
        }.toPrompt()
    }

    override suspend fun delete(id: Long): Int {
        return promptsDao.delete(id)
    }

}