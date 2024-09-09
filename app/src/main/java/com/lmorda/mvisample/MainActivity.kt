package com.lmorda.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.lmorda.mvisample.details.DetailsScreen
import com.lmorda.mvisample.details.DetailsViewModel
import com.lmorda.mvisample.ui.theme.MVISampleTheme

const val OKHTTP_SQUARE_ID = 5152285L // Mocking navigation bundle param, this is OkHttp id

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVISampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.state.observeAsState().value
                    DetailsScreen(
                        id = OKHTTP_SQUARE_ID,
                        state = state,
                        push = viewModel::push
                    )
                }
            }
        }
    }
}