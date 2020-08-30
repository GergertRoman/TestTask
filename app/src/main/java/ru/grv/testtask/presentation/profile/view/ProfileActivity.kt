package ru.grv.testtask.presentation.profile.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_profile.*
import ru.grv.testtask.Constants.BOOKS_KEY
import ru.grv.testtask.R
import ru.grv.testtask.di.DaggerWrapper
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.domain.entity.ProfileEntity
import ru.grv.testtask.presentation.book.view.BookActivity
import ru.grv.testtask.presentation.profile.presenter.IProfilePresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

const val DATE_FORMAT_SHORT_SERVER = "yyyy-MM-dd"

private val sServerShortFormat =
    SimpleDateFormat(DATE_FORMAT_SHORT_SERVER, Locale.US)

// можно получить корректные данные по профилю и списку книг.
const val BOOKS_PROFILE_TOKEN = "ziPZ63IYeuRiHz2ytNlzDw0h98Ve6A7I"

// можно получить профиль с незаполненными данными и списком книг из одной книги.
const val EMPTY_PROFILE_TOKEN = "hldktIc3tiGx3Tu6UlhpXNH993u8vifT"

// можно получить профиль с заполненными данными и с пустым списком книг.
const val EMPTY_BOOKS_TOKEN = "RDhi7k79GSmdZcW4Gg7X0sUNUbYQZsxF"

// можно получить ошибку token expired.
const val ERROR_TOKEN = "xfTV94kfsVFyxPfvTg55aDx_jxvrNUBg"

// можно получить ошибку internal backend error.
const val BACKEND_ERROR_TOKEN = "LbwCgZvvlFO2ydRK5BAfau2elUYnauNT"


const val TOKEN = BOOKS_PROFILE_TOKEN

class ProfileActivity: AppCompatActivity(), IProfileActivity, SwipeRefreshLayout.OnRefreshListener,
View.OnClickListener {

    private val layout = R.layout.activity_profile


    // region Fields
    //----------------------------------------------------------------------------------------------
    private var toolbar: Toolbar? = null
    private var countReadBook = 0

    @Inject
    lateinit var presenter: IProfilePresenter

    @Inject
    fun setViewInPresenter() {
        presenter.setView(this)
    }
    //----------------------------------------------------------------------------------------------
    // endregion Fields


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        initializeInjector()
        initializerStetho()
        configureView()
        initToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        clearInjector()
    }


    // region Dagger
    //----------------------------------------------------------------------------------------------
    private fun clearInjector() {
        DaggerWrapper.getComponentManager(applicationContext)?.clearProfileComponent()
    }

    private fun initializeInjector() {
        DaggerWrapper.getComponentManager(applicationContext)?.getProfileComponent()?.inject(this)
    }
    //----------------------------------------------------------------------------------------------
    // endregion Dadder


    // region Interface for Presenter Imp
    //----------------------------------------------------------------------------------------------
    override fun updateProfileInfo(entity: ProfileEntity?) {
        swipeContainer?.isRefreshing = false
        if (entity != null) {
            name?.setDescription(entity.firstName)
            surname?.setDescription(entity.lastName)
            dateOfBirth?.setDescription(entity.birthDate)
            city?.setDescription(entity.city)
            gender?.setDescription(entity.gender)
            email?.setDescription(entity.email)
            phone?.setDescription(entity.phoneNumber)
        }
    }

    override fun showCountReadBook(count: Int) {
        countReadBook = count
        countOfBooksRead?.setDescription(count.toString())
    }

    override fun openActivityBook(bookList: ArrayList<BookEntity>) {
        val context = applicationContext
        val intent = Intent(context, BookActivity::class.java)
        intent.putExtra(BOOKS_KEY, bookList)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun showErrorAlert(title: String, message: String?) {
        swipeContainer?.isRefreshing = false
        if (message != null) {
            ru.grv.testtask.AlertFunctions(
                activity = this,
                title = title,
                message = message,
                buttonsType = ru.grv.testtask.AlertFunctions.BUTTONS.OK,
                okButtonResId = R.string.ok
            ).show()
        }
    }
    //----------------------------------------------------------------------------------------------
    // endregion Interface for Presenter Imp


    // region private
    //----------------------------------------------------------------------------------------------
    private fun configureView() {
        swipeContainer.setOnRefreshListener(this)
        countOfBooksRead.setOnClickListener(this)
    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar?.title = this.getString(R.string.title_profile_screen)
    }

    private fun fetchDataFromDb() {
        presenter.fetchProfileInfoFromDb()
        presenter.fetchBooksFromDb()
    }

    private fun initializerStetho() {
        // Create an InitializerBuilder
        val initializerBuilder = Stetho.newInitializerBuilder(this)

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
            Stetho.defaultInspectorModulesProvider(this)
        )

        // Enable command line interface
        initializerBuilder.enableDumpapp(
            Stetho.defaultDumperPluginsProvider(this)
        )

        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo?
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
    //----------------------------------------------------------------------------------------------
    // endregion private


    override fun onRefresh() {
        if (isNetworkAvailable(applicationContext)) {
            swipeContainer?.isRefreshing = true
            presenter.loadBooks()
            presenter.loadProfileInfo()
        } else {
            fetchDataFromDb()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            countOfBooksRead -> {
                if (countReadBook > 0) {
                    presenter.chooseCountReadBook()
                } else {
                    showErrorAlert(title = this.getString(R.string.empty_read_books))
                }
            }
        }
    }
}