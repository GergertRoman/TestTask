package ru.grv.testtask.data.repository

import android.content.Context
import io.reactivex.Observable
import ru.grv.testtask.R
import ru.grv.testtask.data.db.TestTaskDatabase
import ru.grv.testtask.data.response.book.BooksResponse
import ru.grv.testtask.data.storage.BookStorage
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.repository.IBookRepository
import javax.inject.Inject

class BookRepository@Inject constructor(
    private val storage: BookStorage,
    private val db: TestTaskDatabase,
    private val context: Context
) : IBookRepository, BaseRepository() {
    override fun getBooks(): Observable<List<BookEntity>> {
        return storage
            .fetchBooks()
            .map {
                if (it.data == null) {
                    definitionError(it.reason)
                }
                extractBooksFromResponse(it)
            }
    }

    override fun writeBooksListInDb(entityList: List<BookEntity?>) {
        db.bookDao().insertList(entityList)
    }

    override fun fetchBooksFromDb(): Observable<List<BookEntity>> {
        return db.bookDao().getAllBooks()
    }

    private fun extractBooksFromResponse(response: BooksResponse): ArrayList<BookEntity> {
        val booksList = arrayListOf<BookEntity>()
        val booksResponse = response.data
        var authorsName = ""

        for (book in booksResponse?.books!!) {
            authorsName = ""
            val authorsText = if (book.authors.size > 1) {
                context.getText(R.string.authors)
            } else {
                context.getText(R.string.author)
            }

            for (authorIndex in book.authors.indices) {
                for (author in booksResponse.authors!!) {
                    if (author.id == book.authors[authorIndex]) {
                        authorsName = if (authorIndex == 0) {
                            ("$authorsName${author.first_name} ${author.last_name} ")
                        } else {
                            ("$authorsName, ${author.first_name} ${author.last_name} ")
                        }
                    }
                }
            }
            booksList.add(
                BookEntity(
                    bookId = book.id,
                    nameBook = book.name,
                    imageBookUrl = book.image_url ?: "",
                    authors = "$authorsText $authorsName"
                )
            )
        }
        return booksList
    }
}