package com.crxapplications.morsy.flows.morse.domain.model

enum class Symbol {
    DOT,
    DASH,
    SPACE;

    companion object {
        fun fromString(symbolString: String): Symbol {
            return when (symbolString) {
                "dot" -> DOT
                "dash" -> DASH
                " " -> SPACE
                else -> throw Exception("Unknown Morse Code Symbol!")
            }
        }
    }
}