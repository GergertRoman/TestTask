package ru.grv.testtask.domain.interactor

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileInteractor {
    fun getProfileInfo(): Single<ProfileEntity>
    fun getBooks(): Single<List<BookEntity>>
    fun writeProfileInfoInDb(entity: ProfileEntity?)
    fun writeBooksListInDb(entityList: List<BookEntity?>)
}