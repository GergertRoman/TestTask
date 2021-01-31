package ru.grv.testtask.presentation.profile.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.grv.testtask.Constants.BOOK_TAG
import ru.grv.testtask.Constants.DATA_BASE_TAG
import ru.grv.testtask.Constants.DATA_NOT_FOUND
import ru.grv.testtask.Constants.ERROR
import ru.grv.testtask.Constants.INTERNAL_BACKEND_ERROR
import ru.grv.testtask.Constants.NETWORK_UNAVAILABLE_ERROR
import ru.grv.testtask.Constants.PROFILE_TAG
import ru.grv.testtask.Constants.TOKEN_EXPIRED
import ru.grv.testtask.data.exception.InternalBackendException
import ru.grv.testtask.data.exception.NetworkUnavailableException
import ru.grv.testtask.data.exception.TokenExpiredException
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.presentation.profile.view.IProfileController
import ru.grv.testtask.domain.interactor.IProfileInteractor
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val interactor: IProfileInteractor
): MvpPresenter<IProfileController>(), IProfilePresenter {

    var bookList = arrayListOf<BookEntity>()
    var isShowAlertTokenExpired = true
    var isShowAlertInternalBackend = true
    private var dispose = CompositeDisposable()

    override fun chooseCountReadBook() {
        viewState.openActivityBook(bookList)
    }


    // region Rx
    //----------------------------------------------------------------------------------------------
    override fun getProfileInfo() {
        isShowAlertTokenExpired = true
        isShowAlertInternalBackend = true
        dispose.add(interactor.getProfileInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateProfileInfo(it)
                getBooks()
                Log.d(PROFILE_TAG, "success")
            }, {
                viewState.setProgressBarVisibility(false)
                handlingCommonErrors(it)
                Log.d(PROFILE_TAG, "Error ⭕️")
            })
        )
    }

    override fun getBooks() {
        isShowAlertTokenExpired = true
        isShowAlertInternalBackend = true
        dispose.add(interactor.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.setProgressBarVisibility(false)
                this.bookList = it as ArrayList<BookEntity>
                viewState.showCountReadBook(it.size)
                Log.d(BOOK_TAG, "success")
            }, {
                viewState.setProgressBarVisibility(false)
                handlingCommonErrors(it)
                Log.d(BOOK_TAG, "Error ⭕️")
            })
        )
    }
    //----------------------------------------------------------------------------------------------
    // endregion Rx


    override fun onDestroy() {
        dispose.dispose()
    }

    private fun handlingCommonErrors(e: Throwable) {
        when (e) {
            is TokenExpiredException -> {
                if (isShowAlertTokenExpired) {
                    viewState.showErrorAlert(
                        ERROR,
                        TOKEN_EXPIRED
                    )
                    isShowAlertTokenExpired = false
                }
            }
            is InternalBackendException -> {
                if (isShowAlertInternalBackend) {
                    viewState.showErrorAlert(
                        ERROR,
                        INTERNAL_BACKEND_ERROR
                    )
                    isShowAlertInternalBackend = false
                }
            }
            is NetworkUnavailableException -> {
                viewState.showErrorAlert(
                    ERROR,
                    NETWORK_UNAVAILABLE_ERROR
                )
            }
            else -> {
                viewState.showErrorAlert(ERROR, DATA_NOT_FOUND)
            }
        }
    }
}