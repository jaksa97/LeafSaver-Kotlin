package com.github.jaksa97.LeafSaver_Kotlin.repositories

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import com.github.jaksa97.LeafSaver_Kotlin.utils.Queries
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*


interface ProducerRepository: JpaRepository<ProducerEntity, Int> {

    @Query(Queries.ProducerNameSearchQuery)
    fun findAll(name: String?, pageable: Pageable): Page<ProducerEntity>

    fun findByName(name: String?): Optional<ProducerEntity>
}