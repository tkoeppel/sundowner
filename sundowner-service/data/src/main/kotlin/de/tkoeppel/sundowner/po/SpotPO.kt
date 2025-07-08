package de.tkoeppel.sundowner.po

import de.tkoeppel.sundowner.basetype.spots.SpotStatus
import de.tkoeppel.sundowner.basetype.spots.SpotType
import de.tkoeppel.sundowner.basetype.spots.TransportType
import de.tkoeppel.sundowner.converter.PointConverter
import jakarta.persistence.*
import org.locationtech.jts.geom.Coordinate
import java.time.ZonedDateTime

@Entity
@Table(name = "spots")
data class SpotPO(
	@Column(name = "type", nullable = false) @Enumerated(EnumType.STRING) val type: SpotType,

	@Convert(converter = PointConverter::class) @Column(
		name = "location", columnDefinition = "geometry(Point, 4326)", nullable = false
	) val location: Coordinate,

	@Column(name = "name", nullable = false) val name: String,

	@Column(name = "description") val description: String? = null,

	@Column(name = "added_by", nullable = false) val addedBy: String,

	@Column(name = "added_date", nullable = false) val addedDate: ZonedDateTime,

	@Column(
		name = "transport", nullable = false
	) @ElementCollection @Enumerated(EnumType.STRING) val transport: List<TransportType>,

	@Column(name = "status", nullable = false) @Enumerated(EnumType.STRING) val status: SpotStatus,

	@OneToMany(
		mappedBy = "spot", cascade = [CascadeType.REMOVE], orphanRemoval = true
	) val reviews: List<SpotReviewPO> = emptyList()

) : BasePO()