package com.github.jaksa97.LeafSaver_Kotlin.exceptions

class ResourceNotFoundException : ResourceException {
    constructor(resourceType: ErrorInfo.ResourceType) : super(
        resourceType,
        "Resource '$resourceType' not found"
    )

    constructor(resourceType: ErrorInfo.ResourceType, message: String) : super(
        resourceType, message
    )
}