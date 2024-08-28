package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user

import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import lombok.Builder
import lombok.Value

@Value
@Builder
data class UserSaveDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: UserRoles
)
