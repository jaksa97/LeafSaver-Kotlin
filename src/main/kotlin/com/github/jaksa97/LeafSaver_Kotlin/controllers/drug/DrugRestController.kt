package com.github.jaksa97.LeafSaver_Kotlin.controllers.drug

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugSaveDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/drugs"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface DrugRestController {

    @GetMapping
    @Operation(summary = "Get all drugs", description = "Returns a list of all drugs")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getDrugs(): List<DrugDto>

    @GetMapping("/producer/{producerId}")
    @Operation(summary = "Get all drugs for producer", description = "Returns a list of all drugs for producer")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Producer not found")
    @Throws(ResourceNotFoundException::class)
    fun getDrugsByProducerId(@PathVariable producerId: Int): List<DrugDto>

    @GetMapping("/{id}")
    @Operation(summary = "Get a drug by ID", description = "Returns a single drug")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @Throws(ResourceNotFoundException::class)
    fun getDrugById(@PathVariable id: Int): DrugDto

    @PostMapping
    @Operation(summary = "Create a new drug", description = "Creates a new drug")
    @ApiResponse(responseCode = "201", description = "Drug created successfully")
    @ApiResponse(responseCode = "400", description = "Drug already exists")
    @ApiResponse(responseCode = "404", description = "Producer not found")
    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun saveDrug(@RequestBody drugSaveDto: DrugSaveDto): DrugDto

    @PutMapping("/{id}")
    @Operation(summary = "Update a drug with ID", description = "Update an existing drug")
    @ApiResponse(responseCode = "200", description = "Drug updated successfully")
    @ApiResponse(responseCode = "400", description = "Drug already exists")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @ApiResponse(responseCode = "404", description = "Producer not found")
    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun updateDrug(@PathVariable id: Int, @RequestBody drugSaveDto: DrugSaveDto): DrugDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a producer with ID", description = "Deletes an existing producer")
    @ApiResponse(responseCode = "204", description = "Drug deleted successfully")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @Throws(ResourceNotFoundException::class)
    fun removeDrug(@PathVariable id: Int)
}