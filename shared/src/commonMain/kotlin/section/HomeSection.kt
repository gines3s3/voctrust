package section

import androidx.compose.runtime.Composable
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.home_welcome_text

@Composable
internal fun HomeSection() = TextSection(
    header = "Welcome",
    stringResource = Res.string.home_welcome_text
)
