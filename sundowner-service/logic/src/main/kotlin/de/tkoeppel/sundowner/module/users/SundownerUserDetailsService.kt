package de.tkoeppel.sundowner.module.users

import de.tkoeppel.sundowner.dao.UserDAO
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SundownerUserDetailsService(private var userDAO: UserDAO) : UserDetailsService {

	/** @see UserDetailsService.loadUserByUsername */
	override fun loadUserByUsername(username: String): SundownerUser {
		val user = this.userDAO.findByUsername(username)
		if (user == null) {
			throw UsernameNotFoundException("User not found with username: $username");
		}

		return SundownerUser(
			user.id, user.passwordHash, user.email, user.active, user.creationTime, user.authorities
		)
	}
}