package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.ProducerMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class ProducerService {
    private val _producerRepository: ProducerRepository? = null
    private val _producerMapper: ProducerMapper? = null

    
}