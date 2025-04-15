package component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import config.MaxWidth
import config.MediumWidth
import org.jetbrains.compose.resources.StringResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.tab_about_us
import voctrust.shared.generated.resources.tab_contact_us
import voctrust.shared.generated.resources.tab_gallery
import voctrust.shared.generated.resources.tab_home
import voctrust.shared.generated.resources.tab_services

enum class ContentTab(
    val label: StringResource,
    val icon: ImageVector,
    val path: String,
) {

    HOME(Res.string.tab_home, Icons.Default.Home, ""),
    SERVICES(Res.string.tab_services, Icons.Default.DesignServices, "services"),
    GALLERY(Res.string.tab_gallery, Icons.Default.PhotoAlbum, "gallery"),
    ABOUT_US(Res.string.tab_about_us, Icons.Default.AccountBox, "about-us"),
    CONTACT_US(Res.string.tab_contact_us, Icons.Default.ContactPhone, "contact-us"),
}

@Composable
internal fun TabLayout(
    current: ContentTab,
    tabState: MutableState<ContentTab> = remember { mutableStateOf(current) },
    tabs: @Composable (MutableState<ContentTab>) -> Unit,
    content: @Composable (ContentTab) -> Unit,
) = Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val density = LocalDensity.current
        val mediumWidthPx = with(density) { MediumWidth.toPx() }
        val currentWidthPx = with(density) { maxWidth.toPx() }

        if (currentWidthPx >= mediumWidthPx) {
            // Large screen - use TabRow for evenly distributed tabs
            TabRow(
                selectedTabIndex = tabState.value.ordinal,
                modifier = Modifier.fillMaxWidth().widthIn(max = MaxWidth)
            ) {
                tabs(tabState)
            }
        } else {
            // Small screen - use ScrollableTabRow for scrolling
            ScrollableTabRow(
                selectedTabIndex = tabState.value.ordinal,
                edgePadding = 8.dp,
                tabs = { tabs(tabState) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
    ) { content(tabState.value) }
}

internal expect fun MutableState<ContentTab>.handleTabChange(
    next: ContentTab,
)
