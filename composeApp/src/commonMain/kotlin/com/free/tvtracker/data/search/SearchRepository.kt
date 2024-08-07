package com.free.tvtracker.data.search

import com.free.tvtracker.Endpoints
import com.free.tvtracker.base.ApiError
import com.free.tvtracker.details.request.TmdbMovieDetailsApiRequestBody
import com.free.tvtracker.discover.request.RecommendedContentApiRequestBody
import com.free.tvtracker.details.request.TmdbPersonApiRequestBody
import com.free.tvtracker.details.request.TmdbShowDetailsApiRequestBody
import com.free.tvtracker.details.response.TmdbMovieDetailsApiResponse
import com.free.tvtracker.discover.response.RecommendedContentApiResponse
import com.free.tvtracker.details.response.TmdbPersonDetailsApiResponse
import com.free.tvtracker.details.response.TmdbShowDetailsApiResponse
import com.free.tvtracker.discover.request.PagedContentApiRequestBody
import com.free.tvtracker.discover.response.TmdbMovieTrendingApiResponse
import com.free.tvtracker.discover.response.TmdbShowTrendingApiResponse
import com.free.tvtracker.expect.data.TvHttpClient
import com.free.tvtracker.search.request.MediaType
import com.free.tvtracker.search.request.SearchApiRequestBody
import com.free.tvtracker.search.response.SearchApiResponse
import io.ktor.utils.io.CancellationException

class SearchRepository(
    private val httpClient: TvHttpClient
) {
    suspend fun searchAll(term: String): SearchApiResponse {
        return try {
            val body = SearchApiRequestBody(term, MediaType.ALL)
            httpClient.call(Endpoints.search, body)
        } catch (e: Throwable) {
            if (e is CancellationException) {
                SearchApiResponse.error(ApiError.Cancelled)
            } else {
                SearchApiResponse.error(ApiError.Network)
            }
        }
    }

    suspend fun searchTvShows(term: String): SearchApiResponse {
        return try {
            val body = SearchApiRequestBody(term, MediaType.TV_SHOWS)
            httpClient.call(Endpoints.search, body)
        } catch (e: Throwable) {
            if (e is CancellationException) {
                SearchApiResponse.error(ApiError.Cancelled)
            } else {
                SearchApiResponse.error(ApiError.Network)
            }
        }
    }

    suspend fun getShow(showTmdbId: Int, includeEpisodes: Boolean, countryCode: String): TmdbShowDetailsApiResponse {
        val body = TmdbShowDetailsApiRequestBody(showTmdbId, includeEpisodes, countryCode)
        return try {
            httpClient.call(Endpoints.getTmdbShow, body)
        } catch (e: Throwable) {
            TmdbShowDetailsApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getMovie(movieTmdbId: Int, countryCode: String): TmdbMovieDetailsApiResponse {
        val body = TmdbMovieDetailsApiRequestBody(movieTmdbId, countryCode)
        return try {
            httpClient.call(Endpoints.getTmdbMovie, body)
        } catch (e: Throwable) {
            TmdbMovieDetailsApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getPerson(tmdbPersonId: Int): TmdbPersonDetailsApiResponse {
        val body = TmdbPersonApiRequestBody(tmdbPersonId)
        return try {
            httpClient.call(Endpoints.getTmdbPerson, body)
        } catch (e: Throwable) {
            TmdbPersonDetailsApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getTrendingWeeklyShows(page: Int): TmdbShowTrendingApiResponse {
        return try {
            httpClient.call(Endpoints.getTrendingWeeklyShows, PagedContentApiRequestBody(page))
        } catch (e: Throwable) {
            TmdbShowTrendingApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getTrendingWeeklyMovies(page: Int): TmdbMovieTrendingApiResponse {
        return try {
            httpClient.call(Endpoints.getTrendingWeeklyMovies, PagedContentApiRequestBody(page))
        } catch (e: Throwable) {
            TmdbMovieTrendingApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getNewEpisodeReleasedSoon(page: Int): TmdbShowTrendingApiResponse {
        return try {
            httpClient.call(Endpoints.getNewEpisodeReleasedSoon, PagedContentApiRequestBody(page))
        } catch (e: Throwable) {
            TmdbShowTrendingApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getNewMoviesReleasedSoon(page: Int): TmdbMovieTrendingApiResponse {
        return try {
            httpClient.call(Endpoints.getReleasedSoonMovies, PagedContentApiRequestBody(page))
        } catch (e: Throwable) {
            TmdbMovieTrendingApiResponse.error(ApiError.Network)
        }
    }

    suspend fun getRecommended(relatedShows: List<Int>, tvShows: Boolean): RecommendedContentApiResponse {
        return try {
            val body = RecommendedContentApiRequestBody(relatedShows)
            if (tvShows) {
                httpClient.call(Endpoints.getRecommendedShows, body)
            } else {
                httpClient.call(Endpoints.getRecommendedMovies, body)
            }
        } catch (e: Throwable) {
            RecommendedContentApiResponse.error(ApiError.Network)
        }
    }
}
