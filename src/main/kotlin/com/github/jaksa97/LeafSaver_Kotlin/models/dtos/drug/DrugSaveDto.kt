package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug

import lombok.Builder
import lombok.Value

@Value
@Builder
data class DrugSaveDto(
    var name: String? = null,
    var producerId: Int? = null,
    var description: String? = null
)

fun DrugSaveDto.isPopulate(): Boolean {
    return name != null && producerId != null && description != null
}
