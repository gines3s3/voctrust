package component

import VOCTag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import component.work.Work

private const val MaxGridCount = 2

@Composable
internal fun CardGrid(
    minWidth: Dp,
    works: List<Work>,
    content: @Composable (Work) -> Unit,
) = BoxWithConstraints {
    val chunkedSize = (((maxWidth / (minWidth + 8.dp)).toInt()))
        .takeIf { it in 1..MaxGridCount } ?: MaxGridCount
    val chunkedWorks = works.toList().chunked(chunkedSize)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (i in 0..<chunkedSize) {
            Column(
                modifier = Modifier.weight(1F)
                    .testTag(VOCTag.CARD_GRID_COLUMN),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                chunkedWorks.forEach work@{ row ->
                    content(row.getOrNull(i) ?: return@work)
                }
            }
        }
    }
}
