package de.tkoeppel.sundowner.po

import jakarta.persistence.*

@MappedSuperclass
abstract class BasePO {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	val id: Long? = null
}