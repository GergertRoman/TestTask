package ru.grv.testtask.presentation.profile.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.grv.testtask.Constants.BOOK_TAG
import ru.grv.testtask.Constants.DATA_BASE_TAG
import ru.grv.testtask.Constants.DATA_NOT_FOUND
import ru.grv.testtask.Constants.ERROR
import ru.grv.testtask.Constants.INTERNAL_BACKEND_ERROR
import ru.grv.testtask.Constants.PROFILE_TAG
import ru.grv.testtask.Constants.TOKEN_EXPIRED
import ru.grv.testtask.data.repository.InternalBackendException
import ru.grv.testtask.data.repository.TokenExpiredException
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.presentation.profile.view.IProfileActivity
import ru.grv.testtask.domain.interactor.IProfileInteractor
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private val interactor: IProfileInteractor
): IProfilePresenter {
    lateinit var activity: IProfileActivity
    var bookList = arrayListOf<BookEntity>()
    var isShowAlertTokenExpired = true
    var isShowAlertInternalBackend = true
    private var dispose = CompositeDisposable()

    override fun setView(activity: IProfileActivity) {
        this.activity = activity
    }

    override fun chooseCountReadBook() {
        activity.openActivityBook(bookList)
    }


    // region Rx
    //----------------------------------------------------------------------------------------------
    private fun writeProfileInfoInDb(entity: ProfileEntity?) {
        CoroutineScope(Dispatchers.IO).launch {
            interactor.writeProfileInfoInDb(entity)
        }
    }

    private fun writeBooksListInDb(entityList: List<BookEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            interactor.writeBooksListInDb(entityList)
        }
    }

    override fun fetchProfileInfoFromDb() {
        dispose.add(interactor.fetchProfileInfoFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                activity.updateProfileInfo(it)
                Log.d(DATA_BASE_TAG, "Rx: A profile of the received base")
            }, {
                Log.d(DATA_BASE_TAG, "Rx: Read error profile")
            })
        )
    }

    override fun fetchBooksFromDb() {
        dispose.add(interactor.fetchBooksFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.bookList = it as ArrayList<BookEntity>
                activity.showCountReadBook(it.size)
                Log.d(DATA_BASE_TAG, "Rx: A books of the received base")
            }, {
                Log.d(DATA_BASE_TAG, "Rx: Read error books")
            })
        )
    }

    override fun loadProfileInfo() {
        isShowAlertTokenExpired = true
        isShowAlertInternalBackend = true
        dispose.add(interactor.getProfileInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                activity.updateProfileInfo(it)
                writeProfileInfoInDb(it)
                Log.d(PROFILE_TAG, "success")
            }, {
                handlingCommonErrors(it)
                Log.d(PROFILE_TAG, "Error ⭕️")
            })
        )
    }

    override fun loadBooks() {
        isShowAlertTokenExpired = true
        isShowAlertInternalBackend = true
        dispose.add(interactor.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.bookList = it as ArrayList<BookEntity>
                activity.showCountReadBook(it.size)
                writeBooksListInDb(it)
                Log.d(BOOK_TAG, "success")
            }, {
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
                    activity.showErrorAlert(
                        ERROR,
                        TOKEN_EXPIRED
                    )
                    isShowAlertTokenExpired = false
                }
            }
            is InternalBackendException -> {
                if (isShowAlertInternalBackend) {
                    activity.showErrorAlert(
                        ERROR,
                        INTERNAL_BACKEND_ERROR
                    )
                    isShowAlertInternalBackend = false
                }
            }
            else -> {
                activity.showErrorAlert(ERROR, DATA_NOT_FOUND)
            }
        }
    }
}