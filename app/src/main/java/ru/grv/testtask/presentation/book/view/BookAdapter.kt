package ru.grv.testtask.presentation.book.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_book.view.*
import ru.grv.testtask.R
import ru.grv.testtask.domain.entity.BookEntity

class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var items: ArrayList<BookEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.nameBook?.text = items[position].nameBook

        holder.author?.text = items[position].authors
        if (items[position].imageBookUrl.isNotEmpty()) {
            Picasso.get().load(items[position].imageBookUrl).into(holder.screenBook)
        } else {
            holder.screenBook?.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_journal))
        }
    }

    fun update(items: ArrayList<BookEntity>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameBook: TextView? = null
        var author: TextView? = null
        var screenBook: ImageView? = null

        init {
            this.nameBook = itemView.nameBook
            this.author = itemView.author
            this.screenBook = itemView.screenBook
        }
    }
}