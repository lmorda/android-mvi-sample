package com.lmorda.mvisample.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lmorda.mvisample.R
import com.lmorda.mvisample.domain.RepoDetailsDto

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
                DetailsContract.State.Initial -> Text(stringResource(id = R.string.welcome_message))
                DetailsContract.State.Loading -> CircularProgressIndicator()
                is DetailsContract.State.Loaded -> RepoDetails(state.detailsDto)
                is DetailsContract.State.LoadError -> Text(stringResource(id = R.string.load_error))
                else -> {}
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(top = 16.dp),
            onClick = {
                push(DetailsContract.Event.OnLoadClick(id = id))
            }
        ) {
            Text(text = stringResource(id = R.string.load_button))
        }
    }
}


@Composable
private fun RepoDetails(details: RepoDetailsDto) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        RepoDetailsTitle(details)
        details.description?.let {
            RepoDetailsDescription(it)
        }
        details.forksCount?.let {
            RepoDetailsStargazers(it)
        }
        details.stargazersCount?.let {
            RepoDetailsForks(it)
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun RepoDetailsTitle(details: RepoDetailsDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        details.owner.avatarUrl?.let {
            GlideImage(
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(shape = CircleShape),
                model = details.owner.avatarUrl,
                contentDescription = details.owner.avatarUrl,
            )
        } ?: Image(
            modifier = Modifier.size(size = 40.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = details.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = details.owner.login.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun RepoDetailsDescription(description: String) {
    if (description.isNotBlank()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun RepoDetailsForks(count: Int) {
    Row(
        modifier = Modifier.padding(top = 2.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Build,
            contentDescription = null,
            tint = Color.Green,
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(
                R.string.forks_under_construction,
                countPrettyString(count),
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun RepoDetailsStargazers(count: Int) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Color.Yellow,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(
                id = R.string.stargazers,
                countPrettyString(count),
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

private fun countPrettyString(value: Int): String {
    return when {
        value >= 1_000_000 -> "${"%.1f".format(value / 1_000_000.0)}M"
        value >= 1_000 -> "${"%.1f".format(value / 1_000.0)}k"
        else -> value.toString()
    }
}
