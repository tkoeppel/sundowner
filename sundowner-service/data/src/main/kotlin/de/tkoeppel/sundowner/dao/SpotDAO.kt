package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.SpotPO
import org.springframework.data.jpa.repository.JpaRepository

interface SpotDAO : JpaRepository<SpotPO, Long>