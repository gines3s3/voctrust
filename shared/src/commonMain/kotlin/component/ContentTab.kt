package component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.ui.graphics.vector.ImageVector
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