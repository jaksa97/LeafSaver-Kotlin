package com.github.jaksa97.LeafSaver_Kotlin.configuration

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DiseaseRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DrugRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.random.Random


@Configuration
class SeederConfig(
    private val _producerRepository: ProducerRepository,
    private val _drugRepository: DrugRepository,
    private val _diseaseRepository: DiseaseRepository
) {

    val producers = mutableListOf<ProducerEntity>()
    val drugs = mutableListOf<DrugEntity>()
    val diseases = mutableListOf<DiseaseEntity>()

    @Bean
    fun databaseSeeder() = ApplicationRunner {
        initProducers()
        initDrugs()
        initDiseases()
    }

    private fun initProducers() {
        if (_producerRepository.count() == 0L) {
            for (i in 1..5) {
                producers.add(
                    ProducerEntity(
                        id = i,
                        name = "Producer $i"
                    )
                )
            }
            _producerRepository.saveAll(producers)
        }
    }

    private fun initDrugs() {
        if (_drugRepository.count() == 0L) {
            for (i in 1 .. 6) {
                drugs.add(
                    DrugEntity(
                        id = i,
                        name = "Drug $i",
                        producer = producers[Random.nextInt(1, 5)],
                        description = "Drug $i description",
                    )
                )
            }
            _drugRepository.saveAll(drugs)
        }
    }

    private fun initDiseases() {
        if (_diseaseRepository.count() == 0L) {
            for (i in 1..5) {
                diseases.add(
                    DiseaseEntity(
                        id = i,
                        name = "Disease $i",
                        niceName = "Disease niceName $i"
                    )
                )
            }
            _diseaseRepository.saveAll(diseases)
        }
    }
}