package ru.grv.testtask.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ru.grv.testtask.R

class CustomItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    // SubViews
    private val titleView: TextView
    private val descriptionView: TextView

    // Attrs
    private var title: String? = null
    private var description: String? = null

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.custom_item_view, this, true)
        titleView = rootView.findViewById(R.id.title_item)
        descriptionView = rootView.findViewById(R.id.description_item)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView, 0, 0)
        applyAttrs(typedArray)
    }

    private fun applyAttrs(a: TypedArray) {
        title = a.getString(R.styleable.CustomItemView_title)
        description = a.getString(R.styleable.CustomItemView_description)

        titleView.text = title
        descriptionView.text = description
    }

    fun setDescription(description: String?) {
        this.description = description
        descriptionView.text = this.description
    }

    fun setTitle(title: String?) {
        this.title = title
        titleView.text = this.title
    }
}