package de.tkoeppel.sundowner.module.users

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserBean {

	/**
	 * Retrieves the currently authenticated user.
	 *
	 * @return The custom User object if a user is authenticated, otherwise null.
	 */
	fun getCurrentUser(): SundownerUser? {
		val authentication = SecurityContextHolder.getContext().authentication

		// Check if there is an authentication object and if the user is not anonymous
		if (authentication == null || !authentication.isAuthenticated || authentication.principal is String) {
			return null
		}

		// The principal should be your custom User object
		return authentication.principal as? SundownerUser
	}
}