package ru.grv.testtask.data.repository

import io.reactivex.Observable
import ru.grv.testtask.domain.entity.BookEntity

interface IBookRepository {
    fun getBooks(): Observable<List<BookEntity>>
    fun writeBooksListInDb(entityList: List<BookEntity?>)
    fun fetchBooksFromDb(): Observable<List<BookEntity>>
}