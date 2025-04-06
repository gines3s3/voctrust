package component.accountchip

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.href_instagram
import voctrust.shared.generated.resources.instagram

@Composable
internal fun Instagram() = AccountLink(
    iconRes = Res.drawable.instagram,
    stringResource(Res.string.instagram),
    stringResource(Res.string.href_instagram),
)
