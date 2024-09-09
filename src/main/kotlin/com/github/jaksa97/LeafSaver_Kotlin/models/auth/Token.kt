package com.github.jaksa97.LeafSaver_Kotlin.models.auth

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "token")
data class Token(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "access_token")
    val accessToken: String,

    @Column(name = "refresh_token")
    val refreshToken: String,

    @Column(name = "is_logged_out")
    var loggedOut: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
)
