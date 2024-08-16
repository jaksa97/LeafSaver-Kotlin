package com.github.jaksa97.LeafSaver_Kotlin.models.mappers

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ProducerMapper {

    fun toDto(producerEntity: ProducerEntity): ProducerDto

    fun toEntity(producerDto: ProducerDto): ProducerEntity

    fun toEntity(producerSaveDto: ProducerSaveDto): ProducerSaveDto
}