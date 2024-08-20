package com.github.jaksa97.LeafSaver_Kotlin.exceptions

class UniqueViolationException(resourceType: ErrorInfo.ResourceType, message: String) : ResourceException(resourceType, message)