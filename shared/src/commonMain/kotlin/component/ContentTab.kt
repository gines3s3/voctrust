package component

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Image
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.PhoneCall
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Wrench
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
    HOME(Res.string.tab_home, Lucide.House, ""),
    SERVICES(Res.string.tab_services, Lucide.Wrench, "services"),
    GALLERY(Res.string.tab_gallery, Lucide.Image, "gallery"),
    ABOUT_US(Res.string.tab_about_us, Lucide.User, "about-us"),
    CONTACT_US(Res.string.tab_contact_us, Lucide.PhoneCall, "contact-us"),
}
