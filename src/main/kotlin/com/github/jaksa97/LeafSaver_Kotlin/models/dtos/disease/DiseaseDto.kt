package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Builder
import lombok.Value

@Value
@Builder
@Schema(name = "Disease")
data class DiseaseDto(
    val id: Int,
    val name: String,
    val niceName: String
)
