package com.github.jaksa97.LeafSaver_Kotlin.models.mappers

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.CureEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CureMapper {

    @Mapping(source = "drug.id", target = "drugId")
    @Mapping(source = "disease.id", target = "diseaseId")
    fun toDto(cureEntity: CureEntity): CureDto

    fun toEntity(cureDto: CureDto): CureEntity

    fun toEntity(cureSaveDto: CureSaveDto): CureEntity
}