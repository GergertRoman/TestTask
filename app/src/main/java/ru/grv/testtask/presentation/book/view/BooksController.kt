package ru.grv.testtask.presentation.book.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_book.view.*
import ru.grv.testtask.R
import ru.grv.testtask.data.Constants
import ru.grv.testtask.domain.entity.BookEntity
import ru.grv.testtask.common.mvp.BaseMvpController
import ru.grv.testtask.common.ToolbarManager

class BooksController(bundle: Bundle) : BaseMvpController(bundle) {

    override fun getLayoutId() = R.layout.activity_book

    private lateinit var adapter: BookAdapter

    // region Bundle Args
    // =============================================================================================
    private val bookList: ArrayList<BookEntity> by lazy {
        args.getSerializable(Constants.BOOKS_KEY) as ArrayList<BookEntity>
    }
    // =============================================================================================
    // endregion Bundle Args


    // region Lifecycle
    // =============================================================================================
    private fun onPostViewCreated(view: View) {
        setupRecycler(view)
        updateRecycler()
    }
    // =============================================================================================
    // endregion Lifecycle


    // region private
    // =============================================================================================
    private fun setupRecycler(view: View) {
        adapter = BookAdapter()
        val layoutManager = LinearLayoutManager(applicationContext)
        val dividerItemDecoration =
            DividerItemDecoration(
                view.rvBooks?.context,
                layoutManager.orientation
            )
        with(view) {
            rvBooks?.addItemDecoration(dividerItemDecoration)
            rvBooks?.layoutManager = layoutManager
            rvBooks?.itemAnimator = DefaultItemAnimator()
            rvBooks?.adapter = adapter
        }
    }

    private fun updateRecycler() {
        adapter.update(bookList)
    }
    // =============================================================================================
    // endregion private


    // region BaseController overrides
    // =============================================================================================
    override fun initializeInjector() {
        getComponentManager()?.getBooksComponent()?.inject(this)
    }

    override fun clearInjector() {
        getComponentManager()?.clearBooksComponent()
    }

    override fun onCreated() {
        view?.let {
            onPostViewCreated(it)
        }
    }

    override fun getTitle() = resources?.getString(R.string.title_book_screen)

    override fun getIconType() = ToolbarManager.IconType.BACK

    override fun initState() = STATE.DEFAULT
    // =============================================================================================
    // endregion BaseController overrides
}