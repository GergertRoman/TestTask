package ru.grv.testtask.presentation.profile.presenter

interface IProfilePresenter {
    fun getProfileInfo()
    fun getBooks()
    fun onDestroy()
    fun chooseCountReadBook()
}