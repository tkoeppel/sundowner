package de.tkoeppel.sundowner.module.users

import de.tkoeppel.sundowner.dao.UserDAO
import de.tkoeppel.sundowner.po.UserPO
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SundownerUserDetailsService(private var userDAO: UserDAO) : UserDetailsService {

	/** @see UserDetailsService.loadUserByUsername */
	override fun loadUserByUsername(username: String): SundownerUser {
		val user = this.userDAO.findByUsername(username)
		if (user == null) {
			throw BadCredentialsException("User not found with username: $username");
		}

		return SundownerUser(
			user.id!!, user.username, user.passwordHash, user.email, user.active, user.createdAt, user.authorities
		)
	}

	fun loadUserByUserPO(userPO: UserPO?): SundownerUser {
		if (userPO == null || userPO.id == null) {
			throw BadCredentialsException("User not found with username: ${userPO?.username}")
		}

		return SundownerUser(
			userPO.id!!,
			userPO.username,
			userPO.passwordHash,
			userPO.email,
			userPO.active,
			userPO.createdAt,
			userPO.authorities
		)
	}
}