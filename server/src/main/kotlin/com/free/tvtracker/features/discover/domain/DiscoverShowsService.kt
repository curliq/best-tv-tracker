package com.free.tvtracker.features.discover.domain

import com.free.tvtracker.discover.request.RecommendedContentApiRequestBody
import com.free.tvtracker.discover.response.ErrorRecommendedNotShowTracked
import com.free.tvtracker.discover.response.RecommendedContentApiResponse
import com.free.tvtracker.logging.TvtrackerLogger
import com.free.tvtracker.tmdb.TmdbClient
import com.free.tvtracker.tmdb.data.TmdbShowSmallResponse
import com.free.tvtracker.tmdb.data.TmdbTrendingShowsResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DiscoverShowsService(
    private val logger: TvtrackerLogger,
    private val tmdbClient: TmdbClient,
) {
    fun getTrendingWeeklyShows(page: Int): TmdbTrendingShowsResponse {
        val respEntity = tmdbClient.get(
            "/3/trending/tv/week",
            TmdbTrendingShowsResponse::class.java,
            params = mapOf(
                "page" to page,
            )
        )
        return respEntity.body!!
    }

    fun getReleasedSoonShows(page: Int): TmdbTrendingShowsResponse {
        val today = LocalDate.now().toString()
        val respEntity = tmdbClient.get(
            "/3/discover/tv",
            TmdbTrendingShowsResponse::class.java,
            params = mapOf(
                "with_original_language" to "en",
                "air_date.gte" to today,
                "page" to page,
            )
        )
        return respEntity.body!!
    }

    fun getRecommended(body: RecommendedContentApiRequestBody): ResponseEntity<RecommendedContentApiResponse>? {
        val res: ArrayList<TmdbShowSmallResponse> = arrayListOf()
        val relatedShows = body.relatedContentTmdbIds.takeIf { it.isNotEmpty() } ?: return ResponseEntity(
            RecommendedContentApiResponse.error(ErrorRecommendedNotShowTracked),
            HttpStatus.NO_CONTENT
        )
        relatedShows.forEach { id ->
            val rec = getTmdbRecommended(id, page = 1)
            res.addAll(rec.results)
            if ((rec.totalPages ?: 1) > 1) {
                val recPage2 = getTmdbRecommended(id, page = 2)
                res.addAll(recPage2.results)
            }
        }
        // filter results that match both selected shows
        val matches = res.groupingBy { it }.eachCount().filter { it.value == relatedShows.size }
            .filterNot { body.relatedContentTmdbIds.contains(it.key.id) }
        return ResponseEntity.ok(
            RecommendedContentApiResponse.ok(
                matches.keys.toList().toApiModel(relatedShows)
            )
        )
    }

    private fun getTmdbRecommended(showId: Int, page: Int): TmdbTrendingShowsResponse {
        val respEntity = tmdbClient.get(
            "/3/tv/$showId/recommendations",
            TmdbTrendingShowsResponse::class.java,
            params = mapOf("page" to page.toString())
        )
        return respEntity.body!!
    }
}


