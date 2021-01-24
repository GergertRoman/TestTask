package ru.grv.testtask.presentation.profile.presenter

import ru.grv.testtask.presentation.profile.view.IProfileView

interface IProfilePresenter {
    fun loadProfileInfo()
    fun loadBooks()
    fun onDestroy()
    fun setView(view: IProfileView)
    fun chooseCountReadBook()
    fun fetchProfileInfoFromDb()
    fun fetchBooksFromDb()
}