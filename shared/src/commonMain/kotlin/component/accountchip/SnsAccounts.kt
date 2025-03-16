package component.accountchip

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource
import voctrust.shared.generated.resources.*

@Composable
internal fun Instagram() = AccountLink(
    icon = {
        ServiceIcon(
            imageResource(Res.drawable.instagram),
        )
    },
    stringResource(Res.string.instagram),
    stringResource(Res.string.href_instagram),
)
