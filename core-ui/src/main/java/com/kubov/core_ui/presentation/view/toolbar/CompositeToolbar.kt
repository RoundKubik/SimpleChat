package com.kubov.core_ui.presentation.view.toolbar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginStart
import com.kubov.core_ui.databinding.ViewCompositeToolbarBinding
import ru.kubov.core_utils.extensions.dpToPx

// TODO: 18.10.2021 add documentation
class CompositeToolbar<LA : View, CA : View, RA : View> : FrameLayout {

    companion object {
        const val STANDARD_END_MARGIN = 8
        const val STANDARD_START_MARGIN = 8
        const val STANDARD_LEFT_AREA_WIDTH = 36
        const val STANDARD_RIGHT_AREA_WIDTH = 36
    }

    private var leftArea: LA? = null
    private var centerArea: CA? = null
    private var rightArea: RA? = null

    private var _binding: ViewCompositeToolbarBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewCompositeToolbarBinding.inflate(LayoutInflater.from(context), this)
    }

    // TODO: 18.10.2021
    fun replaceLeftArea(
        contentArea: LA,
        layoutParams: LayoutParams = LayoutParams(
            dpToPx(STANDARD_LEFT_AREA_WIDTH),
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            marginStart = dpToPx(STANDARD_START_MARGIN)
        }
    ) {
        if (leftArea != null) {
            replaceArea(contentArea, leftArea!!, layoutParams)
        } else {
            replaceArea(contentArea, binding.viewCompositeToolbarVsLeftArea, layoutParams)
        }
        leftArea = contentArea

        if (centerArea != null) {
            updateCenterAreaMargins()
        }
    }

    // TODO: 18.10.2021
    fun replaceCenterArea(
        contentArea: CA,
        layoutParams: LayoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_VERTICAL
            marginStart = dpToPx(STANDARD_START_MARGIN)
            marginEnd = dpToPx(STANDARD_END_MARGIN)
        }
    ) {
        if (centerArea != null) {
            replaceArea(contentArea, leftArea!!, layoutParams)
        } else {
            replaceArea(contentArea, binding.viewCompositeToolbarVsCenterArea, layoutParams)
        }
        centerArea = contentArea
        updateCenterAreaMargins()
    }

    // TODO: 18.10.2021
    fun replaceRightArea(
        contentArea: RA,
        layoutParams: LayoutParams = LayoutParams(
            dpToPx(STANDARD_RIGHT_AREA_WIDTH),
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
            marginEnd = dpToPx(STANDARD_END_MARGIN)
        }
    ) {
        if (rightArea != null) {
            replaceArea(contentArea, leftArea!!, layoutParams)
        } else {
            replaceArea(contentArea, binding.viewCompositeToolbarVsRightArea, layoutParams)
        }
        rightArea = contentArea

        if (centerArea != null) {
            updateCenterAreaMargins()
        }
    }

    // FIXME: 18.10.2021 not work updating from right margin 
    private fun updateCenterAreaMargins() {
        val layoutParamsCenter = centerArea!!.layoutParams as MarginLayoutParams
        if (leftArea != null) {
            val leftAreaLayoutParams = leftArea!!.layoutParams as MarginLayoutParams
            layoutParamsCenter.marginStart = leftAreaLayoutParams.marginStart + leftAreaLayoutParams.width
        }
        if (rightArea != null) {
            val rightAreaLayoutParams = rightArea!!.layoutParams as MarginLayoutParams
            layoutParamsCenter.marginEnd = rightAreaLayoutParams.marginEnd + rightAreaLayoutParams.width
        }
        centerArea!!.layoutParams = layoutParamsCenter
    }

    private fun replaceArea(contentArea: View, area: View, layoutParams: LayoutParams) {
        val indexOfChild = indexOfChild(area)
        removeViewAt(indexOfChild)
        addView(contentArea, indexOfChild, layoutParams)
    }

}