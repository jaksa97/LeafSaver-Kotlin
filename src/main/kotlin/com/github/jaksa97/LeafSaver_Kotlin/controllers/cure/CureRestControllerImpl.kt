package com.github.jaksa97.LeafSaver_Kotlin.controllers.cure

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.services.CureService
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@Tag(name = "Cures")
class CureRestControllerImpl(
    private val _cureService: CureService
): CureRestController {

    override fun getCures(): List<CureDto> {
        return _cureService.getAll()
    }

    @Throws(ResourceNotFoundException::class)
    override fun getCuresByDrugId(drugId: Int): List<CureDto> {
        return _cureService.getAllByDrugId(drugId)
    }

    @Throws(ResourceNotFoundException::class)
    override fun getCuresByDiseaseId(diseaseId: Int): List<CureDto> {
        return _cureService.getAllByDiseaseId(diseaseId)
    }

    @Throws(ResourceNotFoundException::class)
    override fun getCureById(id: Int): CureDto {
        return _cureService.getOne(id)
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    override fun saveCure(cureSaveDto: CureSaveDto): CureDto {
        return _cureService.save(cureSaveDto)
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    override fun updateCure(id: Int, updateCureSaveDto: CureSaveDto): CureDto {
        return _cureService.update(id, updateCureSaveDto)
    }

    @Throws(ResourceNotFoundException::class)
    override fun removeCure(id: Int) {
        _cureService.remove(id)
    }
}