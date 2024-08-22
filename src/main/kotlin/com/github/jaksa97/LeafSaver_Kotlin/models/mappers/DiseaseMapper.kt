package com.github.jaksa97.LeafSaver_Kotlin.models.mappers

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface DiseaseMapper {

    fun toDto(diseaseEntity: DiseaseEntity): DiseaseDto

    fun toEntity(diseaseDto: DiseaseDto): DiseaseEntity

    fun toEntity(diseaseSaveDto: DiseaseSaveDto): DiseaseEntity
}