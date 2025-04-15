package section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import component.AnimatedSection
import component.Footer
import model.Service
import model.Services
import org.jetbrains.compose.resources.painterResource
import voctrust.shared.generated.resources.Res
import voctrust.shared.generated.resources.logo_voc

@Composable
internal fun ServicesSection(
    visible: Boolean,
) = AnimatedSection(
    visible,
    modifier = Modifier.padding(32.dp),
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Our Services",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Using fixed height instead of intrinsic measurements
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 500.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 800.dp) // Use fixed height instead of intrinsic measurement
        ) {
            items(Services.allServices) { service ->
                ServiceCard(service = service)
            }
        }

        Footer()
    }
}

@Composable
fun ServiceCard(service: Service) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
                AsyncImage(
                    model = service.imageUrl,
                    contentDescription = service.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth(),
                    fallback = painterResource(Res.drawable.logo_voc)
                )

            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal =  16.dp)
            )
            Text(
                text = service.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
