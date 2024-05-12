package component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import config.MaxWidth
import config.MinWidth

@Composable
internal fun AppScaffold(
    content: @Composable ColumnScope.() -> Unit,
) = Surface(Modifier.fillMaxSize()) {
    BoxWithConstraints {
        val width = when {
            MinWidth <= maxWidth && maxWidth < MaxWidth -> maxWidth
            maxWidth < MinWidth -> MinWidth
            else -> MaxWidth
        }

        Scaffold(
            topBar = { TopAppBar() },
            modifier = Modifier.width(width)
                .align(Alignment.Center),
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) { content() }
            }
        }
    }
}
