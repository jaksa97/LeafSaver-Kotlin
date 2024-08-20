package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*


interface ProducerRepository: JpaRepository<ProducerEntity, Int>, JpaSpecificationExecutor<ProducerEntity> {

    override fun findAll(): List<ProducerEntity>

    fun findByName(name: String?): Optional<ProducerEntity>
}