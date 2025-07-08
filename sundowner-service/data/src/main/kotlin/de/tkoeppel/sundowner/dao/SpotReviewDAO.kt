package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.SpotReviewPO
import org.springframework.data.jpa.repository.JpaRepository

interface SpotReviewDAO : JpaRepository<SpotReviewPO, Long> {


}