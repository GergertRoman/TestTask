package ru.grv.testtask.data.response.book

data class BooksResponseData (
    var books: List<Book>? = null,
    var authors: List<Author>? = null
)