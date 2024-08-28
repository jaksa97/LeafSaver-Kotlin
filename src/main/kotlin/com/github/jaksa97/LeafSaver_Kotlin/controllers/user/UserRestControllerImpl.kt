package com.github.jaksa97.LeafSaver_Kotlin.controllers.user

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.services.UserService
import com.github.jaksa97.LeafSaver_Kotlin.utils.PageableCreator
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
class UserRestControllerImpl(
    private val _userService: UserService
): UserRestController {

    override fun getUsers(
        page: Int?,
        pageSize: Int?,
        sortBy: String?,
        sortDirection: Sort.Direction?
    ): Page<UserDto> {
        return _userService.getAll(PageableCreator.createPageable(page, pageSize, sortBy, sortDirection))
    }

    @Throws(ResourceNotFoundException::class)
    override fun getUserById(id: Int): UserDto {
        return _userService.getOne(id)
    }

    @Throws(ResourceNotFoundException::class)
    override fun getUserByEmail(email: String): UserDto {
        return _userService.getByEmail(email)
    }

    override fun getUserByFirstName(firstName: String): List<UserDto> {
        return _userService.getAllByFirstName(firstName)
    }

    override fun getUserByLastName(lastName: String): List<UserDto> {
        return _userService.getAllByLastName(lastName)
    }

    @Throws(UniqueViolationException::class)
    override fun saveUser(userSaveDto: UserSaveDto): UserDto {
        return _userService.save(userSaveDto)
    }

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    override fun updateUser(id: Int, updateUserDto: UserSaveDto): UserDto {
        return _userService.update(id, updateUserDto)
    }

    @Throws(ResourceNotFoundException::class)
    override fun deleteUser(id: Int) {
        _userService.remove(id)
    }
}