package ru.grv.testtask.data.entity.mapper

import android.content.Context
import ru.grv.testtask.data.response.book.BooksResponse
import ru.grv.testtask.data.response.profile.ProfileResponse
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

interface IBooksResponseMapper {
    fun map(response: BooksResponse): ArrayList<BookEntity>
}