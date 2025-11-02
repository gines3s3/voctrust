package section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.Footer
import config.MaxWidth
import config.MediumWidth
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import utils.toAnnotatedBoldString
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.about_us_values_vision
import voctrust.shared.generated.resources.logo_voc

@Composable
internal fun AboutUsSection() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // This Box constrains the content width on large screens and handles the layout change
        BoxWithConstraints(
            modifier = Modifier.widthIn(max = MaxWidth),
            contentAlignment = Alignment.Center
        ) {
            val useHorizontalLayout = maxWidth > MediumWidth

            val logo = @Composable { sizeModifier: Modifier ->
                Image(
                    painter = painterResource(Res.drawable.logo_voc),
                    contentDescription = "VOC Trust Logo",
                    modifier = sizeModifier
                )
            }

            val text = @Composable { textModifier: Modifier ->
                SelectionContainer {
                    Text(
                        modifier = textModifier,
                        text = stringResource(Res.string.about_us_values_vision).toAnnotatedBoldString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (useHorizontalLayout) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    logo(Modifier.size(200.dp))
                    Spacer(modifier = Modifier.width(32.dp))
                    text(Modifier.weight(1f))
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    logo(Modifier.size(150.dp))
                    Spacer(modifier = Modifier.height(32.dp))
                    text(Modifier)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Footer is now correctly placed at the bottom of the section
        Footer()
    }
}
