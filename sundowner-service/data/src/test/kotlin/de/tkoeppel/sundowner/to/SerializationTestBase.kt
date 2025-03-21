package de.tkoeppel.sundowner.to

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class SerializationTestBase<T>(
	val json: String, val obj: T, val type: TypeReference<T>
) {
	private val mapper = ObjectMapper().registerKotlinModule()

	@Test
	fun testSerialization() {
		val serialized = this.mapper.writeValueAsString(obj)
		assertEquals(json, serialized)
	}

	@Test
	fun testSerializedToDeserialized() {
		val serialized = this.mapper.writeValueAsString(obj)
		val deserialized = this.mapper.readValue(serialized, type)
		assertEquals(obj, deserialized)
	}

	@Test
	fun testDeserialization() {
		val deserialized = this.mapper.readValue(json, type)
		assertEquals(obj, deserialized)
	}

	@Test
	fun testDeserializedToSerialized() {
		val deserialized = this.mapper.readValue(json, type)
		val serialized = this.mapper.writeValueAsString(deserialized)
		assertEquals(json, serialized)
	}
}