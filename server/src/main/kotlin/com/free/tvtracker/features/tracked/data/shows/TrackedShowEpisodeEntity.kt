package com.free.tvtracker.features.tracked.data.shows

import com.free.tvtracker.storage.shows.data.StoredEpisodeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.CreationTimestamp

@Entity
@Table(
    name = "tracked_episodes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["storedepisode_id", "trackedshow_id"])]
)
data class TrackedShowEpisodeEntity(
    @Id
    val id: String = "",

    @CreationTimestamp
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAtDatetime: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storedepisode_id", nullable = false, insertable = false, updatable = false)
    val storedEpisode: StoredEpisodeEntity = StoredEpisodeEntity(),

    @Column(name="storedepisode_id", nullable = false)
    val storedEpisodeId: Int = 0,

    // this doesn't actually work, but keep just to show that there is a relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackedshow_id", nullable = false, updatable = false, insertable = false)
    val trackedTvShow: TrackedShowEntity = TrackedShowEntity(),

    @Column(name = "trackedshow_id", nullable = false)
    val trackedTvShowId: Int = 0,
)
