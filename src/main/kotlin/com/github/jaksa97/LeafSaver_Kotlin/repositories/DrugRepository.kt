package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface DrugRepository: JpaRepository<DrugEntity, Int>, JpaSpecificationExecutor<DrugEntity> {

    override fun findAll(): List<DrugEntity>

    fun findByName(name: String): Optional<DrugEntity>

    fun findAllByProducerId(producerId: Int): List<DrugEntity>

    fun deleteAllByProducer(producerEntity: ProducerEntity)
}