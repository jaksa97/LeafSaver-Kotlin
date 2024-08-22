package com.github.jaksa97.LeafSaver_Kotlin.controllers.cure

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureSaveDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/cures"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface CureRestController {

    @GetMapping
    @Operation(summary = "Get all cures", description = "Returns a list of all cures")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getCures(): List<CureDto>

    @GetMapping("/drug/{drugId}")
    @Operation(summary = "Get all cures for drug", description = "Returns a list of all cures for drug")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @Throws(ResourceNotFoundException::class)
    fun getCuresByDrugId(@PathVariable drugId: Int): List<CureDto>

    @GetMapping("/disease/{diseaseId}")
    @Operation(summary = "Get all cures for disease", description = "Returns a list of all cures for disease")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @Throws(ResourceNotFoundException::class)
    fun getCuresByDiseaseId(@PathVariable diseaseId: Int): List<CureDto>

    @GetMapping("/{id}")
    @Operation(summary = "Get a cure by ID", description = "Returns a single cure")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Cure not found")
    @Throws(ResourceNotFoundException::class)
    fun getCureById(@PathVariable id: Int): CureDto

    @PostMapping
    @Operation(summary = "Create a new cure", description = "Creates a new cure")
    @ApiResponse(responseCode = "201", description = "Cure created successfully")
    @ApiResponse(responseCode = "400", description = "Cure already exists")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun saveCure(@RequestBody cureSaveDto: CureSaveDto): CureDto

    @PutMapping("/{id}")
    @Operation(summary = "Update a cure with ID", description = "Update an existing cure")
    @ApiResponse(responseCode = "200", description = "Cure updated successfully")
    @ApiResponse(responseCode = "400", description = "Cure already exists")
    @ApiResponse(responseCode = "404", description = "Cure not found")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun updateCure(@PathVariable id: Int, @RequestBody updateCureSaveDto: CureSaveDto): CureDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cure with ID", description = "Deletes an existing cure")
    @ApiResponse(responseCode = "200", description = "Cure deleted successfully")
    @ApiResponse(responseCode = "404", description = "Cure not found")
    @Throws(ResourceNotFoundException::class)
    fun removeCure(@PathVariable id: Int)
}