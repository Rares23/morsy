package com.crxapplications.morsy.flows.morse.data.dto

import com.squareup.moshi.Json

data class LetterCodeDto(
    @field:Json(name = "letter") val letter: String,
    @field:Json(name = "code") val code: List<String>
)