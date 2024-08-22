package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.CureEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CureRepository: JpaRepository<CureEntity, Int>, JpaSpecificationExecutor<CureEntity> {

    override fun findAll(): List<CureEntity>

    fun findAllByDrugId(drugId: Int): List<CureEntity>

    fun findAllByDiseaseId(diseaseId: Int): List<CureEntity>

    fun deleteAllByDrug(drugEntity: DrugEntity)

    fun deleteAllByDisease(diseaseEntity: DiseaseEntity)
}