package com.github.jaksa97.LeafSaver_Kotlin.configuration

import com.github.jaksa97.LeafSaver_Kotlin.models.entities.*
import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import com.github.jaksa97.LeafSaver_Kotlin.repositories.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.random.Random


@Configuration
class SeederConfig(
    private val _producerRepository: ProducerRepository,
    private val _drugRepository: DrugRepository,
    private val _diseaseRepository: DiseaseRepository,
    private val _cureRepository: CureRepository,
    private val _userRepository: UserRepository
) {

    val producers = mutableListOf<ProducerEntity>()
    val drugs = mutableListOf<DrugEntity>()
    val diseases = mutableListOf<DiseaseEntity>()
    val cures = mutableListOf<CureEntity>()
    val users = mutableListOf<UserEntity>()

    @Bean
    fun databaseSeeder() = ApplicationRunner {
        initProducers()
        initDrugs()
        initDiseases()
        initCures()
        initUsers()
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

    private fun initCures() {
        if (_cureRepository.count() == 0L) {
            for (i in 1..10) {
                cures.add(
                    CureEntity(
                        id = i,
                        drug = drugs[Random.nextInt(0, 6)],
                        disease = diseases[Random.nextInt(0, 5)],
                        instruction = "Instructions $i"
                    )
                )
            }
            _cureRepository.saveAll(cures)
        }
    }

    private fun initUsers() {
        if (_userRepository.count() == 0L) {
            for (i in 1..5) {
                users.add(
                    UserEntity(
                        id = i,
                        firstName = "FirstName $i",
                        lastName = "LastName $i",
                        email = "email$i@gmail.com",
                        password = "Password$i",
                        role = UserRoles.entries.shuffled().first()
                    )
                )
            }
            _userRepository.saveAll(users)
        }
    }
}