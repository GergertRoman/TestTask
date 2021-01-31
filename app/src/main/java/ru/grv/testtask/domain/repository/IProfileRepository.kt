package ru.grv.testtask.domain.repository

import io.reactivex.Maybe
import io.reactivex.Single
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileRepository {
    fun getProfileInfo(): Maybe<ProfileEntity>
    fun fetchProfileInfo(): Single<ProfileEntity>
    fun writeProfileInfoInDb(entity: ProfileEntity)
}