package com.kubov.core_ui.presentation.view.toolbar

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.kubov.core_ui.databinding.ViewCompositeToolbarBinding
import ru.kubov.core_utils.extensions.dpToPx

// TODO: 18.10.2021 add documentation
class CompositeToolbar<LA : View, CA : View, RA : View> : FrameLayout {

    var leftArea: LA? = null
        private set

    var centerArea: CA? = null
        private set
    var rightArea: RA? = null
        private set

    private var _binding: ViewCompositeToolbarBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        _binding = ViewCompositeToolbarBinding.inflate(LayoutInflater.from(context), this)
    }

    // TODO: 18.10.2021
    fun setLeftArea(
        leftArea: LA,
        layoutParams: ViewGroup.LayoutParams = binding.viewCompositeToolbarVsLeftArea.layoutParams
    ) {
        this.leftArea = leftArea
        setArea(leftArea, binding.viewCompositeToolbarVsLeftArea, layoutParams)
    }

    // TODO: 18.10.2021
    fun setCenterArea(
        centerArea: CA,
        layoutParams: ViewGroup.LayoutParams = binding.viewCompositeToolbarVsCenterArea.layoutParams
    ) {
        this.centerArea = centerArea
        setArea(centerArea, binding.viewCompositeToolbarVsCenterArea, layoutParams)
    }

    // TODO: 18.10.2021 1
    fun setRightArea(
        rightArea: RA,
        layoutParams: ViewGroup.LayoutParams = binding.viewCompositeToolbarVsRightArea.layoutParams
    ) {
        this.rightArea = rightArea
        setArea(rightArea, binding.viewCompositeToolbarVsCenterArea, layoutParams)
    }


    private fun replaceAreaView(replacedIndex: Int, replacedView: View?, childLayoutParams: ViewGroup.LayoutParams) {
        removeViewAt(replacedIndex)
        if (replacedView != null) {
            addView(replacedView, replacedIndex, childLayoutParams)
        }
    }

    private fun setArea(content: View, area: View, layoutParams: ViewGroup.LayoutParams) {
        replaceAreaView(
            indexOfChild(area),
            content,
            layoutParams
        )
    }

}