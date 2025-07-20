package de.tkoeppel.sundowner.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface GeneralDAO<T> : JpaRepository<T, Long>

