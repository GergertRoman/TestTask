package ru.grv.testtask.presentation.book.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_book.*
import ru.grv.testtask.Constants.BOOKS_KEY
import ru.grv.testtask.R
import ru.grv.testtask.domain.entity.BookEntity

class BookActivity: AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        initToolbar()
        val booksList = intent.getSerializableExtra(BOOKS_KEY) as ArrayList<BookEntity>

        setupRecycler()
        adapter.update(booksList)
    }

    // region private
    //----------------------------------------------------------------------------------------------
    private fun setupRecycler() {
        adapter = BookAdapter()
        val layoutManager = LinearLayoutManager(applicationContext)
        val dividerItemDecoration = DividerItemDecoration(rvBooks?.context, layoutManager.orientation)
        rvBooks?.addItemDecoration(dividerItemDecoration)
        rvBooks?.layoutManager = layoutManager
        rvBooks?.itemAnimator = DefaultItemAnimator()
        rvBooks?.adapter = adapter
    }

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar?.setNavigationIcon(R.drawable.arrow_left)
        toolbar?.title = this.getString(R.string.title_book_screen)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }
    //----------------------------------------------------------------------------------------------
    // endregion private
}