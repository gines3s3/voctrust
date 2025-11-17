package section

import androidx.compose.runtime.Composable
import component.TextSection
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.home_welcome_text

@Composable
internal fun HomeSection() = TextSection(
    header = "Welcome to VOC Youth Social Welfare Trust!",
    stringResource = Res.string.home_welcome_text
)
