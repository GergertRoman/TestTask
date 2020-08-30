package ru.grv.testtask.domain.interactor

import io.reactivex.Observable
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileInteractor {
    fun getProfileInfo(): Observable<ProfileEntity>
    fun getBooks(): Observable<List<BookEntity>>
    fun writeProfileInfoInDb(entity: ProfileEntity?)
    fun fetchProfileInfoFromDb(): Observable<ProfileEntity>
    fun writeBooksListInDb(entityList: List<BookEntity?>)
    fun fetchBooksFromDb(): Observable<List<BookEntity>>
}