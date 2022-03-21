package com.html.compose.text

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.style.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.core.text.getSpans
/**
 * Created by Rehan Sarwar on 18/03/2022.
 */
private const val URL_TAG = "url_tag"

@Composable
fun HtmlText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    linkClicked: ((String) -> Unit)? = null,
    fontSize: TextUnit = 14.sp,
    flags: Int = HtmlCompat.FROM_HTML_MODE_COMPACT,
    URLSpanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colors.secondary,
        textDecoration = TextDecoration.Underline
    )
) {
    val content = text.asHTML(fontSize, flags, URLSpanStyle)
    if (linkClicked != null) {
        ClickableText(
            modifier = modifier,
            text = content,
            style = style,
            softWrap = softWrap,
            overflow = overflow,
            maxLines = maxLines,
            onTextLayout = onTextLayout,
            onClick = {
                content
                    .getStringAnnotations(URL_TAG, it, it)
                    .firstOrNull()
                    ?.let { stringAnnotation -> linkClicked(stringAnnotation.item) }
            }
        )
    } else {
        val annotatedString = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Html.fromHtml(text)
        } else {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        }.toAnnotatedString(URLSpanStyle)

        val uriHandler = LocalUriHandler.current
        val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

        Text(
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures(onTap = { pos ->
                    layoutResult.value?.let { layoutResult ->
                        val position = layoutResult.getOffsetForPosition(pos)
                        annotatedString.getStringAnnotations(position, position)
                            .firstOrNull()
                            ?.let { sa ->
                                if (sa.tag == "url") { // NON-NLS
                                    uriHandler.openUri(sa.item)
                                }
                            }
                    }
                })
            },
            text = content,
            style = style,
            softWrap = softWrap,
            overflow = overflow,
            maxLines = maxLines,
              onTextLayout = {
                layoutResult.value = it
                onTextLayout(it)
            },
        )
    }

}


@Composable
private fun String.asHTML(
    fontSize: TextUnit,
    flags: Int,
    URLSpanStyle: SpanStyle
) = buildAnnotatedString {
    val spanned = HtmlCompat.fromHtml(this@asHTML, flags)
    val spans = spanned.getSpans(0, spanned.length, Any::class.java)

    append(spanned.toString())

    spans
        .filter { it !is BulletSpan }
        .forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)
            when (span) {
                is RelativeSizeSpan -> span.spanStyle(fontSize)
                is StyleSpan -> span.spanStyle()
                is UnderlineSpan -> span.spanStyle()
                is ForegroundColorSpan -> span.spanStyle()
                is TypefaceSpan -> span.spanStyle()
                is StrikethroughSpan -> span.spanStyle()
                is SuperscriptSpan -> span.spanStyle()
                is SubscriptSpan -> span.spanStyle()
                is URLSpan -> {
                    addStringAnnotation(
                        tag = URL_TAG,
                        annotation = span.url,
                        start = start,
                        end = end
                    )
                    URLSpanStyle
                }


                else -> {
                    null
                }
            }?.let { spanStyle ->
                addStyle(spanStyle, start, end)
            }
        }
}

fun CharSequence.toAnnotatedString(
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    )
): AnnotatedString {
    return if (this is Spanned) {
        this.toAnnotatedString(urlSpanStyle)
    } else {
        buildAnnotatedString {
            append(this@toAnnotatedString.toString())
        }
    }
}

fun Spanned.toAnnotatedString(
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    )
): AnnotatedString {
    return buildAnnotatedString {
        append(this@toAnnotatedString.toString())
        val urlSpans = getSpans<URLSpan>()
        val styleSpans = getSpans<StyleSpan>()
        val underlineSpans = getSpans<UnderlineSpan>()
        val strikethroughSpans = getSpans<StrikethroughSpan>()
        urlSpans.forEach { urlSpan ->
            val start = getSpanStart(urlSpan)
            val end = getSpanEnd(urlSpan)
            addStyle(urlSpanStyle, start, end)
            addStringAnnotation("url", urlSpan.url, start, end) // NON-NLS
        }
        styleSpans.forEach { styleSpan ->
            val start = getSpanStart(styleSpan)
            val end = getSpanEnd(styleSpan)
            when (styleSpan.style) {
                android.graphics.Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                android.graphics.Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                android.graphics.Typeface.BOLD_ITALIC -> addStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    ), start, end
                )
            }
        }
        underlineSpans.forEach { underlineSpan ->
            val start = getSpanStart(underlineSpan)
            val end = getSpanEnd(underlineSpan)
            addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
        }
        strikethroughSpans.forEach { strikethroughSpan ->
            val start = getSpanStart(strikethroughSpan)
            val end = getSpanEnd(strikethroughSpan)
            addStyle(SpanStyle(textDecoration = TextDecoration.LineThrough), start, end)
        }
    }
}


