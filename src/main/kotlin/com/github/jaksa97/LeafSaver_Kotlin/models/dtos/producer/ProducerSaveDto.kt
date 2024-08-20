package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer

import lombok.Builder
import lombok.Value

@Value
@Builder
data class ProducerSaveDto(
    val name: String
)