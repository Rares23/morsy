package com.crxapplications.morsy.flows.morse.domain.model

import java.util.Date

data class Prompt(
    val int: Int,
    val text: String,
    val date: Date
)