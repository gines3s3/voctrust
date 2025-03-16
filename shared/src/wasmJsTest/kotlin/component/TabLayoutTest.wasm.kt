package component

import androidx.compose.runtime.mutableStateOf
import kotlinx.browser.window
import kotlin.test.Test
import kotlin.test.assertEquals

class TabLayoutTestWasm {
    @Test
    fun HandleTabChange_shouldChangeLocationPathname() {
        listOf(
            ContentTab.HOME to "",
            ContentTab.ABOUT_US to "my_fave",
            ContentTab.GET_INVOLVED to "works",
        ).forEach { (tab, path) ->
            val state = mutableStateOf(ContentTab.HOME)
            state.handleTabChange(tab)

            assertEquals(tab, state.value)
            assertEquals("/$path", window.location.pathname)
        }
    }
}
