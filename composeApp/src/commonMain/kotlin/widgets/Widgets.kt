package widgets

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Category(label: String, isSelected: Boolean = false, onSelection: (String) -> Unit) {
    FilterChip(
        selected = isSelected,
        onClick = { onSelection(label) },
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                )
            } else null
        },
        content = { Text(label) }
    )
}