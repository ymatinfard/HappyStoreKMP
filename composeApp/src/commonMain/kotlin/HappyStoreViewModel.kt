import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.NetworkProduct

class HappyStoreViewModel : ViewModel() {

    val state = MutableStateFlow(ProductsUiState(emptyList()))

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        loadProduct()
    }

    val productsUiState = combine(state.map { it.products },
        state.map { it.selectedCategory }) { products, selectedCategory ->
        val filteredProducts =
            products.filter {
                selectedCategory == null ||
                        selectedCategory == "all" ||
                        it.category == selectedCategory
            }

        state.value.copy(products = filteredProducts)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProductsUiState()
    )

    private fun loadProduct() {
        viewModelScope.launch {
            val products = fetchProduct()
            val categories = mutableListOf("all")
            categories.addAll(products.map { it.category })
            state.value =
                state.value.copy(products = products, categories = categories.toSet())
        }
    }

    private suspend fun fetchProduct(): List<NetworkProduct> {
        return httpClient.get("https://fakestoreapi.com/products")
            .body<List<NetworkProduct>>()
    }

    fun updateSelectedCategory(newCategory: String) {
        state.update {
            it.copy(selectedCategory = newCategory)
        }
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }
}

data class ProductsUiState(
    val products: List<NetworkProduct> = emptyList(),
    val categories: Set<String> = emptySet(),
    val selectedCategory: String? = "jewelery",
)