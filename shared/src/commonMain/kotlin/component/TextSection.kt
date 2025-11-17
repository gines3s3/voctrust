package component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import utils.toAnnotatedBoldString

@Composable
internal fun TextSection(
    stringResource: StringResource,
    header: String? = null
) = Column(
    modifier = Modifier.padding(32.dp),
) {
    header?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
    }
    SelectionContainer {
        Text(
            stringResource(stringResource).toAnnotatedBoldString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
