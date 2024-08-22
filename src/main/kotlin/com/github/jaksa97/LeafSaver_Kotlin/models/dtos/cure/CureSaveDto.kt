package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure

import lombok.Builder
import lombok.Value

@Value
@Builder
data class CureSaveDto(
    val drugId: Int,
    val diseaseId: Int,
    val instruction: String
)
