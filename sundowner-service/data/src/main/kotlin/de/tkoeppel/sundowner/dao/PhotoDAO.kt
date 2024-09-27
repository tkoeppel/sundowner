package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.PhotoPO
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoDAO : JpaRepository <PhotoPO, Long>