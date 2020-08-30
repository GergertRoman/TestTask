package ru.grv.testtask.data.storage

import io.reactivex.Observable
import ru.grv.testtask.data.response.book.BooksResponse
import ru.grv.testtask.data.submitter.BookSubmitter
import javax.inject.Inject

class BookStorage @Inject constructor() {
    fun fetchBooks(): Observable<BooksResponse> {
        return BookSubmitter().observable
    }
}