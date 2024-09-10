package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import com.github.jaksa97.LeafSaver_Kotlin.utils.Queries
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository: JpaRepository<UserEntity, Int> {

    @Query(Queries.UserSearchQuery)
    fun findAll(firstName: String?, lastName: String?, pageable: Pageable): Page<UserEntity>

    fun findAllByRole(role: UserRoles): List<UserEntity>

    fun findByEmail(email: String): Optional<UserEntity>
}