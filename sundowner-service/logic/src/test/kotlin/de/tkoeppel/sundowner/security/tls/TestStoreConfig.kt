package de.tkoeppel.sundowner.security.tls

data class TestStoreConfig(
	override val type: String,
	override val path: String,
	override val password: String,
	override val keys: Map<String, String>
) : StoreConfig(type, path, password, keys)