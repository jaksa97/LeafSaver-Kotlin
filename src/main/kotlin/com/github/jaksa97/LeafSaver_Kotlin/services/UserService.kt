package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.UserMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService(
    private val _userRepository: UserRepository,
    private val _userMapper: UserMapper
) {

    @Throws(ResourceNotFoundException::class)
    fun getOne(
        id: Int
    ): UserDto {
        val userEntity = _userRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }

        return _userMapper.toDto(userEntity)
    }

    fun getAll(pageable: Pageable): Page<UserDto> = _userRepository.findAll(pageable).map(_userMapper::toDto)

    fun getAllByFirstName(firstName: String): List<UserDto> = _userRepository.findAllByFirstName(firstName).map(_userMapper::toDto)

    fun getAllByLastName(lastName: String): List<UserDto> = _userRepository.findAllByLastName(lastName).map(_userMapper::toDto)

    fun getAllByRole(role: UserRoles): List<UserDto> = _userRepository.findAllByRole(role).map(_userMapper::toDto)

    @Throws(ResourceNotFoundException::class)
    fun getByEmail(
        email: String
    ): UserDto {
        val userEntity = _userRepository.findByEmail(email).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER, "User with email $email don't exist")
        }

        return _userMapper.toDto(userEntity)
    }

    @Throws(UniqueViolationException::class)
    fun save(
        userSaveDto: UserSaveDto
    ): UserDto {
        if (_userRepository.findByEmail(userSaveDto.email).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.USER, "'email' already exists")
        }

        return _userMapper.toDto(_userRepository.save(_userMapper.toEntity(userSaveDto)))
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun update(
        id: Int,
        updateUser: UserSaveDto
    ): UserDto {
        val originalUserEntity = _userRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }

        if (originalUserEntity.email != updateUser.email && _userRepository.findByEmail(updateUser.email).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.USER, "'email' already exists")
        }

        val userEntity = _userMapper.toEntity(updateUser)
        userEntity.id = id

        _userRepository.save(userEntity)

        return _userMapper.toDto(userEntity)
    }

    @Throws(ResourceNotFoundException::class)
    fun remove(
        id: Int
    ) {
        if (!_userRepository.existsById(id)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }

        _userRepository.deleteById(id)
    }
}