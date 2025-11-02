package component

import androidx.compose.runtime.MutableState

internal actual fun MutableState<ContentTab>.handleTabChange(
    next: ContentTab,
) {
    value = next
}
