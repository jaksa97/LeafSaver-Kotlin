package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.BadRequestException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.isPopulate
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.DrugMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.CureRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DrugRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class DrugService(
    private val _drugRepository: DrugRepository,
    private val _producerRepository: ProducerRepository,
    private val _cureRepository: CureRepository,
    private val _drugMapper: DrugMapper
) {

    @Throws(ResourceNotFoundException::class)
    fun getOne(
        id: Int
    ): DrugDto {
        val drugEntity = _drugRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        return _drugMapper.toDto(drugEntity)
    }

    @Throws(ResourceNotFoundException::class)
    fun getAllByProducerId(
        producerId: Int
    ): List<DrugDto> {
        if (!_producerRepository.existsById(producerId)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER, "Producer with id $producerId don't exist")
        }

        return _drugRepository.findAllByProducerId(producerId).map(_drugMapper::toDto)
    }

    fun getAll(name: String?, pageable: Pageable): Page<DrugDto> = _drugRepository.findAll(name, pageable).map(_drugMapper::toDto)

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class, BadRequestException::class)
    fun save(
        drugSaveDto: DrugSaveDto
    ): DrugDto {

        if (!drugSaveDto.isPopulate()) {
            throw BadRequestException("All params all required")
        }

        if (_drugRepository.findByName(drugSaveDto.name!!).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.DRUG, "'name' already exists")
        }

        val drugEntity = _drugMapper.toEntity(drugSaveDto)
        val producerEntity = _producerRepository.findById(drugSaveDto.producerId!!).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER, "Producer with id ${drugSaveDto.producerId} don't exist")
        }

        drugEntity.producer = producerEntity

        return _drugMapper.toDto(_drugRepository.save(drugEntity))
    }

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun update(
        id: Int,
        updateDrug: DrugSaveDto
    ): DrugDto {
        val originalDrugEntity = _drugRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        updateDrug.name?.let {
            if (originalDrugEntity.name != updateDrug.name && _drugRepository.findByName(it).isPresent) {
                throw UniqueViolationException(ErrorInfo.ResourceType.DRUG, "'name' already exists")
            }

            originalDrugEntity.name = it
        }

        updateDrug.producerId?.let {
            val producerEntity = _producerRepository.findById(it).orElseThrow {
                ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
            }

            originalDrugEntity.producer = producerEntity
        }

        updateDrug.description?.let {
            originalDrugEntity.description = it
        }

        _drugRepository.save(originalDrugEntity)

        return _drugMapper.toDto(originalDrugEntity)
    }

    @Transactional
    @Throws(ResourceNotFoundException::class)
    fun remove(
        id: Int
    ) {
        val drugEntity = _drugRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        _cureRepository.deleteAllByDrug(drugEntity)

        _drugRepository.deleteById(id)
    }
}