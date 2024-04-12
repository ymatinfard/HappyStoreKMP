import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.NetworkProduct
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    val viewModel = getViewModel(Unit, viewModelFactory { HappyStoreViewModel() })

    val productState = viewModel.uiState.collectAsState().value

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (productState.products.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(productState.products) { product ->
                            ProductItem(product)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(item: NetworkProduct) {
    Row(
        modifier = Modifier.padding(6.dp).fillMaxWidth().height(120.dp)
    ) {
        KamelImageLoader(item.image, modifier = Modifier.size(120.dp))
        Column(
            modifier = Modifier.padding(start = 6.dp).fillMaxWidth(),
        ) {
            Text(
                item.title.take(30), fontSize = 18.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1F)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Price: ${item.price}")
                Button(onClick = {}) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun KamelImageLoader(imageUrl: String, modifier: Modifier = Modifier) {
    KamelImage(
        asyncPainterResource(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.aspectRatio(1.0f)
    )
}
