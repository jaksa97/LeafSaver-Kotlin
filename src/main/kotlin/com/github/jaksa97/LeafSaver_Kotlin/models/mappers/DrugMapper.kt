package com.github.jaksa97.LeafSaver_Kotlin.models.mappers

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping


@Mapper(componentModel = "spring")
interface DrugMapper {
    @Mapping(source = "producer.id", target = "producerId")
    fun toDto(drugEntity: DrugEntity?): DrugDto

    fun toEntity(drugDto: DrugDto?): DrugEntity

    fun toEntity(drugSaveDto: DrugSaveDto?): DrugEntity
}