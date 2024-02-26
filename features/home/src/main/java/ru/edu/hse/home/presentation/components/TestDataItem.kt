package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.themes.DefaultText

@Composable
fun TestDataItem(
    items: List<String>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(4.dp)) {
        for (i in items.indices) {
            DefaultText(text = "${i + 1}. ${items[i]}", modifier = Modifier
                .clickable(onClick = { onClick(i) })
                .padding(vertical = 8.dp, horizontal = 2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestDataItemPreview() {
    DefaultCardWithTitle(title = "Тест", modifier = Modifier.padding(20.dp)) {
        TestDataItem(items = listOf("First very very very very very very very very very very very very  long", "Second", "Third", "Fourth"), onClick = {})
    }
}