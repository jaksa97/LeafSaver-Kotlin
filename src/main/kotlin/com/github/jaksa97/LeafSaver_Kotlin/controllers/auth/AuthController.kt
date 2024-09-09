package com.github.jaksa97.LeafSaver_Kotlin.controllers.auth

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.AuthResponse
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.LoginRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RegisterRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface AuthController {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): AuthResponse


    @PostMapping("/login")
    @Throws(ResourceNotFoundException::class)
    fun login(@RequestBody loginRequest: LoginRequest): AuthResponse

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse)

    @PostMapping("/refreshToken")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any>
}