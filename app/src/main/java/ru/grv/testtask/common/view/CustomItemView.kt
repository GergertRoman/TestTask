package ru.grv.testtask.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_item_view.view.*
import ru.grv.testtask.R

class CustomItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    // Attrs
    private var title: String? = null
    private var description: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_item_view, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView, 0, 0)
        applyAttrs(typedArray)
    }

    private fun applyAttrs(a: TypedArray) {
        title = a.getString(R.styleable.CustomItemView_title)
        description = a.getString(R.styleable.CustomItemView_description)

        titleItem.text = title
        descriptionItem.text = description
    }

    fun setDescription(description: String?) {
        this.description = description
        descriptionItem.text = this.description
    }

    fun setTitle(title: String?) {
        this.title = title
        titleItem.text = this.title
    }
}