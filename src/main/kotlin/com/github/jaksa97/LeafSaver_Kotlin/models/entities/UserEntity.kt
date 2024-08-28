package com.github.jaksa97.LeafSaver_Kotlin.models.entities

import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(nullable = false, name = "first_name")
    var firstName: String,

    @Column(nullable = false, name = "last_name")
    var lastName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val role: UserRoles
)
