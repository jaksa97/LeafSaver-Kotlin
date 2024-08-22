package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Builder
import lombok.Value

@Value
@Builder
@Schema(name = "Drug")
data class DrugDto(
    val id: Int,
    val name: String,
    val producerId: Int,
    val description: String
)
