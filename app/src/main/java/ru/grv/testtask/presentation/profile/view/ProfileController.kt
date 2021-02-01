package ru.grv.testtask.presentation.profile.view

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.controller_profile.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.grv.testtask.data.Constants
import ru.grv.testtask.data.Constants.BOOKS_PROFILE_TOKEN
import ru.grv.testtask.data.Constants.DEFAULT_STRING
import ru.grv.testtask.R
import ru.grv.testtask.common.AlertFunctions
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.common.mvp.BaseMvpController
import ru.grv.testtask.common.ToolbarManager
import ru.grv.testtask.presentation.book.view.BooksController
import ru.grv.testtask.presentation.profile.presenter.ProfilePresenter
import javax.inject.Inject
import kotlin.collections.ArrayList

const val TOKEN = BOOKS_PROFILE_TOKEN

class ProfileController :
    BaseMvpController(),
    IProfileController,
    SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener
{
    override fun getLayoutId() = R.layout.controller_profile


    // region Fields
    // =============================================================================================
    private var countReadBook = 0

    @Inject
    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return presenter
    }
    // =============================================================================================
    // endregion Fields


    // region Lifecycle
    // =============================================================================================
    private fun onPostViewCreated(view: View) {
        configureView(view)
        presenter.getProfileInfo()
    }
    // =============================================================================================
    // endregion Lifecycle


    // region private
    // =============================================================================================
    private fun configureView(view: View) {
        view.swipeContainer?.setOnRefreshListener(this)
        view.countOfBooksRead?.setOnClickListener(this)
    }
    // =============================================================================================
    // endregion View


    override fun updateProfileInfo(entity: ProfileEntity?) {
        view?.swipeContainer?.isRefreshing = false
        if (entity != null) {
            view?.apply {
                name?.setDescription(entity.firstName)
                surname?.setDescription(entity.lastName)
                dateOfBirth?.setDescription(entity.birthDate)
                city?.setDescription(entity.city)
                gender?.setDescription(entity.gender)
                email?.setDescription(entity.email)
                phone?.setDescription(entity.phoneNumber)
            }
        }
    }

    override fun showCountReadBook(count: Int) {
        countReadBook = count
        view?.countOfBooksRead?.setDescription(count.toString())
    }

    override fun openActivityBook(bookList: ArrayList<BookEntity>) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.BOOKS_KEY, bookList)
        val controller = BooksController(bundle)
        pushController(controller, DEFAULT_STRING)
    }

    override fun showErrorAlert(title: String, message: String?) {
        view?.swipeContainer?.isRefreshing = false
        if (message != null) {
            activity?.let {
                AlertFunctions(
                    activity = it,
                    title = title,
                    message = message,
                    buttonsType = AlertFunctions.BUTTONS.OK,
                    okButtonResId = R.string.ok
                ).show()
            }
        }
    }

    override fun setProgressBarVisibility(visible: Boolean) {
        if(visible) {
            setState(STATE.LOADING)
        } else {
            setState(STATE.DEFAULT)
        }
    }

    override fun onRefresh() {
        view?.swipeContainer?.isRefreshing = false
        presenter.getProfileInfo()
    }

    override fun onClick(view: View?) {
        when (view) {
            view?.countOfBooksRead -> {
                if (countReadBook > 0) {
                    presenter.chooseCountReadBook()
                } else {
                    resources?.getString(R.string.empty_read_books)?.let {
                        showErrorAlert(title = it)
                    }
                }
            }
        }
    }


    // region BaseController overrides
    // =============================================================================================
    override fun initializeInjector() {
        getComponentManager()?.getProfileComponent()?.inject(this)
    }

    override fun clearInjector() {
        getComponentManager()?.clearProfileComponent()
    }

    override fun onCreated() {
        view?.let {
            onPostViewCreated(it)
        }
    }

    override fun getTitle() = resources?.getString(R.string.title_profile_screen)

    override fun getIconType() = ToolbarManager.IconType.NONE

    override fun initState() = STATE.LOADING
    // =============================================================================================
    // endregion BaseController overrides
}