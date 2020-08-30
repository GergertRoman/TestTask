package ru.grv.testtask.data.response.book

data class BooksResponse(
    var data: BooksResponseData? = null,
    var status: String? = null,
    var reason: String? = null
)