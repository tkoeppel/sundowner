package de.tkoeppel.sundowner.security.securestore

abstract class SecureStoreConfig(
	open val type: String, open val path: String, open val password: String, open val keys: Map<String, String>
)