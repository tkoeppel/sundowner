package de.tkoeppel.sundowner.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel

@Converter
class PointConverter : AttributeConverter<Coordinate, Point> {
	private val geoFactory = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING))

	override fun convertToDatabaseColumn(attribute: Coordinate?): Point? {
		return this.geoFactory.createPoint(attribute)
	}

	override fun convertToEntityAttribute(dbData: Point?): Coordinate? {
		return dbData?.coordinate
	}

}