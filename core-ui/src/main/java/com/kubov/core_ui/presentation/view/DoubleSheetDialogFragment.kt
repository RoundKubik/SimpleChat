package com.kubov.core_ui.presentation.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kubov.core_ui.databinding.DialogDoubleChoiseBinding
import ru.kubov.core_utils.R
import ru.kubov.core_utils.extensions.setDebounceClickListener


/**
 * Sheet dialog fragment implements slide animation
 */
@Deprecated(
    "Use flexible sheet dialog with possibility add any dialog layout",
    ReplaceWith("See new sheet dialog", "package com.kuboc.core_ui.presentation.view.SheetDialog")
)
class DoubleSheetDialogFragment private constructor(
    private val title: String,
    private val confirmTitle: String,
    private val declineTitle: String,
    @ColorRes private val confirmColor: Int,
    @ColorRes private val declineColor: Int
) : BottomSheetDialogFragment() {

    private var _binding: DialogDoubleChoiseBinding? = null
    private val binding get() = _binding!!

    private var onDeclineListener: (() -> Unit)? = null
    private var onConfirmListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogDoubleChoiseBinding.inflate(inflater, container, false)
        requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            frgDialogDoubleChoiceTvOk.setDebounceClickListener {
                onConfirmListener?.invoke()
                dismiss()
            }
            frgDialogDoubleChoiceTvCancel.setDebounceClickListener {
                onDeclineListener?.invoke()
                dismiss()
            }

            frgDialogDoubleChoiceTvTitle.text = title
            frgDialogDoubleChoiceTvOk.text = confirmTitle
            frgDialogDoubleChoiceTvOk.setTextColor(ContextCompat.getColor(requireContext(), confirmColor))
            frgDialogDoubleChoiceTvCancel.text = declineTitle
            frgDialogDoubleChoiceTvCancel.setTextColor(ContextCompat.getColor(requireContext(), declineColor))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onDeclineListener = null
        onDeclineListener = null
    }

    /**
     * Method provides setting of listener of clicking decline
     * @param [declineListener]
     */
    fun setDeclineListener(declineListener: () -> Unit) {
        onDeclineListener = declineListener
    }

    /**
     * Method provides setting of listener of clicking confirm
     * @param [confirmListener]
     */
    fun setConfirmListener(confirmListener: () -> Unit) {
        onConfirmListener = confirmListener
    }

    /**
     * Builder of [DoubleSheetDialogFragment]
     *
     * @param context - provides from view where [DoubleSheetDialogFragment] called
     */
    class Builder(context: Context) {

        private var title: String = ""
        private var confirmTitle: String = context.getString(R.string.yes)
        private var declineTitle: String = context.getString(R.string.no)

        @ColorRes
        private var confirmColor: Int = R.color.main_text_color

        @ColorRes
        private var declineColor: Int = R.color.main_text_color

        /**
         * Method for init title in bottom sheet dialog
         * @param title - text title of bottom sheet dialog
         */
        fun initTitle(title: String) = apply {
            this.title = title
        }

        /**
         * Method for init text in confirm button
         * @param confirmTitle - text of confirmation, example of text: ok
         */
        fun initConfirmTitleButton(confirmTitle: String) = apply {
            this.confirmTitle = confirmTitle
        }

        /**
         * Method for init text in decline button
         * @param declineTitle - text of declining, example of text: cancel
         */
        fun initDeclineTitleButton(declineTitle: String) = apply {
            this.declineTitle = declineTitle
        }

        /**
         * Method for set color of confirm button
         *
         * @param confirmColor - resource from res/color, for example you may set red color for maximisation
         * of user attention of confirmation
         */
        fun initConfirmColor(@ColorRes confirmColor: Int) = apply {
            this.confirmColor = confirmColor
        }

        /**
         * Method for set color of decline button
         *
         * @param declineColor - resource from res/color, for example you may set red color for maximisation
         * of user attention of confirmation
         */
        fun initDeclineColor(@ColorRes declineColor: Int) = apply {
            this.declineColor = declineColor
        }

        /**
         * Method form build current [DoubleSheetDialogFragment]
         */
        fun build() = DoubleSheetDialogFragment(
            title, confirmTitle, declineTitle, confirmColor, declineColor
        )
    }
}