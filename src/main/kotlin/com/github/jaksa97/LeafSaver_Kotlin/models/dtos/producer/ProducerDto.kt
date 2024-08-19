package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Value
import lombok.Builder

@Value
@Builder
@Schema(name = "Producer")
data class ProducerDto(
    val id: Int,
    val name: String
)
