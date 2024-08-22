package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface DiseaseRepository: JpaRepository<DiseaseEntity, Int>, JpaSpecificationExecutor<DiseaseEntity> {

    override fun findAll(): List<DiseaseEntity>

    fun findByName(name: String): Optional<DiseaseEntity>

    fun findByNiceName(niceName: String): Optional<DiseaseEntity>
}