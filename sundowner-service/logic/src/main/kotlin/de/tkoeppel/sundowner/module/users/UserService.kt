package de.tkoeppel.sundowner.module.users

import de.tkoeppel.sundowner.dao.UserDAO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
	private val userDAO: UserDAO, private val passwordEncoder: PasswordEncoder
) {
	fun createUser(rawPassword: String) {
		// TODO
	}
}