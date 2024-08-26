package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface DiseaseRepository: JpaRepository<DiseaseEntity, Int>, JpaSpecificationExecutor<DiseaseEntity> {

    @Query("SELECT d FROM DiseaseEntity d WHERE (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    fun findAll(name: String?, pageable: Pageable): Page<DiseaseEntity>

    fun findByName(name: String): Optional<DiseaseEntity>

    fun findByNiceName(niceName: String): Optional<DiseaseEntity>
}