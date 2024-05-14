package com.free.tvtracker.screens.details.dialogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.free.tvtracker.core.composables.NonLazyGrid
import com.free.tvtracker.core.composables.TvImage
import com.free.tvtracker.core.composables.backdropRatio
import com.free.tvtracker.core.composables.posterRatio
import com.free.tvtracker.core.theme.TvTrackerTheme
import com.free.tvtracker.screens.details.DetailsScreenNavAction
import com.free.tvtracker.screens.details.DetailsUiModel
import com.free.tvtracker.screens.details.DetailsUiState
import com.free.tvtracker.screens.details.DetailsViewModel
import com.free.tvtracker.screens.details.MediaVideoCard

@Composable
fun DetailsMediaSheet(
    viewModel: DetailsViewModel,
    navAction: (DetailsScreenNavAction) -> Unit,
    bottomPadding: Float = 0f
) {
    val show = viewModel.result.collectAsState().value as DetailsUiState.Ok
    TvTrackerTheme {
        DetailsMediaSheetContent(show.data, navAction, bottomPadding)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsMediaSheetContent(
    show: DetailsUiModel,
    navAction: (DetailsScreenNavAction) -> Unit,
    bottomPadding: Float = 0f
) {
    fun onClick(url: String) = run { navAction(DetailsScreenNavAction.GoYoutube(url)) }
    LazyColumn {
        stickyHeader {
            Header("Trailers")
        }
        item {
            VideosGrid(show.mediaVideosTrailers, ::onClick)
        }
        stickyHeader {
            Header("Teasers")
        }
        item {
            VideosGrid(show.mediaVideosTeasers, ::onClick)
        }
        stickyHeader {
            Header("Behind the scenes")
        }
        item {
            VideosGrid(show.mediaVideosBehindTheScenes, ::onClick)
        }
        stickyHeader {
            Header("Photos: thumbnails")
        }
        item {
            if (show.mediaImagesBackdrops.isEmpty()) {
                Box(Modifier.fillMaxWidth().height(72.dp).padding(horizontal = TvTrackerTheme.sidePadding)) {
                    Text(
                        text = "No photos",
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                NonLazyGrid(
                    columns = 2,
                    itemCount = show.mediaImagesBackdrops.size,
                    Modifier.padding(TvTrackerTheme.sidePadding)
                ) { index ->
                    val photo = show.mediaImagesBackdrops[index]
                    Box(Modifier.aspectRatio(backdropRatio())) {
                        TvImage(photo, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
        stickyHeader {
            Header("Photos: posters")
        }
        item {
            if (show.mediaImagesPosters.isEmpty()) {
                Box(Modifier.fillMaxWidth().height(72.dp).padding(horizontal = TvTrackerTheme.sidePadding)) {
                    Text(
                        text = "No photos",
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                NonLazyGrid(
                    columns = 3,
                    itemCount = show.mediaImagesPosters.size,
                    Modifier.padding(TvTrackerTheme.sidePadding)
                ) { index ->
                    val photo = show.mediaImagesPosters[index]
                    Box(Modifier.aspectRatio(posterRatio())) {
                        TvImage(photo, modifier = Modifier.fillMaxSize())
                    }
                }
            }
            Spacer(Modifier.height(bottomPadding.dp))
        }
    }
}

@Composable
private fun LazyItemScope.Header(text: String) {
    Column(Modifier.fillParentMaxWidth().background(MaterialTheme.colorScheme.surfaceContainerLow)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = TvTrackerTheme.sidePadding, vertical = 8.dp)
                    .weight(1f)
            )
            Spacer(Modifier.width(TvTrackerTheme.sidePadding))
        }
        HorizontalDivider()
    }
}

@Composable
private fun VideosGrid(videos: List<DetailsUiModel.Video>, onClick: (String) -> Unit) {
    if (videos.isEmpty()) {
        Box(
            Modifier.fillMaxWidth().height(72.dp).padding(horizontal = TvTrackerTheme.sidePadding),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "No videos",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    } else {
        NonLazyGrid(
            columns = 3,
            itemCount = videos.size,
            Modifier.padding(TvTrackerTheme.sidePadding)
        ) { index ->
            val video = videos[index]
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier.fillMaxWidth().clickable { onClick(video.videoUrl) },
            ) {
                MediaVideoCard(trailer = video)
                Spacer(Modifier.width(8.dp))
                Column(Modifier.padding(8.dp)) {
                    Text(
                        video.title ?: "",
                        minLines = 1,
                        maxLines = 3,
                        overflow = Ellipsis,
                        style = TvTrackerTheme.Typography.labelMedium
                    )
                }
            }
        }
    }
}
