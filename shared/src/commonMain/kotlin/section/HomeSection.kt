package section

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.AnimatedSection
import component.Footer
import org.jetbrains.compose.resources.stringResource
import utils.toAnnotatedBoldString
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.home_welcome_text

@Composable
internal fun HomeSection(
    visible: Boolean
) = AnimatedSection(
    visible,
    modifier = Modifier.fillMaxHeight()
        .padding(32.dp),
) {
    SelectionContainer {
        Text(
            stringResource(Res.string.home_welcome_text).toAnnotatedBoldString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }

    Spacer(Modifier.weight(1F))

    Footer()
}