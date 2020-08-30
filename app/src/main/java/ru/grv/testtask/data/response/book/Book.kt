package ru.grv.testtask.data.response.book

data class Book (
    var id: Int,
    var name: String,
    var authors: ArrayList<Int>,
    val image_url: String
)