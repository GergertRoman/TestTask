package ru.grv.testtask.domain.repository

import io.reactivex.Maybe
import io.reactivex.Single
import ru.grv.testtask.domain.entity.BookEntity

interface IBookRepository {
    fun getBooks(): Maybe<List<BookEntity>>
    fun fetchBooks(): Single<ArrayList<BookEntity>>
    fun writeBooksListInDb(entityList: List<BookEntity?>)
}