package ru.kubov.core_utils.presentation.view.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import ru.kubov.core_utils.databinding.ViewSearchBinding
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.utils.TextChangesTextWatcher

class SearchView : FrameLayout {

    var onSearchQueryChanged: ((query: String) -> Unit)? = null

    private val textChangeWatcher = TextChangesTextWatcher(this::onTextChanged)

    private var _binding: ViewSearchBinding? = null
    private val binding get() = _binding!!

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        _binding = ViewSearchBinding.inflate(LayoutInflater.from(context), this)
        initViews()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.viewSearchEtSearch.addTextChangedListener(textChangeWatcher)
    }

    override fun onDetachedFromWindow() {
        binding.viewSearchEtSearch.removeTextChangedListener(textChangeWatcher)
        super.onDetachedFromWindow()
    }

    fun setSearchHint(@StringRes hint: Int) {
        binding.viewSearchEtSearch.setHint(hint)
    }

    fun setSearchBackground(@ColorRes color: Int) {
        binding.viewSearchEtSearch.setBackgroundResource(color)
    }

    fun setClearIcon(@DrawableRes icon: Int) {
        binding.viewSearchToolbarIbActionClear.setImageResource(icon)
    }

    private fun onTextChanged(text: String) {
        binding.viewSearchToolbarIbActionClear.isVisible = text.isNotEmpty()
        onSearchQueryChanged?.invoke(text)
    }

    private fun clearInput() {
        binding.viewSearchEtSearch.setText("")
    }

    private fun initViews() {
        binding.viewSearchToolbarIbActionClear.setDebounceClickListener {
            clearInput()
        }
    }
}