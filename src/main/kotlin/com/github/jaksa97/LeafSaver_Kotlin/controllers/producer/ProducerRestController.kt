package com.github.jaksa97.LeafSaver_Kotlin.controllers.producer

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSearchOptions
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping(path = ["/producers"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface ProducerRestController {

//    @GetMapping
//    fun getProducers(producerSearchOptions: ProducerSearchOptions?): Page<ProducerDto>

    @GetMapping
    fun getProducers(): List<ProducerDto>

    @GetMapping("/{id}")
    fun getProducerById(@PathVariable id: Int): ProducerDto


    @PostMapping
    fun saveProducer(@RequestBody producerSaveDto: ProducerSaveDto): ProducerDto

    @PutMapping("/{id}")
    fun updateProducer(@PathVariable id: Int, @RequestBody producerSaveDto: ProducerSaveDto): ProducerDto

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProducer(@PathVariable id: Int)
}