package de.tkoeppel.sundowner.to

import com.fasterxml.jackson.core.type.TypeReference

class MapSpotTOTest :
	SerializationTestBase<MapSpotTO>("{\"id\":12,\"location\":{\"long\":1.1,\"lat\":2.2},\"name\":\"location name\",\"avgRating\":9.9}",
		MapSpotTO(12, CoordinateTO(1.1, 2.2), "location name", 9.9),
		object : TypeReference<MapSpotTO>() {})