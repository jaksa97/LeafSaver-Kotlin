package com.github.jaksa97.LeafSaver_Kotlin.controllers.disease

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.services.DiseaseService
import com.github.jaksa97.LeafSaver_Kotlin.utils.PageableCreator
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@Tag(name = "Disease")
class DiseaseRestControllerImpl(
    private val _diseaseService: DiseaseService
): DiseaseRestController {

    override fun getDiseases(
        search: String?,
        page: Int?,
        pageSize: Int?,
        sortBy: String?,
        sortDirection: Sort.Direction?
    ): Page<DiseaseDto> {
        return _diseaseService.getAll(search, PageableCreator.createPageable(page, pageSize, sortBy, sortDirection))
    }

    @Throws(ResourceNotFoundException::class)
    override fun getDiseaseById(
        id: Int
    ): DiseaseDto {
        return _diseaseService.getOne(id)
    }

    @Throws(UniqueViolationException::class)
    override fun saveDisease(
        diseaseSaveDto: DiseaseSaveDto
    ): DiseaseDto {
        return _diseaseService.save(diseaseSaveDto)
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    override fun updateDisease(
        id: Int,
        updateDisease: DiseaseSaveDto
    ): DiseaseDto {
        return _diseaseService.update(id, updateDisease)
    }

    @Throws(ResourceNotFoundException::class)
    override fun deleteDisease(
        id: Int
    ) {
        _diseaseService.remove(id)
    }
}