package ru.grv.testtask.domain.repository

import io.reactivex.Single
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileRepository {
    fun getProfileInfo(): Single<ProfileEntity>
    fun writeProfileInfoInDb(entity: ProfileEntity?)
}