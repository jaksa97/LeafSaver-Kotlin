package com.github.jaksa97.LeafSaver_Kotlin.exceptions

abstract class ResourceException(var resourceType: ErrorInfo.ResourceType, message: String?): Exception(message)