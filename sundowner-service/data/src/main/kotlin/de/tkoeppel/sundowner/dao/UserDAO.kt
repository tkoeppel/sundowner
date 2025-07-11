package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.UserPO
import org.springframework.data.jpa.repository.JpaRepository

interface UserDAO : JpaRepository<UserPO, Long> {
	fun findByUsername(username: String): UserPO?
}

