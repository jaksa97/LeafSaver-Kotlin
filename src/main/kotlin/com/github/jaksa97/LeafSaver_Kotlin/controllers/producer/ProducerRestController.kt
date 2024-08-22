package com.github.jaksa97.LeafSaver_Kotlin.controllers.producer

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/producers"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface ProducerRestController {

//    @GetMapping
//    fun getProducers(producerSearchOptions: ProducerSearchOptions?): Page<ProducerDto>

    @GetMapping
    @Operation(summary = "Get all producers", description = "Returns a list of all producers")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getProducers(): List<ProducerDto>

    @GetMapping("/{id}")
    @Operation(summary = "Get a producer by ID", description = "Returns a single producer")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Producer not found")
    @Throws(ResourceNotFoundException::class)
    fun getProducerById(@PathVariable id: Int): ProducerDto

    @PostMapping
    @Operation(summary = "Create a new producer", description = "Creates a new producer")
    @ApiResponse(responseCode = "201", description = "Producer created successfully")
    @ApiResponse(responseCode = "400", description = "Producer already exists")
    @Throws(UniqueViolationException::class)
    fun saveProducer(@RequestBody producerSaveDto: ProducerSaveDto): ProducerDto

    @PutMapping("/{id}")
    @Operation(summary = "Update a producer with ID", description = "Update an existing producer")
    @ApiResponse(responseCode = "200", description = "Producer updated successfully")
    @ApiResponse(responseCode = "400", description = "Producer already exists")
    @ApiResponse(responseCode = "404", description = "Producer not found")
    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun updateProducer(@PathVariable id: Int, @RequestBody producerSaveDto: ProducerSaveDto): ProducerDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a producer with ID", description = "Deletes an existing producer")
    @ApiResponse(responseCode = "204", description = "Producer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Producer not found")
    @Throws(ResourceNotFoundException::class)
    fun deleteProducer(@PathVariable id: Int)
}