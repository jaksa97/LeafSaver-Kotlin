package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.BadRequestException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.isPopulate
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.DiseaseMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.CureRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DiseaseRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class DiseaseService(
    private val _diseaseRepository: DiseaseRepository,
    private val _cureRepository: CureRepository,
    private val _diseaseMapper: DiseaseMapper
) {
    @Throws(ResourceNotFoundException::class)
    fun getOne(
        id: Int
    ): DiseaseDto {
        val diseaseEntity = _diseaseRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        return _diseaseMapper.toDto(diseaseEntity)
    }

    fun getAll(name: String?, pageable: Pageable): Page<DiseaseDto> = _diseaseRepository.findAll(name, pageable).map(_diseaseMapper::toDto)

    @Throws(UniqueViolationException::class, BadRequestException::class)
    fun save(
        diseaseSaveDto: DiseaseSaveDto
    ): DiseaseDto {

        if (!diseaseSaveDto.isPopulate()) {
            throw BadRequestException("All params all required")
        }

        if (_diseaseRepository.findByName(diseaseSaveDto.name!!).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.DISEASE, "'name' already exists")
        }

        return _diseaseMapper.toDto(_diseaseRepository.save(_diseaseMapper.toEntity(diseaseSaveDto)))
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun update(
        id: Int,
        updateDisease: DiseaseSaveDto
    ): DiseaseDto {
        val originalDiseaseEntity = _diseaseRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        updateDisease.name?.let {
            if (originalDiseaseEntity.name != updateDisease.name && _diseaseRepository.findByName(it).isPresent) {
                throw UniqueViolationException(ErrorInfo.ResourceType.DISEASE, "'name' already exists")
            }

            originalDiseaseEntity.name = it
        }

        updateDisease.niceName?.let {
            originalDiseaseEntity.niceName = it
        }

        _diseaseRepository.save(originalDiseaseEntity)

        return _diseaseMapper.toDto(originalDiseaseEntity)
    }

    @Transactional
    @Throws(ResourceNotFoundException::class)
    fun remove(
        id: Int
    ) {

        val diseaseEntity = _diseaseRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        _cureRepository.deleteAllByDisease(diseaseEntity)

        _diseaseRepository.deleteById(id)
    }
}