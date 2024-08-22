package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug

import lombok.Builder
import lombok.Value

@Value
@Builder
data class DrugSaveDto(
    var name: String,
    var producerId: Int,
    var description: String
)
