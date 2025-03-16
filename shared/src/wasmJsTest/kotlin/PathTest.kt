import component.ContentTab
import kotlinx.browser.window
import utils.buildContentPath
import kotlin.test.Test
import kotlin.test.assertEquals

class PathTest {
    @Test
    fun testBuildContentPath() {
        listOf(
            "" to ContentTab.HOME,
            "my_fave" to ContentTab.ABOUT_US,
            "works" to ContentTab.GET_INVOLVED,
            "unknown_path" to ContentTab.HOME,
        ).forEach { (path, tab) ->
            window.history.replaceState(null, "", "/$path")
            assertEquals(tab, buildContentPath())
        }
    }
}
