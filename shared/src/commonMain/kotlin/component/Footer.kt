package component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import utils.openWindow
import voctrust.shared.LAST_MODIFIED_DATE_TIME
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.last_modified
import voctrust.shared.generated.resources.sourcecode

private const val RepositoryHref = "https://github.com/gines3s3/voctrust"

@Composable
internal fun Footer(
    modifier: Modifier = Modifier,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.fillMaxWidth().padding(top = 48.dp, bottom = 16.dp),
) {
    Text(
        stringResource(Res.string.last_modified, LAST_MODIFIED_DATE_TIME),
        color = MaterialTheme.colorScheme.outline,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
    )

    Text(
        stringResource(Res.string.sourcecode),
        color = MaterialTheme.colorScheme.outline,
        style = MaterialTheme.typography.bodyMedium,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { openWindow(RepositoryHref) },
        ).pointerHoverIcon(PointerIcon.Hand),
        textAlign = TextAlign.Center,
    )
}
