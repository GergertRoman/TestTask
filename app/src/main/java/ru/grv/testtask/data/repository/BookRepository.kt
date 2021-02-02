package ru.grv.testtask.data.repository

import android.content.Context
import io.reactivex.Maybe
import io.reactivex.Single
import ru.grv.testtask.data.Constants
import ru.grv.testtask.data.db.TestTaskDatabase
import ru.grv.testtask.data.entity.mapper.IBooksResponseMapper
import ru.grv.testtask.data.storage.BookStorage
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.repository.IBookRepository
import javax.inject.Inject

class BookRepository@Inject constructor(
    private val storage: BookStorage,
    private val db: TestTaskDatabase,
    private val context: Context,
    private val booksResponseMapper: IBooksResponseMapper
) : IBookRepository, BaseRepository() {

    override fun getBooks(): Maybe<List<BookEntity>> {
        return db.bookDao().getAllBooks()
    }

    override fun fetchBooks(): Single<ArrayList<BookEntity>> {
        if (!isNetworkAvailable(context)) {
            definitionError(Constants.NETWORK_UNAVAILABLE_ERROR_TYPE)
        }
        return storage
            .fetchBooks()
            .map {
                if (it.data == null) {
                    definitionError(it.reason)
                }
                booksResponseMapper.map(it)
            }
    }

    override fun writeBooksListInDb(entityList: List<BookEntity?>) {
        db.bookDao().deleteAllBooks()
        db.bookDao().insertList(entityList)
    }
}