package component.accountchip

import VOCTag
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.SmallWidth
import config.isLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import utils.openWindow

private val smallIconSize = 18.dp
private val largeIconSize = 30.dp

@Composable
internal fun AccountLink(
    iconRes: DrawableResource,
    label: String,
    href: String,
) = BoxWithConstraints {
    val isLargeScreen = maxWidth > SmallWidth
    if (isLargeScreen) {
        FilterChip(
            true,
            onClick = { openWindow(href) },
            leadingIcon = {
                ServiceIcon(iconRes, smallIconSize)
            },
            label = {
                Text(label, style = MaterialTheme.typography.bodyMedium)
            },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                .testTag(VOCTag.ACCOUNT_LINK),
        )
    } else {
        IconButton(
            onClick = { openWindow(href) },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                .testTag(VOCTag.ACCOUNT_LINK),
        ) {
            ServiceIcon(iconRes, largeIconSize)
        }
    }
}

@Composable
internal fun ServiceIcon(
    iconRes: DrawableResource,
    iconSize: Dp,
) = Image(
    imageResource(iconRes),
    "Service Icon",
    modifier = Modifier.size(iconSize),
)

/*@Composable
internal fun ServiceIcon(
    painter: Painter,
    lightColor: Color? = null,
    darkColor: Color? = null,
) = Image(
    painter,
    "Service Icon",
    modifier = Modifier.size(smallIconSize),
    colorFilter = tintColor(lightColor, darkColor),
)*/

@Composable
private fun tintColor(
    lightColor: Color? = null,
    darkColor: Color? = null,
): ColorFilter? {
    lightColor ?: return null
    darkColor ?: return null

    return ColorFilter.tint(
        if (MaterialTheme.colorScheme.isLight()) lightColor else darkColor,
    )
}
