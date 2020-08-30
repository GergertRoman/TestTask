package ru.grv.testtask.data.response.book

data class Author (
    var id: Int,
    var first_name: String,
    var last_name: String,
    val birth_date: String
)