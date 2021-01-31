package ru.grv.testtask.data.storage

import io.reactivex.Single
import ru.grv.testtask.data.response.book.BooksResponse
import ru.grv.testtask.data.submitter.BookSubmitter
import javax.inject.Inject

class BookStorage @Inject constructor() {
    fun fetchBooks(): Single<BooksResponse> {
        return BookSubmitter().single
    }
}