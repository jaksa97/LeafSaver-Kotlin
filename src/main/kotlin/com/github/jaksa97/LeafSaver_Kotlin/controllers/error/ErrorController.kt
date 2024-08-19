package com.github.jaksa97.LeafSaver_Kotlin.controllers.error

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.BadRequestException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@ResponseBody
class ErrorController {

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(e: BadRequestException): ErrorInfo {
        return ErrorInfo(
            errorType = ErrorInfo.ErrorType.BAD_REQUEST,
            message = e.message
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): ErrorInfo {
        return ErrorInfo(
            errorType = ErrorInfo.ErrorType.NOT_FOUND,
            resourceType = e.resourceType,
            message = e.message
        )
    }

    @ExceptionHandler(UniqueViolationException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun handleUniqueViolationException(e: UniqueViolationException): ErrorInfo {
        return ErrorInfo(
            errorType = ErrorInfo.ErrorType.UNIQUE_VIOLATION,
            resourceType = e.resourceType,
            message = e.message
        )
    }
}