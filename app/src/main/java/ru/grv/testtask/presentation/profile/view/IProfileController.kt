package ru.grv.testtask.presentation.profile.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity

@StateStrategyType(OneExecutionStateStrategy::class)
interface IProfileController: MvpView {
    fun updateProfileInfo(entity: ProfileEntity?)
    fun showCountReadBook(count: Int)
    fun openActivityBook(bookList: ArrayList<BookEntity>)
    fun showErrorAlert(title: String, message: String? = "")
    fun setProgressBarVisibility(visible: Boolean)
}