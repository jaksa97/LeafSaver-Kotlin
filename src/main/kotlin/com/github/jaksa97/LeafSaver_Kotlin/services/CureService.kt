package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.BadRequestException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.CureSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.cure.isPopulate
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.CureEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.CureMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.CureRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DiseaseRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DrugRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class CureService(
    private val _cureRepository: CureRepository,
    private val _drugRepository: DrugRepository,
    private val _diseaseRepository: DiseaseRepository,
    private val _cureMapper: CureMapper
) {

    @Throws(ResourceNotFoundException::class)
    fun getOne(
        id: Int
    ): CureDto {
        val cureEntity = _cureRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.CURE)
        }

        return _cureMapper.toDto(cureEntity)
    }

    fun getAll(pageable: Pageable): Page<CureDto> = _cureRepository.findAll(pageable).map(_cureMapper::toDto)

    @Throws(ResourceNotFoundException::class)
    fun getAllByDrugId(
        drugId: Int
    ): List<CureDto> {
        if (!_drugRepository.existsById(drugId)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        return _cureRepository.findAllByDrugId(drugId).map(_cureMapper::toDto)
    }

    @Throws(ResourceNotFoundException::class)
    fun getAllByDiseaseId(
        diseaseId: Int
    ): List<CureDto> {
        if (!_diseaseRepository.existsById(diseaseId)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        return _cureRepository.findAllByDiseaseId(diseaseId).map(_cureMapper::toDto)
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class, BadRequestException::class)
    fun save(
        cureSaveDto: CureSaveDto
    ): CureDto {

        if (!cureSaveDto.isPopulate()) {
            throw BadRequestException("All params are required")
        }

        val drugEntity = _drugRepository.findById(cureSaveDto.drugId!!).orElseThrow {
                ResourceNotFoundException(ErrorInfo.ResourceType.DRUG, ("Drug with id ${cureSaveDto.drugId} don't exist"))
            }

        val diseaseEntity = _diseaseRepository.findById(cureSaveDto.diseaseId!!).orElseThrow {
                ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE, ("Disease with id ${cureSaveDto.diseaseId} don't exist"))
            }

        if (checkCure(cureSaveDto)) {
            throw UniqueViolationException(ErrorInfo.ResourceType.CURE, "Cure already exists")
        }

        val cureEntity = _cureMapper.toEntity(cureSaveDto)
        cureEntity.drug = drugEntity
        cureEntity.disease = diseaseEntity

        return _cureMapper.toDto(_cureRepository.save(cureEntity))
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun update(
        id: Int,
        updatedCure: CureSaveDto
    ): CureDto {

        val originalCureEntity = _cureRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.CURE)
        }

        updatedCure.drugId?.let {
            val drugEntity = _drugRepository.findById(it).orElseThrow {
                ResourceNotFoundException(ErrorInfo.ResourceType.DRUG, ("Drug with id $it don't exist"))
            }

            originalCureEntity.drug = drugEntity
        }

        updatedCure.diseaseId?.let {
            val diseaseEntity = _diseaseRepository.findById(it).orElseThrow {
                ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE, ("Disease with id $it don't exist"))
            }

            originalCureEntity.disease = diseaseEntity
        }

        val existingCure = _cureRepository.findByDrugIdAndDiseaseId(
            updatedCure.drugId ?: originalCureEntity.drug.id,
            updatedCure.diseaseId ?: originalCureEntity.disease.id
        )

        if (existingCure != null && existingCure.id != originalCureEntity.id) {
            throw UniqueViolationException(ErrorInfo.ResourceType.CURE, "Cure already exists")
        }

        updatedCure.instruction?.let {
            originalCureEntity.instruction = it
        }

        _cureRepository.save(originalCureEntity)

        return _cureMapper.toDto(originalCureEntity)
    }

    @Throws(ResourceNotFoundException::class)
    fun remove(
        id: Int
    ) {
        if (!_cureRepository.existsById(id)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.CURE)
        }

        _cureRepository.deleteById(id)
    }

    private fun checkCure(
        cureSaveDto: CureSaveDto
    ): Boolean {
        val cureEntitiesByDrugId = _cureRepository.findAllByDrugId(cureSaveDto.drugId!!)

        if (cureEntitiesByDrugId.isNotEmpty()) {
            for (cure: CureEntity in cureEntitiesByDrugId) {
                if (cure.disease.id == cureSaveDto.diseaseId!!) {
                    return true
                }
            }
        }

        return false
    }
}