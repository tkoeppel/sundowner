package de.tkoeppel.sundowner.to

import com.fasterxml.jackson.core.type.TypeReference
import de.tkoeppel.sundowner.to.spots.CoordinateTO
import de.tkoeppel.sundowner.to.spots.MapSpotTO

class MapSpotTOTest : SerializationTestBase<MapSpotTO>(
	"{\"id\":12,\"location\":{\"lng\":1.1,\"lat\":2.2},\"name\":\"location name\",\"avgRating\":9.9}",
	MapSpotTO(12, CoordinateTO(1.1, 2.2), "location name", 9.9),
	object : TypeReference<MapSpotTO>() {})