package de.tkoeppel.sundowner.po

import de.tkoeppel.sundowner.basetype.SpotType
import de.tkoeppel.sundowner.basetype.TransportType
import jakarta.persistence.*
import org.springframework.data.geo.Point
import java.time.ZonedDateTime

@Entity
@Table(name = "spots")
data class Spot(

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	val type: SpotType = SpotType.SUNSET,

	@Column(name = "location", columnDefinition = "geography(Point, 4326)", nullable = false)
	val location: Point,

	@Column(name = "name", nullable = false)
	val name: String,

	@Column(name = "description")
	val description: String? = null,

	@Column(name = "added_by", nullable = false)
	val addedBy: String,

	@Column(name = "added_date", nullable = false)
	val addedDate: ZonedDateTime,

	@Column(name = "transport", nullable = false)
	@Enumerated(EnumType.STRING)
	val transport: TransportType
) : BasePO()