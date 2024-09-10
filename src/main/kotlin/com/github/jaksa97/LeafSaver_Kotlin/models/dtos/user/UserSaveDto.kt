package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user

import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import lombok.Builder
import lombok.Value

@Value
@Builder
data class UserSaveDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val role: UserRoles? = null
)

fun UserSaveDto.isPopulate(): Boolean {
    return firstName != null && lastName != null && email != null && password != null && role != null
}
