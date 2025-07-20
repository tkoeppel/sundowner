package de.tkoeppel.sundowner.dao

import de.tkoeppel.sundowner.po.UserPO

interface UserDAO : GeneralDAO<UserPO> {
	fun findByUsername(username: String): UserPO?
}

