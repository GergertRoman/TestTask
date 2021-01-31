package ru.grv.testtask

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class AlertFunctions(
        private val activity: Activity,
        private val message: String,
        private val title: String? = "Ошибка",
        private val buttonsType: BUTTONS? = BUTTONS.OK,
        private val okButtonResId: Int? = R.string.ok,
        private val cancelButtonResId: Int? = R.string.cancel,
        private val okListener: DialogInterface.OnClickListener? = null,
        private val cancelListener: DialogInterface.OnClickListener? = null,
        private val canceledOnTouchOutside: Boolean = false) {

    enum class BUTTONS {
        OK,
        OK_CANCEL
    }

    fun show() {
        val faceButton = Typeface.createFromAsset(activity.assets, "fonts/Roboto-Medium.ttf")
        val alertDialog = android.app.AlertDialog.Builder(
                activity,
                R.style.YDialog
        )
        alertDialog.setMessage(message)
            .setTitle(title)
        when (buttonsType) {
            BUTTONS.OK -> {
                if (okButtonResId != null) {
                    alertDialog.setPositiveButton(activity.getString(okButtonResId), okListener)
                }
            }
            BUTTONS.OK_CANCEL -> {
                if (okButtonResId != null) {
                    alertDialog.setPositiveButton(activity.getString(okButtonResId), okListener)
                }
                if (cancelButtonResId != null) {
                    alertDialog.setNegativeButton(activity.getString(cancelButtonResId), cancelListener)
                }
            }
            else -> {
                if (okButtonResId != null) {
                    alertDialog.setPositiveButton(activity.getString(okButtonResId), okListener)
                }
            }
        }

        val alert = alertDialog.create()

        if (!title.isNullOrEmpty()) {
            // title
            val faceTitle = Typeface.createFromAsset(activity.assets, "fonts/Roboto-Medium.ttf")
            val titleOfDialog = TextView(activity)
            titleOfDialog.text = title
            titleOfDialog.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            titleOfDialog.gravity = Gravity.START;
            titleOfDialog.setPadding(0, 0, 50, 0)
            titleOfDialog.setTextColor(ContextCompat.getColor(activity, R.color.prime_black))
            titleOfDialog.typeface = faceTitle
            alert.setCustomTitle(titleOfDialog)
        }

        if(canceledOnTouchOutside) {
            alert.setCanceledOnTouchOutside(true)
        }
        alert.show()

        val buttonPositive = alert.findViewById<View>(android.R.id.button1) as Button
        buttonPositive.apply {
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, R.color.prime_black))
            typeface = faceButton
        }

        val buttonNegative = alert.findViewById<View>(android.R.id.button2) as Button
        buttonNegative.apply {
            textSize = 14f
            setTextColor(ContextCompat.getColor(context, R.color.prime_black))
            typeface = faceButton
        }

        // message
        val message = alert.findViewById<View>(android.R.id.message) as TextView
        val faceMessage = Typeface.createFromAsset(activity.assets, "fonts/Roboto-Regular.ttf")
        if (!message.text.isNullOrEmpty()) {
            message.apply {
                textSize = 16f
                setPadding(0, 40, 50, 60)
                setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                typeface = faceMessage
            }
        } else {
            message.visibility = View.GONE
        }
    }
}