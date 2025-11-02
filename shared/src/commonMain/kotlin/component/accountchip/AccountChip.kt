package component.accountchip

import VOCTag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import config.isLight
import model.Member
import utils.isLargeScreen
import utils.openWindow

private val smallIconSize = 22.dp
private val largeIconSize = 34.dp

@Composable
internal fun AccountLink(
    link: Member.Link
) = BoxWithConstraints {

    if (maxWidth.isLargeScreen()) {
        FilterChip(
            selected = true,
            onClick = { openWindow(link.href) },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                selectedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            leadingIcon = {
                ServiceIcon(link.linkType.getIconContent(), smallIconSize)
            },
            label = {
                Text(link.label, style = MaterialTheme.typography.bodyMedium)
            },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                .testTag(VOCTag.ACCOUNT_LINK),
        )
    } else {
        IconButton(
            onClick = { openWindow(link.href) },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                .testTag(VOCTag.ACCOUNT_LINK),
        ) {
            ServiceIcon(link.linkType.getIconContent(), largeIconSize)
        }
    }
}

@Composable
internal fun ServiceIcon(
    icon: @Composable () -> Unit,
    iconSize: Dp,
) = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.size(iconSize),
) {
    icon()
}

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
