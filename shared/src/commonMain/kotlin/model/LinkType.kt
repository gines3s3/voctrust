package model

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composables.icons.lucide.LucideIcons
import com.composables.icons.lucide.Mail
import com.composables.icons.lucide.Phone
import org.jetbrains.compose.resources.imageResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.instagram

sealed class LinkType {
    abstract fun getIconContent(): @Composable () -> Unit

    data object Instagram : LinkType() {
        override fun getIconContent(): @Composable () -> Unit = {
            Icon(
                bitmap = imageResource(Res.drawable.instagram),
                contentDescription = "Instagram",
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    data object Phone : LinkType() {
        override fun getIconContent(): @Composable () -> Unit = {
            Icon(
                imageVector = LucideIcons.Phone,
                contentDescription = "Phone",
                modifier = Modifier.fillMaxSize()

            )
        }
    }

    data object Email : LinkType() {
        override fun getIconContent(): @Composable () -> Unit = {
            Icon(
                imageVector = LucideIcons.Mail,
                contentDescription = "Email",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
