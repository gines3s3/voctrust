package component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import config.MaxWidth
import config.MinWidth

@Composable
internal fun AppScaffold(
    header: @Composable (Dp) -> Unit = {},
    scrollState: LazyListState,
    content: LazyListScope.() -> Unit
) = Surface(Modifier.fillMaxSize()) {
    BoxWithConstraints {
        val width = when {
            maxWidth in MinWidth..<MaxWidth -> maxWidth
            maxWidth < MinWidth -> MinWidth
            else -> MaxWidth
        }

        LazyColumnScaffold(
            width,
            header = header,
            content = content,
            scrollState = scrollState
        )
    }
}

@Composable
private fun LazyColumnScaffold(
    width: Dp,
    header: @Composable (Dp) -> Unit = {},
    content: LazyListScope.() -> Unit,
    scrollState: LazyListState,
) = Scaffold(
    topBar = {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) { header(width) }
    },
    modifier = Modifier.fillMaxSize(),
) { paddingValues ->
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
                .width(width)
                .padding(paddingValues),
            content = content,
            state = scrollState
        )
    }
}
