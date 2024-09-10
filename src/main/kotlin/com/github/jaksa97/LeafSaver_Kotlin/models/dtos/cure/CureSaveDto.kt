package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure

import lombok.Builder
import lombok.Value

@Value
@Builder
data class CureSaveDto(
    val drugId: Int? = null,
    val diseaseId: Int? = null,
    val instruction: String? = null
)

fun CureSaveDto.isPopulate(): Boolean {
    return drugId != null && diseaseId != null && instruction != null
}
