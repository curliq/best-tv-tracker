package com.free.tvtracker.ui.search

import com.free.tvtracker.base.MapperWithOptions
import com.free.tvtracker.search.response.SearchMultiApiModel
import com.free.tvtracker.ui.common.TmdbConfigData

class ShowSearchUiModelMapper :
    MapperWithOptions<SearchMultiApiModel, AddTrackedItemUiModel, SearchUiModelMapperOptions> {
    override fun map(from: SearchMultiApiModel, options: SearchUiModelMapperOptions): AddTrackedItemUiModel {
        return AddTrackedItemUiModel(
            from.tmdbId,
            from.name!!,
            TmdbConfigData.get().getPosterUrl(from.posterPath),
            options.tracked,
            when (options.originScreen) {
                AddTrackedScreenOriginScreen.Watching -> AddTrackedItemUiModel.TrackAction.Watching
                AddTrackedScreenOriginScreen.Finished ->  AddTrackedItemUiModel.TrackAction.Watching
                AddTrackedScreenOriginScreen.Watchlist ->  AddTrackedItemUiModel.TrackAction.Watchlist
                AddTrackedScreenOriginScreen.Discover ->  AddTrackedItemUiModel.TrackAction.None
            }
        )
    }
}
