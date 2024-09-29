package de.tkoeppel.sundowner.to

import com.fasterxml.jackson.core.type.TypeReference

class CoordinateTOTest : SerializationTestBase<CoordinateTO>(
	"{\"long\":1.1,\"lat\":2.2}",
	CoordinateTO(1.1, 2.2),
	object : TypeReference<CoordinateTO>() {})