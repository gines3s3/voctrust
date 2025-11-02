package component.icon

import androidx.compose.ui.test.*
import org.jetbrains.compose.resources.imageResource
import voctrust.shared.generated.resources.Res
import kotlin.test.Test

private const val CONTENT_DESCRIPTION = "test"

@OptIn(ExperimentalTestApi::class)
class MyFavoriteTest {
    @Test
    fun shouldDisplay() = runComposeUiTest {
        setContent {
            MyFavourite(
                imageResource(Res.drawable.kotlin),
                contentDescription = CONTENT_DESCRIPTION,
            )
        }

        onNodeWithContentDescription(CONTENT_DESCRIPTION)
            .assertIsDisplayed()
    }
}
