package com.free.tvtracker.stored.shows.data

import com.free.tvtracker.tracked.data.TrackedShowEpisodeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp

@Entity
@Table(name = "stored_episodes")
data class StoredEpisodeEntity(

    @Id
    val id: String = "",

    @CreationTimestamp
    @Column(
        name = "created_at_datetime",
        nullable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    val createdAtDatetime: String = "",

    @Column(name = "air_date", nullable = true)
    val airDate: String? = null,

    @Column(nullable = false, name = "season_number")
    val seasonNumber: Int = 0,

    @Column(nullable = false, name = "episode_number")
    val episodeNumber: Int = 0,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "storedEpisode")
    val trackedEpisodes: List<TrackedShowEpisodeEntity> = emptyList(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storedshow_id", nullable = false)
    val storedShow: StoredShowEntity = StoredShowEntity(),
)
