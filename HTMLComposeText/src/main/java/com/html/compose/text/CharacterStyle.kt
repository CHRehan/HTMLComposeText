package com.html.compose.text

import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

/**
 * Created by Rehan Sarwar on 18/03/2022.
 */

internal fun UnderlineSpan.spanStyle(): SpanStyle =
    SpanStyle(textDecoration = TextDecoration.Underline)

internal fun ForegroundColorSpan.spanStyle(): SpanStyle =
    SpanStyle(color = Color(foregroundColor))

internal fun StrikethroughSpan.spanStyle(): SpanStyle =
    SpanStyle(textDecoration = TextDecoration.LineThrough)