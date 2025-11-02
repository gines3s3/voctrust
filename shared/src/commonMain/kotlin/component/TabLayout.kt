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
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.runtime.Composable
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
    selectedTab: ContentTab,
    onTabSelected: (ContentTab) -> Unit,
    tabs: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val density = LocalDensity.current
            val mediumWidthPx = with(density) { MediumWidth.toPx() }
            val currentWidthPx = with(density) { maxWidth.toPx() }

            if (currentWidthPx >= mediumWidthPx) {
                PrimaryTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    modifier = Modifier.fillMaxWidth().widthIn(max = MaxWidth)
                ) { tabs() }
            } else {
                PrimaryScrollableTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    edgePadding = 8.dp,
                    tabs = { tabs() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Box(
            contentAlignment = Alignment.TopCenter,
        ) { content() }
    }
}
