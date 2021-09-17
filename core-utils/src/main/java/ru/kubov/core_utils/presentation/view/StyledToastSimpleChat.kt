package ru.kubov.core_utils.presentation.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ToastStyledSimpleChatBinding

/**
 * Class implements logic of toast manager to show custom toasts
 */
class StyledToastSimpleChat {

    companion object {

        fun makeToast(context: Context, text: String, @DrawableRes iconId: Int? = null): Toast {
            val contentViewBinding = ToastStyledSimpleChatBinding.inflate(LayoutInflater.from(context))
            with(contentViewBinding) {
                toastStyledSimpleChatTvToastTitle.text = text
                toastStyledSimpleChatIvIcon.apply {
                    if(iconId != null) {
                        isVisible = true
                        setImageResource(iconId)
                    }
                }
            }
            return Toast(context).apply {
                setGravity(Gravity.CENTER, 0, 0)
                duration = Toast.LENGTH_LONG
                view = contentViewBinding.root
            }
        }
    }
}