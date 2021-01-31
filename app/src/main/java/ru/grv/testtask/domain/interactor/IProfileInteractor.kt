package ru.grv.testtask.domain.interactor

import io.reactivex.Single
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileInteractor {
    fun getProfileInfo(): Single<ProfileEntity>
    fun getBooks(): Single<List<BookEntity>>
}