package ru.kubov.core_utils.presentation.view.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import ru.kubov.core_utils.R
import ru.kubov.core_utils.databinding.ViewSearchToolbarBinding
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.utils.TextChangesTextWatcher

// TODO: 29.09.2021
// TODO: 29.09.2021 set stylable attrs from xml not from code
class SearchToolbar : RelativeLayout {

    val onSearchQueryChanged: ((query: String) -> Unit)? = null

    private var _binding: ViewSearchToolbarBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        _binding = ViewSearchToolbarBinding.inflate(LayoutInflater.from(context), this)
        initViews()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.viewSearchToolbarSvSearch.onSearchQueryChanged = onSearchQueryChanged
    }

    override fun onDetachedFromWindow() {
        binding.viewSearchToolbarSvSearch.onSearchQueryChanged = null
        super.onDetachedFromWindow()
    }

    fun setSearchHint(@StringRes hint: Int) {
        binding.viewSearchToolbarSvSearch.setSearchHint(hint)
    }

    fun setBackButtonIcon(@DrawableRes icon: Int) {
        binding.viewSearchToolbarIbActionBack.setImageResource(icon)
    }

    fun setBackButtonVisibility(isVisible: Boolean) {
        binding.viewSearchToolbarIbActionBack.isVisible = isVisible
    }

    private fun initViews() {
        binding.viewSearchToolbarSvSearch.setSearchBackground(R.color.background_secondary)
    }
}