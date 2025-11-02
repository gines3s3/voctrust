import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import component.AppScaffold
import component.ContentTab
import component.Footer
import component.ImageCarousel
import component.TopAppBar
import config.MediumWidth
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import section.AboutUsSection
import section.ContactUsSection
import section.GallerySection
import section.HomeSection
import section.ServicesSection

@Composable
fun VOCApp() {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val sectionIndexes = listOf(1, 2, 3, 4, 5)

    val selectedTabIndex by remember {
        derivedStateOf {
            if (!scrollState.canScrollForward) {
                sectionIndexes.lastIndex
            } else {
                sectionIndexes.indexOf(scrollState.firstVisibleItemIndex).coerceAtLeast(0)
            }
        }
    }

    AppScaffold(
        scrollState = scrollState,
        header = { width ->
            Column(modifier = Modifier.width(width)) {
                TopAppBar()
                BoxWithConstraints {
                    val tabs = @Composable {
                        ContentTab.entries.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                text = { Text(stringResource(tab.label), maxLines = 1) },
                                onClick = {
                                    coroutineScope.launch {
                                        scrollState.animateScrollToItem(sectionIndexes[index])
                                    }
                                },
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    if (maxWidth < MediumWidth) {
                        PrimaryScrollableTabRow(
                            selectedTabIndex = selectedTabIndex,
                        ) {
                            tabs()
                        }
                    } else {
                        PrimaryTabRow(
                            selectedTabIndex = selectedTabIndex,
                        ) {
                            tabs()
                        }
                    }
                }
            }
        },
    ) {
        item { ImageCarousel() } // Index 0

        item { HomeSection() } // Index 1
        item { ServicesSection() } // Index 2
        item { GallerySection() } // Index 3
        item { AboutUsSection() } // Index 4
        item { ContactUsSection() } // Index 5

        item { Footer() }
    }
}
