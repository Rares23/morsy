package com.crxapplications.morsy.core.helper

import com.crxapplications.morsy.R

fun Exception.toUiText(customErrorStringResource: Int? = null): UiText {
    val message = localizedMessage ?: message
    return if (message != null) {
        UiText.DynamicText(text = message)
    } else {
        UiText.StringResourceText(stringResource = customErrorStringResource ?: R.string.general_error)
    }
}