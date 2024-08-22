package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease

import lombok.Builder
import lombok.Value

@Value
@Builder
data class DiseaseSaveDto(
    var name: String,
    var niceName: String
)
