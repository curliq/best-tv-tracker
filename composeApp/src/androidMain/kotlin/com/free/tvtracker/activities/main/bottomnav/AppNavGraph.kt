package com.free.tvtracker.activities.main.bottomnav

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.free.tvtracker.activities.add.AddShowActivity
import com.free.tvtracker.activities.discover.RecommendationsActivity
import com.free.tvtracker.activities.main.AppNavController
import com.free.tvtracker.activities.showdetails.ShowDetailsActivity
import com.free.tvtracker.activities.showdetails.ShowDetailsActivity.Extras.EXTRA_SHOW_ID
import com.free.tvtracker.ui.discover.DiscoverScreen
import com.free.tvtracker.ui.discover.DiscoverScreenNavActions
import com.free.tvtracker.ui.discover.DiscoverViewModel
import com.free.tvtracker.ui.discover.dialogs.DiscoverNewReleasesSheet
import com.free.tvtracker.ui.discover.dialogs.DiscoverTrendingSheet
import com.free.tvtracker.ui.finished.FinishedScreen
import com.free.tvtracker.ui.finished.FinishedScreenNavAction
import com.free.tvtracker.ui.search.AddTrackedScreenOriginScreen
import com.free.tvtracker.ui.watching.WatchingScreen
import com.free.tvtracker.ui.watching.WatchingScreenNavAction
import com.free.tvtracker.ui.watchlist.WatchlistScreen
import com.free.tvtracker.ui.watchlist.WatchlistScreenNavAction
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@ExperimentalMaterialNavigationApi
fun NavGraphBuilder.mainNavGraph(navController: AppNavController, context: Activity) {
    composable(AppNavDestinations.WATCHING.id) {
        WatchingScreen(
            navigate = { action ->
                when (action) {
                    WatchingScreenNavAction.GoAddShow -> context.startActivity(
                        AddShowActivity.createIntent(
                            context,
                            AddTrackedScreenOriginScreen.Watching
                        )
                    )

                    is WatchingScreenNavAction.GoShowDetails -> context.startActivity(
                        Intent(
                            context,
                            ShowDetailsActivity::class.java
                        ).putExtra(EXTRA_SHOW_ID, action.tmdbShowId)
                    )
                }
            },
            viewModel = koinViewModel()
        )
    }
    composable(AppNavDestinations.FINISHED.id) {
        FinishedScreen(
            viewModel = koinViewModel(),
            navigate = { action ->
                when (action) {
                    FinishedScreenNavAction.GoAddShow -> context.startActivity(
                        AddShowActivity.createIntent(
                            context,
                            AddTrackedScreenOriginScreen.Finished
                        )
                    )

                    is FinishedScreenNavAction.GoShowDetails -> {
                        context.startActivity(
                            Intent(
                                context,
                                ShowDetailsActivity::class.java
                            ).putExtra(EXTRA_SHOW_ID, action.tmdbShowId)
                        )
                    }
                }
            },
        )
    }
    composable(AppNavDestinations.WATCHLIST.id) {
        WatchlistScreen(
            viewModel = koinViewModel(),
            navigate = { action ->
                when (action) {
                    WatchlistScreenNavAction.GoAddShow -> context.startActivity(
                        AddShowActivity.createIntent(
                            context,
                            AddTrackedScreenOriginScreen.Watchlist
                        )
                    )

                    is WatchlistScreenNavAction.GoShowDetails -> {
                        context.startActivity(
                            Intent(
                                context,
                                ShowDetailsActivity::class.java
                            ).putExtra(EXTRA_SHOW_ID, action.tmdbShowId)
                        )
                    }
                }
            },
        )
    }
    composable(AppNavDestinations.DISCOVER.id) {
        var showBottomSheet: DiscoverNavDestinations? by remember { mutableStateOf(null) }
        val action = { action: DiscoverScreenNavActions ->
            when (action) {
                DiscoverScreenNavActions.GoAddShow -> {
                    context.startActivity(
                        AddShowActivity.createIntent(
                            context = context,
                            origin = AddTrackedScreenOriginScreen.Discover
                        )
                    )
                }

                is DiscoverScreenNavActions.GoShowDetails -> {
                    context.startActivity(
                        Intent(
                            context,
                            ShowDetailsActivity::class.java
                        ).putExtra(EXTRA_SHOW_ID, action.tmdbShowId)
                    )
                }

                DiscoverScreenNavActions.GoRecommendations -> {
                    context.startActivity(
                        Intent(
                            context,
                            RecommendationsActivity::class.java
                        )
                    )
                }

                DiscoverScreenNavActions.GoTrending -> {
                    showBottomSheet = DiscoverNavDestinations.TRENDING
                }

                DiscoverScreenNavActions.GoNewRelease -> {
                    showBottomSheet = DiscoverNavDestinations.RELEASES_SOON
                }
            }
        }
        val discoverViewModel: DiscoverViewModel = get() // get() instead of viewmodel() to share between activities
        DiscoverScreen(viewModel = discoverViewModel, action)
        val sheetState = rememberModalBottomSheetState()
        val modalMaxHeight = LocalConfiguration.current.screenHeightDp.dp.times(0.7f)
        if (showBottomSheet != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = null
                },
                sheetState = sheetState,
                windowInsets = WindowInsets(0, 0, 0, 0) // draw behind navbar
            ) {
                Box(Modifier.heightIn(0.dp, modalMaxHeight)) {
                    when (showBottomSheet) {
                        DiscoverNavDestinations.TRENDING -> {
                            DiscoverTrendingSheet(
                                viewModel = discoverViewModel,
                                navActions = action,
                                bottomPadding = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding().value
                            )
                        }

                        DiscoverNavDestinations.RELEASES_SOON -> {
                            DiscoverNewReleasesSheet(
                                viewModel = discoverViewModel,
                                navActions = action,
                                bottomPadding = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding().value
                            )
                        }
                        null -> {}
                    }
                }
            }
        }
    }
}

enum class DiscoverNavDestinations {
    TRENDING,
    RELEASES_SOON
}
