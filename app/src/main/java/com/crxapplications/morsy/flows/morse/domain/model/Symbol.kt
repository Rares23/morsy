package com.crxapplications.morsy.flows.morse.domain.model

enum class Symbol {
    DOT,
    DASH;

    companion object {
        fun fromString(symbolString: String): Symbol {
            return when (symbolString) {
                "dot" -> DOT
                "dash" -> DASH
                else -> throw Exception("Unknown Morse Code Symbol!")
            }
        }
    }
}