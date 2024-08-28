package com.github.jaksa97.LeafSaver_Kotlin.models.mappers

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {

    fun toDto(userEntity: UserEntity): UserDto {
        return UserDto(
            id = userEntity.id,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            email = userEntity.email,
            role = userEntity.role
        )
    }

    fun toEntity(userDto: UserDto): UserEntity

    fun toEntity(userSaveDto: UserSaveDto): UserEntity
}