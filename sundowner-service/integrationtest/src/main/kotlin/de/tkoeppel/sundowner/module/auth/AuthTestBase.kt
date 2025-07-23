package de.tkoeppel.sundowner.module.auth

import de.tkoeppel.sundowner.SundownerServiceTestBase

class AuthTestBase : SundownerServiceTestBase() {
	protected val AUTH_PATH = "$API_PATH/auth"

	protected val LOGIN_PATH = "$AUTH_PATH/login"

	protected val LOGOUT_PATH = "$AUTH_PATH/logout"

}