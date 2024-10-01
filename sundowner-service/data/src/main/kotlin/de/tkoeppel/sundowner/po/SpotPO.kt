package de.tkoeppel.sundowner.po

import de.tkoeppel.sundowner.basetype.SpotType
import de.tkoeppel.sundowner.basetype.TransportType
import de.tkoeppel.sundowner.converter.PointConverter
import jakarta.persistence.*
import org.locationtech.jts.geom.Coordinate
import java.time.ZonedDateTime

@Entity
@Table(name = "spots")
data class SpotPO(
	@Column(name = "type", nullable = false) @Enumerated(EnumType.STRING) val type: SpotType = SpotType.SUNSET,

	@Convert(converter = PointConverter::class) @Column(
		name = "location", columnDefinition = "geometry(Point, 4326)", nullable = false
	) val location: Coordinate,

	@Column(name = "name", nullable = false) val name: String,

	@Column(name = "description") val description: String? = null,

	@Column(name = "average_rating") val averageRating: Double,

	@Column(name = "added_by", nullable = false) val addedBy: String,

	@Column(name = "added_date", nullable = false) val addedDate: ZonedDateTime,

	@Column(
		name = "transport", nullable = false
	) @ElementCollection @Enumerated(EnumType.STRING) val transport: List<TransportType>
) : BasePO()