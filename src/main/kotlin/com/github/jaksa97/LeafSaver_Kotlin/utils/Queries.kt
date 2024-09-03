package com.github.jaksa97.LeafSaver_Kotlin.utils

object Queries {

    const val ProducerNameSearchQuery = "SELECT p FROM ProducerEntity p WHERE (:name IS NULL OR :name = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))"

    const val DrugNameSearchQuery = "SELECT d FROM DrugEntity d WHERE (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))"

    const val DisaseNameSarchQuery = "SELECT d FROM DiseaseEntity d WHERE (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))"

    const val UserSearchQuery = "SELECT u FROM UserEntity u WHERE (:firstName IS NULL OR :firstName = '' OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND (:lastName IS NULL OR :lastName = '' OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))"
}