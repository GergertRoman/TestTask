package ru.grv.testtask.presentation

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.controller_profile.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.grv.testtask.Constants
import ru.grv.testtask.R
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.presentation.book.view.BookView
import ru.grv.testtask.presentation.profile.BaseController
import ru.grv.testtask.presentation.profile.presenter.ProfilePresenter
import ru.grv.testtask.presentation.profile.view.IProfileController
import javax.inject.Inject


class ProfileController :
    BaseController(),
    IProfileController,
    SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener
{
    override fun getLayoutId() = R.layout.controller_profile

    // region Fields
    //----------------------------------------------------------------------------------------------
    private var countReadBook = 0

    @Inject
    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return presenter
    }
    //----------------------------------------------------------------------------------------------
    // endregion Fields


    // region Lifecycle
    // =============================================================================================
    private fun onPostViewCreated(view: View) {
        initToolbar()
        configureView(view)
        setProgressBarVisibility(true)
        presenter.getProfileInfo()
    }
    // =============================================================================================
    // endregion Lifecycle


    // region private
    //----------------------------------------------------------------------------------------------
    private fun configureView(view: View) {
        view.swipeContainer?.setOnRefreshListener(this)
        view.countOfBooksRead?.setOnClickListener(this)
    }

    private fun initToolbar() {
        val toolbar = (view?.toolbar as Toolbar)
        toolbar.title = resources?.getString(R.string.title_profile_screen)
    }
    // =============================================================================================
    // endregion View

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
        val context = applicationContext
        val intent = Intent(context, BookView::class.java)
        intent.putExtra(Constants.BOOKS_KEY, bookList)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }

    override fun showErrorAlert(title: String, message: String?) {
        view?.swipeContainer?.isRefreshing = false
        if (message != null) {
            activity?.let {
                ru.grv.testtask.AlertFunctions(
                    activity = it,
                    title = title,
                    message = message,
                    buttonsType = ru.grv.testtask.AlertFunctions.BUTTONS.OK,
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

    override fun onClick(v: View?) {
        when (v) {
            view?.countOfBooksRead -> {
                if (countReadBook > 0) {
                    presenter.chooseCountReadBook()
                } else {
                    resources?.getString(R.string.empty_read_books)?.let { showErrorAlert(title = it) }
                }
            }
        }
    }
}