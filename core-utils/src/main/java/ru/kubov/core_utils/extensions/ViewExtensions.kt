package ru.kubov.core_utils.extensions

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ru.kubov.core_utils.utils.DebounceClickListener
import ru.kubov.core_utils.utils.DebouncePostHandler

// TODO: 12.09.2021
fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}

// TODO: 12.09.2021
fun View.setPaddingRight(padding: Int) = this.setPadding(this.paddingLeft, this.paddingTop, padding, this.paddingBottom)

// TODO: 12.09.2021
fun View.getFont(@FontRes id: Int): Typeface? = ResourcesCompat.getFont(context, id)

// TODO: 12.09.2021
fun View.getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

// TODO: 12.09.2021
fun View.getDrawable(@DrawableRes id: Int) : Drawable? = ContextCompat.getDrawable(context, id)