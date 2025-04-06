import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import component.AppScaffold
import component.ContentTab
import component.ImageCarousel
import component.TabLayout
import component.TopAppBar
import component.handleTabChange
import config.LocalAppConfigState
import config.rememberAppConfigState
import org.jetbrains.compose.resources.stringResource
import section.AboutUsSection
import section.GallerySection
import section.GetInvolvedSection
import section.HomeSection
import theme.rememberTypography

@Composable
fun VOCApp(
    current: ContentTab,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val config = rememberAppConfigState(useDarkTheme)
    val typography = rememberTypography()

    CompositionLocalProvider(
        LocalAppConfigState provides config,
    ) {
        MaterialTheme(
            colorScheme = config.theme.schema,
            typography = typography,
        ) {
            AppScaffold(
                header = { width ->
                    TopAppBar(
                        modifier = Modifier.width(width),
                    )
                },
            ) {
                item { ImageCarousel() }
                item { VOCContent(current) }
            }
        }
    }
}

@Composable
private fun VOCContent(current: ContentTab) {
    TabLayout(
        current,
        tabs = { currentTab ->
            ContentTab.entries.forEach {
                Tab(
                    currentTab.value == it,
                    text = { Text(stringResource(it.label)) },
                    icon = { Icon(it.icon, contentDescription = null) },
                    onClick = { currentTab.handleTabChange(it) },
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                )
            }
        },
        content = { tab ->
            HomeSection(tab == ContentTab.HOME)
            AboutUsSection(tab == ContentTab.ABOUT_US)
            GetInvolvedSection(tab == ContentTab.SERVICES)
            GallerySection(tab == ContentTab.GALLERY)
        },
    )
}