package de.tkoeppel.sundowner.dao

interface SpotAverageRating {
	fun getSpotId(): Long
	fun getAverageRating(): Double?
}