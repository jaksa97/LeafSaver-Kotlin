package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease

import lombok.Builder
import lombok.Value

@Value
@Builder
data class DiseaseSaveDto(
    var name: String? = null,
    var niceName: String? = null
)

fun DiseaseSaveDto.isPopulate(): Boolean {
    return name != null && niceName != null
}
