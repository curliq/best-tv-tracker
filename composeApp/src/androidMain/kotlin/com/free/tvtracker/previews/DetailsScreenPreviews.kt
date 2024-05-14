package com.free.tvtracker.previews

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.free.tvtracker.core.theme.TvTrackerTheme
import com.free.tvtracker.previews.DetailsScreenPreviews.uimodel
import com.free.tvtracker.screens.details.DetailsScreenContent
import com.free.tvtracker.screens.details.DetailsUiModel

object DetailsScreenPreviews {
    val uimodel = DetailsUiModel(
        tmdbId = 1,
        name = "game of thrones",
        posterUrl = "",
        homepageUrl = "",
        releaseStatus = "2014 - Ongoing",
        trackingStatus = "currently watching",
        description = "game of thrones is a show about society",
        seasonsInfo = "2 seasons - 16 episodes - 0h40m each",
        castFirst = DetailsUiModel.Cast("William Dicksdoor 2 lines", "King Joffrey", ""),
        castSecond = DetailsUiModel.Cast("Peter O'mo", "Joffrey's brother", ""),
        watchProviders = listOf(
            DetailsUiModel.WatchProvider("", ""),
            DetailsUiModel.WatchProvider("", ""),
            DetailsUiModel.WatchProvider("", ""),
            DetailsUiModel.WatchProvider("", ""),
            DetailsUiModel.WatchProvider("", ""),
            DetailsUiModel.WatchProvider("", ""),
        ),
        seasons = listOf(
            DetailsUiModel.Season(
                1, 1, "", false, true,
                listOf(
                    DetailsUiModel.Season.Episode(
                        1, "ep 1", "1", "name", "date", false, true,
                    ),
                    DetailsUiModel.Season.Episode(
                        1, "ep 1", "1", "name", "date", false, true,
                    ),
                    DetailsUiModel.Season.Episode(
                        1, "ep 1", "1", "name", "date", false, true,
                    ),
                ),
            ),
            DetailsUiModel.Season(
                1, 1, "", false, true,
                listOf(
                    DetailsUiModel.Season.Episode(
                        1, "ep 1", "1", "name", "date", true, true,
                    ),
                    DetailsUiModel.Season.Episode(
                        1,
                        "",
                        "1",
                        "ep 1123 12 3123123123 3231323 3 3dasd asd asdasdasd 2",
                        "date",
                        true,
                        true
                    ),
                    DetailsUiModel.Season.Episode(
                        1, "ep 1", "1", "name", "date", true, true,
                    ),
                ),
            )
        ),
        mediaTrailer = DetailsUiModel.Video("thumbnail url", "video url", "video title"),
        mediaVideosTrailers = listOf(
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
        ),
        mediaVideosTeasers = listOf(
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
        ),
        mediaVideosBehindTheScenes = listOf(
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
            DetailsUiModel.Video("thumbnail url", "video url", "video title"),
        ),
        mediaMostPopularImage = "url",
        mediaImagesPosters = listOf("url", "url", "url", "url", "url", "url", "url"),
        mediaImagesBackdrops = listOf("url", "url", "url", "url", "url", "url", "url")
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(heightDp = 1200)
@Composable
fun DetailsScreenPreview() {
    TvTrackerTheme {
        Scaffold { padding ->
            DetailsScreenContent(uimodel, {})
        }
    }
}
