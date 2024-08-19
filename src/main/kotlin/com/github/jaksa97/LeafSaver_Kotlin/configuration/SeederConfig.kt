package com.github.jaksa97.LeafSaver_Kotlin.configuration

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SeederConfig {

    @Bean
    fun databaseSeeder(producerRepository: ProducerRepository) = ApplicationRunner {
        if (producerRepository.count() == 0L) {
            val producers = listOf(
                ProducerEntity(1, "Producer 1"),
                ProducerEntity(2, "Producer 2"),
                ProducerEntity(3, "Producer 3"),
                ProducerEntity(4, "Producer 4"),
                ProducerEntity(5, "Producer 5"),
            )
            producerRepository.saveAll(producers)
        }
    }
}