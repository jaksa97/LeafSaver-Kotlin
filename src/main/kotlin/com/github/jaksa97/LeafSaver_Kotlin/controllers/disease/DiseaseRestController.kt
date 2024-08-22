package com.github.jaksa97.LeafSaver_Kotlin.controllers.disease

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseSaveDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/disease"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface DiseaseRestController {

    @GetMapping
    @Operation(summary = "Get all diseases", description = "Returns a list of all diseases")
    @ApiResponse(responseCode = "200", description = "Operation successfully")
    fun getDiseases(): List<DiseaseDto>

    @GetMapping("/{id}")
    @Operation(summary = "Get a disease by ID", description = "Returns a single disease by ID")
    @ApiResponse(responseCode = "200", description = "Operation successfully")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @Throws(ResourceNotFoundException::class)
    fun getDiseaseById(@PathVariable id: Int): DiseaseDto

    @PostMapping
    @Operation(summary = "Create a new disease", description = "Create a new disease")
    @ApiResponse(responseCode = "201", description = "Disease created successfully")
    @ApiResponse(responseCode = "400", description = "Disease already exists")
    @Throws(UniqueViolationException::class)
    fun saveDisease(@RequestBody diseaseSaveDto: DiseaseSaveDto): DiseaseDto

    @PutMapping("/{id}")
    @Operation(summary = "Update a disease with ID", description = "Update an existing disease")
    @ApiResponse(responseCode = "200", description = "Disease updated successfully")
    @ApiResponse(responseCode = "400", description = "Disease already exists")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun updateDisease(@PathVariable id: Int, @RequestBody updateDisease: DiseaseSaveDto): DiseaseDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a disease with ID", description = "Deletes an existing disease")
    @ApiResponse(responseCode = "200", description = "Disease deleted successfully")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @Throws(ResourceNotFoundException::class)
    fun deleteDisease(@PathVariable id: Int)
}