package de.tkoeppel.sundowner.security.tls

abstract class StoreConfig(
	open val type: String, open val path: String, open val password: String, open val keys: Map<String, String>
)