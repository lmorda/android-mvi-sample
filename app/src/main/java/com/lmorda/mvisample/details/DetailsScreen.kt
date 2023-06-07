package com.lmorda.mvisample.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(
    id: Long,
    state: DetailsContract.State?,
    push: (DetailsContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            when (state) {
                DetailsContract.State.Initial -> Text("Welcome to Sample MVI App")
                DetailsContract.State.Loading -> CircularProgressIndicator()
                is DetailsContract.State.Loaded -> Text(state.detailsDto.toString())
                is DetailsContract.State.LoadError -> Text(state.errorMessage)
                else -> {}
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(top = 16.dp),
            onClick = { push(DetailsContract.Event.OnLoadClick(id = id)) }
        ) {
            Text(text = "Load Repository Details")
        }
    }
}