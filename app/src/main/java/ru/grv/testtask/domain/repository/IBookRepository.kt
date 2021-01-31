package ru.grv.testtask.domain.repository

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.grv.testtask.domain.entity.BookEntity

interface IBookRepository {
    fun getBooks(): Single<List<BookEntity>>
    //fun writeBooksListInDb(entityList: List<BookEntity?>)
    //fun fetchBooksFromDb(): Maybe<List<BookEntity>>
}