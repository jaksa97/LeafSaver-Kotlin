package com.github.jaksa97.LeafSaver_Kotlin.services.jwt

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val _userRepository: UserRepository
): UserDetailsService {

    @Throws(ResourceNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        return _userRepository.findByEmail(email).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }
    }
}