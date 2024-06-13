package com.free.tvtracker.features.tracked.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackedShowEpisodeJpaRepository : JpaRepository<TrackedShowEpisodeEntity, Int>