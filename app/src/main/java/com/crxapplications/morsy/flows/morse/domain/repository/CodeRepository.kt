package com.crxapplications.morsy.flows.morse.domain.repository

import com.crxapplications.morsy.flows.morse.domain.model.Symbol

interface CodeRepository {
    suspend fun getCodeMap(): Map<String, List<Symbol>>
}