package com.github.jaksa97.LeafSaver_Kotlin.controllers.user

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface UserRestController {

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getUsers(
        @Parameter(description = "Enter the number of pages you want to retrieve") page: Int?,
        @Parameter(description = "Enter the number of elements per page you want to retrieve") pageSize: Int?,
        @Parameter(description = "Enter the exact name of the field you want to sort by") sortBy: String?,
        sortDirection: Sort.Direction?
    ): Page<UserDto>

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Returns a single user")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Throws(ResourceNotFoundException::class)
    fun getUserById(@PathVariable id: Int): UserDto


    //TODO: Refactor this method
    @GetMapping("/email")
    @Operation(summary = "Get a user by email", description = "Returns a single user")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Throws(ResourceNotFoundException::class)
    fun getUserByEmail(@RequestParam email: String): UserDto

    @GetMapping("/firstName/{firstName}")
    @Operation(summary = "Get all users with first name", description = "Returns a list of all users with first name")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getUsersByFirstName(@PathVariable firstName: String): List<UserDto>

    @GetMapping("/lastName/{lastName}")
    @Operation(summary = "Get all users with last name", description = "Returns a list of all users with last name")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getUsersByLastName(@PathVariable lastName: String): List<UserDto>

    @GetMapping("/roles")
    @Operation(summary = "Get all users with last name", description = "Returns a list of all users with last name")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getUsersByRole(@RequestBody role: UserRoles): List<UserDto>

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "User already exists")
    @Throws(UniqueViolationException::class)
    fun saveUser(@RequestBody userSaveDto: UserSaveDto): UserDto

    @PutMapping("/{id}")
    @Operation(summary = "Update a user with ID", description = "Update an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "User already exists")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun updateUser(@PathVariable id: Int, @RequestBody updateUserDto: UserSaveDto): UserDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user with ID", description = "Deletes an existing user")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @Throws(ResourceNotFoundException::class)
    fun deleteUser(@PathVariable id: Int)
}