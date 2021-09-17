package ru.kubov.core_utils.extensions

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.facebook.drawee.view.SimpleDraweeView
import ru.kubov.core_utils.utils.DebounceClickListener
import ru.kubov.core_utils.utils.DebouncePostHandler
import kotlin.math.roundToInt

/**
 * Extension method for [View] implements setting [DebounceClickListener]
 * @param delay - delay for debouncing
 * @param onClickListener - default click listener
 */
fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}

/**
 * Extension method for [View] implements setting right padding
 * @param padding
 */
fun View.setPaddingRight(padding: Int) = this.setPadding(this.paddingLeft, this.paddingTop, padding, this.paddingBottom)

/**
 * Extension method for [View] implements getting font with [ResourcesCompat]
 * @param id - resource id
 */
fun View.getFont(@FontRes id: Int): Typeface? = ResourcesCompat.getFont(context, id)

/**
 * Extension method for [View] implements getting color with [ContextCompat]
 * @param id - resource id
 */
fun View.getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

/**
 * Extension method for [View] implements getting drawable with [ContextCompat]
 * @param id - resource id
 */
fun View.getDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(context, id)

/**
 * Extension method provides converting dp to px
 *
 * @param dp - value od dp in int
 */
fun View.dpToPx(dp: Int): Int = context.dpToPx(dp)

/**
 * Extension method for [View] implements setting alpha if alpha was changed
 * @param padding
 */
fun View.setAlphaIfNew(alpha: Float) {
    if (this.alpha != alpha) this.alpha = alpha
}
