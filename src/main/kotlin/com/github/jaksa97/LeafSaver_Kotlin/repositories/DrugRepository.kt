package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.util.*

interface DrugRepository: JpaRepository<DrugEntity, Int>, JpaSpecificationExecutor<DrugEntity> {

    @Query("SELECT d FROM DrugEntity d WHERE (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    fun findAll(name: String?, pageable: Pageable): Page<DrugEntity>

    fun findByName(name: String): Optional<DrugEntity>

    fun findAllByProducerId(producerId: Int): List<DrugEntity>

    fun deleteAllByProducer(producerEntity: ProducerEntity)
}