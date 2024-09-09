package com.github.jaksa97.LeafSaver_Kotlin.models.auth

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "token")
data class Token(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val token: String,

    @Column(name = "is_logged_out")
    var loggedOut: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
)
