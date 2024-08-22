package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Builder
import lombok.Value

@Value
@Builder
@Schema(name = "Cure")
data class CureDto(
    val id: Int,
    val drugId: Int,
    val diseaseId: Int,
    val instruction: String
)
