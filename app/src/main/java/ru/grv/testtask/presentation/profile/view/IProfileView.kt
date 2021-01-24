package ru.grv.testtask.presentation.profile.view

import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

interface IProfileView {
    fun updateProfileInfo(entity: ProfileEntity?)
    fun showCountReadBook(count: Int)
    fun openActivityBook(bookList: ArrayList<BookEntity>)
    fun showErrorAlert(title: String, message: String? = "")
}