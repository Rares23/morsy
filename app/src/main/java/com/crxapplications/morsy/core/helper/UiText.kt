package com.crxapplications.morsy.core.helper

import android.content.Context

sealed class UiText {
    class DynamicText(val text: String) : UiText()
    class StringResourceText(val stringResource: Int) : UiText()
}

fun UiText.toString(context: Context): String {
    return when (this) {
        is UiText.DynamicText -> text
        is UiText.StringResourceText -> context.getString(stringResource)
    }
}