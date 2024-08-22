package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.DiseaseMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.CureRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DiseaseRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class DiseaseService(
    private val _diseaseRepository: DiseaseRepository,
    private val _cureRepository: CureRepository,
    private val _diseaseMapper: DiseaseMapper
) {
    @Throws(ResourceNotFoundException::class)
    fun getOne(id: Int): DiseaseDto {
        val diseaseEntity = _diseaseRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        return _diseaseMapper.toDto(diseaseEntity)
    }

    fun getAll(): List<DiseaseDto> = _diseaseRepository.findAll().map(_diseaseMapper::toDto)

    @Throws(UniqueViolationException::class)
    fun save(diseaseSaveDto: DiseaseSaveDto): DiseaseDto {
        if (_diseaseRepository.findByName(diseaseSaveDto.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.DISEASE, "'name' already exists")
        }

        return _diseaseMapper.toDto(_diseaseRepository.save(_diseaseMapper.toEntity(diseaseSaveDto)))
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun update(id: Int, updateDisease: DiseaseSaveDto): DiseaseDto {
        val originalDiseaseEntity = _diseaseRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        if (originalDiseaseEntity.name != updateDisease.name && _diseaseRepository.findByName(updateDisease.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.DISEASE, "'name' already exists")
        }

        val diseaseEntity = _diseaseMapper.toEntity(updateDisease)
        diseaseEntity.id = originalDiseaseEntity.id

        _diseaseRepository.save(diseaseEntity)

        return _diseaseMapper.toDto(diseaseEntity)
    }

    @Transactional
    @Throws(ResourceNotFoundException::class)
    fun remove(id: Int) {

        val diseaseEntity = _diseaseRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DISEASE)
        }

        _cureRepository.deleteAllByDisease(diseaseEntity)

        _diseaseRepository.deleteById(id)
    }
}