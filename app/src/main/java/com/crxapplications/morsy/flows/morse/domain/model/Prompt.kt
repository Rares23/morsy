package com.crxapplications.morsy.flows.morse.domain.model

import java.util.Date

data class Prompt(
    val id: Long,
    val text: String,
    val date: Date
)