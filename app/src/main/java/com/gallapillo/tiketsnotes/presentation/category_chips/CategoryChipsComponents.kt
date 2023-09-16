package com.gallapillo.tiketsnotes.presentation.category_chips

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun CategoryChipsPreview() {
    CategoryChips()
}

@Composable
fun CategoryChips() {
    // TODO: SAVE HERE MAYBE RELEASE IN 1.1.0!
    LazyRow(content = {
        items(10) {
            Card(modifier = Modifier
                .padding(12.dp)
                .height(32.dp)
            ) {
                Text(text = "Simple Category", modifier = Modifier.padding(4.dp))
            }
        }
    })
}