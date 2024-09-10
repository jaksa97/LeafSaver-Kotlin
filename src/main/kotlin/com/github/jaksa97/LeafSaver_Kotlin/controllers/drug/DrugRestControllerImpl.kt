package com.github.jaksa97.LeafSaver_Kotlin.controllers.drug

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.BadRequestException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.services.DrugService
import com.github.jaksa97.LeafSaver_Kotlin.utils.PageableCreator
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@Tag(name = "Drugs")
class DrugRestControllerImpl(
    private val _drugService: DrugService
): DrugRestController {

    override fun getDrugs(
        search: String?,
        page: Int?,
        pageSize: Int?,
        sortBy: String?,
        sortDirection: Sort.Direction?
    ): Page<DrugDto> {
        return _drugService.getAll(search, PageableCreator.createPageable(page, pageSize, sortBy, sortDirection))
    }

    @Throws(ResourceNotFoundException::class)
    override fun getDrugsByProducerId(producerId: Int): List<DrugDto> {
        return _drugService.getAllByProducerId(producerId)
    }

    @Throws(ResourceNotFoundException::class)
    override fun getDrugById(id: Int): DrugDto {
        return _drugService.getOne(id)
    }

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class, BadRequestException::class)
    override fun saveDrug(drugSaveDto: DrugSaveDto): DrugDto {
        return _drugService.save(drugSaveDto)
    }

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    override fun updateDrug(id: Int, drugSaveDto: DrugSaveDto): DrugDto {
        return _drugService.update(id, drugSaveDto)
    }

    @Throws(ResourceNotFoundException::class)
    override fun removeDrug(id: Int) {
        _drugService.remove(id)
    }
}