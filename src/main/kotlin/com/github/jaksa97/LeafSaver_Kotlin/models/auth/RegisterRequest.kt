package com.github.jaksa97.LeafSaver_Kotlin.models.auth

import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: UserRoles
)
