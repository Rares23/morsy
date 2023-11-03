package com.crxapplications.morsy.flows.morse.data.dto

import com.squareup.moshi.Json

data class CodesMapDto(
    @field:Json(name = "map") val map: List<LetterCodeDto>,
)