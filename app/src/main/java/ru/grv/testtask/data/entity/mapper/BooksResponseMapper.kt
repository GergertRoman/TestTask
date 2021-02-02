package ru.grv.testtask.data.entity.mapper

import ru.grv.testtask.data.response.book.BooksResponse
import ru.grv.testtask.domain.entity.BookEntity
import javax.inject.Inject
import kotlin.collections.ArrayList

class BooksResponseMapper @Inject constructor() : IBooksResponseMapper {
    override fun map(response: BooksResponse): ArrayList<BookEntity> {
        val booksList = arrayListOf<BookEntity>()
        val booksResponse = response.data
        var authorsName = ""

        for (book in booksResponse?.books!!) {
            authorsName = ""
            val authorsText = if (book.authors.size > 1) {
                "Авторы: "
            } else {
                "Автор: "
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