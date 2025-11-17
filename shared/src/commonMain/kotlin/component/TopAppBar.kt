package component

import VOCTag
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import config.AppConfigState
import config.ColorTheme
import config.LocalAppConfigState
import config.isLight
import model.LinkType
import model.Members
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.logo_voc
import voctrust.shared.generated.resources.title
import androidx.compose.material3.TopAppBar as M3TopAppBar

@Composable
internal fun TopAppBar(
    modifier: Modifier = Modifier,
) = TopAppBar(
    title = {
        Text(
            stringResource(Res.string.title),
            modifier = Modifier.testTag(VOCTag.TOP_APP_BAR_TITLE),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium
        )
    },
    modifier = modifier,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val config = LocalAppConfigState.current

    M3TopAppBar(
        title = title,
        navigationIcon = {
            IconButton(
                onClick = { /* Add action if needed */ },
                modifier = Modifier
            ) {
                Icon(
                    painterResource(Res.drawable.logo_voc),
                    contentDescription = "VOC Logo",
                    tint = null
                )
            }
        },
        actions = {

            Members.memberDeva.links.firstOrNull { it.linkType == LinkType.Phone }
                ?.let { memberLink ->
                    AccountLink(memberLink)
                }

            Spacer(Modifier.width(8.dp))

            Members.memberDeva.links.firstOrNull { it.linkType == LinkType.Instagram }
                ?.let { memberLink ->
                    AccountLink(memberLink)
                }

            IconButton(
                onClick = { config.switchTheme() },
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    .testTag(VOCTag.TOP_APP_BAR_COLOR_THEME_BUTTON),
            ) {
                Icon(
                    config.themeIcon(),
                    contentDescription = "Change Theme",
                )
            }
        },
        modifier = modifier,
    )
}

@Suppress("CyclomaticComplexMethod")
private fun AppConfigState.switchTheme() {
    theme = when (theme) {
        ColorTheme.AndroidLight -> ColorTheme.AndroidDark
        ColorTheme.AndroidDark -> ColorTheme.AndroidLight
    }
}

@Composable
private fun AppConfigState.themeIcon() =
    if (theme.schema.isLight()) Icons.Default.DarkMode else Icons.Default.LightMode
