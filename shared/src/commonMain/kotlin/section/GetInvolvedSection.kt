package section

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.AnimatedSection
import component.CardGrid
import component.Footer
import component.WorkCard
import component.WorkCardMinWidth
import component.work.Works

@Composable
internal fun GetInvolvedSection(
    visible: Boolean,
) = AnimatedSection(
    visible,
    modifier = Modifier.padding(32.dp),
) {
    CardGrid(
        WorkCardMinWidth,
        Works,
    ) { work ->
        WorkCard(work)
    }

    Footer()
}
