import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.NetworkProduct

class HappyStoreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState(emptyList()))
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(products = fetchProduct())
            }
        }
    }

    private suspend fun fetchProduct(): List<NetworkProduct> {
        return httpClient.get("https://fakestoreapi.com/products")
            .body<List<NetworkProduct>>()
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }
}

data class ProductUiState(val products: List<NetworkProduct>)