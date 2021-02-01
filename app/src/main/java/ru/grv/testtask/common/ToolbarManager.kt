package ru.grv.testtask.common

import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import ru.grv.testtask.R
import ru.grv.testtask.common.mvp.BaseController

class ToolbarManager(
    private val controller: BaseController,
    private val toolbar: Toolbar,
    private val title: String?,
    private val iconType: IconType
) {

    enum class IconType {
        NONE {
            override fun imageResId(): Int {
                return -1
            }
        },
        BACK {
            override fun imageResId(): Int {
                return R.drawable.arrow_left
            }
        };

        @DrawableRes
        internal abstract fun imageResId(): Int
    }

    init {
        setTitle()
        setIcon()
        setVisibility()
    }

    private fun setTitle() {
        if (title != null) {
            toolbar.title = title
        }
    }

    private fun setIcon() {
        val iconResId = iconType.imageResId()
        if (iconResId != -1) {
            val iconDrawable = controller.applicationContext
                    ?.let{ AppCompatResources.getDrawable(it, iconResId) }
            if (iconDrawable != null) {
                toolbar.navigationIcon = iconDrawable
                toolbar.setNavigationOnClickListener {
                    when (iconType) {
                        IconType.BACK -> {
                            controller.close()
                        }
                        IconType.NONE -> {
                            // Nothing
                        }
                    }
                }
            }
        }
    }

    private fun setVisibility() {
        //TODO: implement this
    }
}