package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user

import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Builder
import lombok.Value

@Value
@Builder
@Schema(name = "User")
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: UserRoles
)
