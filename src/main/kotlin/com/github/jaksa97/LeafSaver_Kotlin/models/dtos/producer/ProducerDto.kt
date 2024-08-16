package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer

import lombok.Value
import lombok.Builder

@Value
@Builder
data class ProducerDto(
    val id: Int,
    val name: String
)
