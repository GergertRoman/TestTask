package ru.grv.testtask.presentation.profile.presenter

import ru.grv.testtask.presentation.profile.view.IProfileActivity

interface IProfilePresenter {
    fun loadProfileInfo()
    fun loadBooks()
    fun onDestroy()
    fun setView(activity: IProfileActivity)
    fun chooseCountReadBook()
    fun fetchProfileInfoFromDb()
    fun fetchBooksFromDb()
}