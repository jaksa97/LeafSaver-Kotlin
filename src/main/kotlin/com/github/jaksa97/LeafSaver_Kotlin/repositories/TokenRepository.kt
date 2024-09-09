package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.auth.Token
import com.github.jaksa97.LeafSaver_Kotlin.utils.Queries
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TokenRepository: JpaRepository<Token, Int> {

    @Query(Queries.UserTokensQuery)
    fun findAllAccessTokenByUser(userId: Int): List<Token>

    fun findByAccessToken(token: String): Optional<Token>

    fun findByRefreshToken(token: String): Optional<Token>
}