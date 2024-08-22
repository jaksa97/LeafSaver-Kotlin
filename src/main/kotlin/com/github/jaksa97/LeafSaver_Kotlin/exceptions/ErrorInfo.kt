package com.github.jaksa97.LeafSaver_Kotlin.exceptions

import lombok.Builder
import lombok.Value

@Value
@Builder
data class ErrorInfo(
    val errorType: ErrorType? = null,
    val resourceType: ResourceType? = null,
    val message: String? = null
) {
    enum class ErrorType {
        BAD_REQUEST,
        NOT_FOUND,
        UNIQUE_VIOLATION,
        UNKNOWN
    }

    enum class ResourceType {
        PRODUCER,
        DRUG,
        DISEASE
    }
}
