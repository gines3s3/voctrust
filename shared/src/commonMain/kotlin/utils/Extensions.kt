package utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import config.SmallWidth

fun Dp.isLargeScreen() = this > SmallWidth

fun String.toAnnotatedBoldString(): AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        val input = this@toAnnotatedBoldString
        while (true) {
            val start = input.indexOf("<b>", currentIndex, ignoreCase = true)
            val end = input.indexOf("</b>", currentIndex, ignoreCase = true)

            if (start == -1 || end == -1 || end < start) {
                append(input.substring(currentIndex))
                break
            }

            // Append normal text before <b>
            append(input.substring(currentIndex, start))

            // Append bold text
            val boldText = input.substring(start + 3, end)
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(boldText)
            }

            currentIndex = end + 4
        }
    }
}
