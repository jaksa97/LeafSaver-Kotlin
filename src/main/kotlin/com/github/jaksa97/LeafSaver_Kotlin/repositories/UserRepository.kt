package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface UserRepository: JpaRepository<UserEntity, Int>, JpaSpecificationExecutor<ProducerEntity> {

    fun findAllByFirstName(firstName: String): List<UserEntity>

    fun findAllByLastName(lastName: String): List<UserEntity>

    fun findAllByRole(role: UserRoles): List<UserEntity>

    fun findByEmail(email: String): Optional<UserEntity>
}