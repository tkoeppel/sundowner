package de.tkoeppel.sundowner.security

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithUserSecurityContextFactory::class)
annotation class WithUser(
	val username: String = "user"
)