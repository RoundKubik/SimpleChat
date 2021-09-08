package ru.kubov.core_utils.presentation.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.kubov.core_utils.R
import ru.kubov.core_utils.databinding.DialogSheetBinding
import kotlin.math.roundToLong


/**
 * Implementation of bottom sheet dialog with swipe logic
 */
class SheetDialog(
    context: Context,
    private val contentView: View
) : Dialog(context, R.style.Dialog_SheetDialogTheme) {

    companion object {
        private const val SHOW_ANIM_DURATION_MS = 300L
        const val HIDE_ANIM_DURATION_MS = 300L
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private var _binding: DialogSheetBinding? = null
    private val binding get() = _binding!!

    private var showHideAnimator: AnimatorSet? = null
    private var backgroundAlphaAnimator: ValueAnimator? = null

    private var backgroundAlpha: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backgroundAlpha = context.resources.getInteger(R.integer.dialog_overlay_alpha)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window!!.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window!!.setDimAmount(0.0f)

        // region initialize layout
        _binding = DialogSheetBinding.inflate(LayoutInflater.from(context))
        binding.dialogSheetClCoordinatorLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN && !isShowHideAnimatorRunning()) {
                animatedDismiss()
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.dialogSheetLlBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss()
                }
            }

            override fun onSlide(view: View, slideOffset: Float) {}
        })
        val contentLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        contentView.background = ContextCompat.getDrawable(context, R.drawable.background_dialog_sheet)
        binding.dialogSheetLlBottomSheet.addView(contentView, contentLayoutParams)
        super.setContentView(binding.dialogSheetClCoordinatorLayout)
        // endregion
    }

    override fun onStart() {
        super.onStart()
        if (!isShowing) {
            binding.dialogSheetClCoordinatorLayout.post {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                animateShow()
            }
        }
    }

    override fun onBackPressed() {
        if (!isShowHideAnimatorRunning()) {
            animateHide {
                super.onBackPressed()
            }
        }
    }

    override fun onDetachedFromWindow() {
        showHideAnimator?.cancel()
        super.onDetachedFromWindow()
    }

    /**
     * Start animation of dismissing dialog
     */
    fun animatedDismiss() {
        animateHide {
            dismiss()
        }
    }

    private fun animateShow() {
        if (showHideAnimator?.isRunning == true) {
            showHideAnimator?.cancel()
        }
        backgroundAlphaAnimator = ValueAnimator.ofInt(0, backgroundAlpha).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                updateBackgroundAlpha(value)
            }
        }
        binding.dialogSheetLlBottomSheet.translationY = binding.dialogSheetLlBottomSheet.height.toFloat()
        val contentTransitionAnimator = ObjectAnimator.ofFloat(binding.dialogSheetLlBottomSheet, "translationY", 0f)
        showHideAnimator = AnimatorSet().apply {
            play(backgroundAlphaAnimator).with(contentTransitionAnimator)
            duration = SHOW_ANIM_DURATION_MS
        }
        showHideAnimator?.start()
    }

    private fun animateHide(onFinished: () -> Unit) {
        if (showHideAnimator?.isRunning == true) {
            showHideAnimator?.cancel()
        }
        val fromAlpha = backgroundAlphaAnimator?.animatedValue as? Int ?: backgroundAlpha
        val duration = (fromAlpha / backgroundAlpha.toFloat() * HIDE_ANIM_DURATION_MS).roundToLong()
        backgroundAlphaAnimator = ValueAnimator.ofInt(fromAlpha, 0).apply {
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                updateBackgroundAlpha(value)
            }
        }
        val toTransition = binding.dialogSheetLlBottomSheet.height.toFloat()
        val contentTransitionAnimator =
            ObjectAnimator.ofFloat(binding.dialogSheetLlBottomSheet, "translationY", toTransition)
        showHideAnimator = AnimatorSet().apply {
            play(backgroundAlphaAnimator).with(contentTransitionAnimator)
            this.duration = duration
            addListener(onEnd = { onFinished() })
        }
        showHideAnimator?.start()
    }

    private fun isShowHideAnimatorRunning() = showHideAnimator?.isRunning == true

    private fun updateBackgroundAlpha(alpha: Int) {
        val drawable = ColorDrawable(ContextCompat.getColor(context, R.color.overlay_sheet_dialog))
        drawable.alpha = alpha
        window!!.setBackgroundDrawable(drawable)
    }

}