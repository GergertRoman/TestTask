package ru.grv.testtask.domain.repository

import io.reactivex.Observable
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileRepository {
    fun getProfileInfo(): Observable<ProfileEntity>
    fun writeProfileInfoInDb(entity: ProfileEntity?)
    fun fetchProfileInfoFromDb(): Observable<ProfileEntity>
}