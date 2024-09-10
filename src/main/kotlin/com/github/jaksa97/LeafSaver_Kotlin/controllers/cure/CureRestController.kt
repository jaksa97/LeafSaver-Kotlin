package com.github.jaksa97.LeafSaver_Kotlin.controllers.cure

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.BadRequestException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureSaveDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/cures"], produces = [MediaType.APPLICATION_JSON_VALUE])
interface CureRestController {

    @GetMapping
    @Operation(summary = "Get all cures", description = "Returns a list of all cures")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    fun getCures(
        @Parameter(description = "Enter the number of pages you want to retrieve") page: Int?,
        @Parameter(description = "Enter the number of elements per page you want to retrieve") pageSize: Int?,
        @Parameter(description = "Enter the exact name of the field you want to sort by") sortBy: String?,
        sortDirection: Sort.Direction?
    ): Page<CureDto>

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
    @ApiResponse(responseCode = "400", description = "All params all required")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @ApiResponse(responseCode = "409", description = "Cure already exists")
    @Throws(UniqueViolationException::class, ResourceNotFoundException::class, BadRequestException::class)
    fun saveCure(@RequestBody cureSaveDto: CureSaveDto): CureDto

    @PatchMapping("/{id}")
    @Operation(summary = "Update a cure with ID", description = "Update an existing cure")
    @ApiResponse(responseCode = "200", description = "Cure updated successfully")
    @ApiResponse(responseCode = "404", description = "Cure not found")
    @ApiResponse(responseCode = "404", description = "Drug not found")
    @ApiResponse(responseCode = "404", description = "Disease not found")
    @ApiResponse(responseCode = "409", description = "Cure already exists")
    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun updateCure(@PathVariable id: Int, @RequestBody updateCureSaveDto: CureSaveDto): CureDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cure with ID", description = "Deletes an existing cure")
    @ApiResponse(responseCode = "200", description = "Cure deleted successfully")
    @ApiResponse(responseCode = "404", description = "Cure not found")
    @Throws(ResourceNotFoundException::class)
    fun removeCure(@PathVariable id: Int)
}