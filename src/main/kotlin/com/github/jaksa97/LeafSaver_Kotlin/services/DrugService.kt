package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.DrugMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DrugRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class DrugService(
    private val _drugRepository: DrugRepository,
    private val _producerRepository: ProducerRepository,
    private val _drugMapper: DrugMapper
) {
    @Throws(ResourceNotFoundException::class)
    fun getOne(id: Int): DrugDto {
        val drugEntity = _drugRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        return _drugMapper.toDto(drugEntity)
    }

    @Throws(ResourceNotFoundException::class)
    fun getAllByProducerId(producerId: Int): List<DrugDto> {
        if (!_producerRepository.existsById(producerId)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER, "Producer with id $producerId don't exist")
        }

        return _drugRepository.findAllByProducerId(producerId).map(_drugMapper::toDto)
    }

    fun getAll(): List<DrugDto> = _drugRepository.findAll().map(_drugMapper::toDto)

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun save(drugSaveDto: DrugSaveDto): DrugDto {
        if (_drugRepository.findByName(drugSaveDto.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.DRUG, "'name' already exists")
        }

        if (_producerRepository.existsById(drugSaveDto.producerId)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER, "Producer with id ${drugSaveDto.producerId} don't exist")
        }

        val drugEntity = _drugMapper.toEntity(drugSaveDto)
        val producerEntity = _producerRepository.findById(drugSaveDto.producerId).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        drugEntity.producer = producerEntity

        return _drugMapper.toDto(_drugRepository.save(drugEntity))
    }

    @Throws(UniqueViolationException::class, ResourceNotFoundException::class)
    fun update(id: Int, updateDrug: DrugSaveDto): DrugDto {
        val originalDrugEntity = _drugRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        if (originalDrugEntity.name != updateDrug.name && _drugRepository.findByName(updateDrug.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.DRUG, "'name' already exists")
        }

        val drugEntity = _drugMapper.toEntity(updateDrug)
        val producerEntity = _producerRepository.findById(updateDrug.producerId).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        drugEntity.producer = producerEntity

        drugEntity.id = id

        _drugRepository.save(drugEntity)

        return _drugMapper.toDto(drugEntity)
    }

    @Throws(ResourceNotFoundException::class)
    fun remove(id: Int) {
        if (!_drugRepository.existsById(id)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.DRUG)
        }

        _drugRepository.deleteById(id)
    }
}