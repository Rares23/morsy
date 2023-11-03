package com.crxapplications.morsy.flows.morse.data.repository

import com.crxapplications.morsy.core.service.AssetsFileService
import com.crxapplications.morsy.flows.morse.data.dto.CodesMapDto
import com.crxapplications.morsy.flows.morse.domain.model.Symbol
import com.crxapplications.morsy.flows.morse.domain.repository.CodeRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val assetsFileService: AssetsFileService,
    private val moshi: Moshi,
) : CodeRepository {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun getCodeMap(): Map<String, List<Symbol>> {
        val json = assetsFileService.getJsonDataFromAsset("morse.json")
            ?: throw Exception("Codes Map Json is empty!")

        val adapter: JsonAdapter<CodesMapDto> = moshi.adapter()

        val codeMap = adapter.fromJson(json) ?: throw Exception("Failed to parse the Codes Map")

        val result = HashMap<String, List<Symbol>>()

        codeMap.map.forEach { item ->
            result[item.letter] = item.code.map { Symbol.fromString(it) }.toList()
        }

        return result
    }
}