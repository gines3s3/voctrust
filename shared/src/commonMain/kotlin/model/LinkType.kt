package model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
                tint = Color.Unspecified
            )
        }
    }

    data object Phone : LinkType() {
        override fun getIconContent(): @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Phone"
            )
        }
    }

    data object Email : LinkType() {
        override fun getIconContent(): @Composable () -> Unit = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email"
            )
        }
    }
}