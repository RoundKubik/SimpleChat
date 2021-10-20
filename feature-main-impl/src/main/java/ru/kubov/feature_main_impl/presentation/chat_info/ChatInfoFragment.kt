package ru.kubov.feature_main_impl.presentation.chat_info

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.feature_main_impl.R
import ru.kubov.feature_main_impl.databinding.FrgamentChatInfoBinding
import ru.kubov.feature_main_impl.databinding.IncludeMenuOptionBinding
import ru.kubov.feature_main_impl.di.module.MainFeatureComponent
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder
import javax.inject.Inject
import com.github.dhaval2404.imagepicker.ImagePicker
import ru.kubov.core_utils.domain.models.Chat
import ru.kubov.core_utils.domain.models.ChatInfo
import ru.kubov.core_utils.extensions.addCircleRipple
import ru.kubov.core_utils.extensions.showImage


// TODO: 20.10.2021 add documentation
class ChatInfoFragment : Fragment() {

    companion object {
        private const val TITLE_VISIBLE_OFFSET = 200
    }

    @Inject
    lateinit var viewModel: ChatInfoViewModel

    private var _binding: FrgamentChatInfoBinding? = null
    private val binding get() = _binding!!

    private var _includeMembersMenuOptionBinging: IncludeMenuOptionBinding? = null
    private val includeMembersMenuOptionBinging get() = _includeMembersMenuOptionBinging!!

    private var _includeMediaMenuOptionBinding: IncludeMenuOptionBinding? = null
    private val includeMediaMenuOptionBinding get() = _includeMediaMenuOptionBinding!!

    private var _includeNotificationOptionBinging: IncludeMenuOptionBinding? = null
    private val includeNotificationOptionBinging get() = _includeNotificationOptionBinging!!

    private var _includePrivacyMenuOptionBinding: IncludeMenuOptionBinding? = null
    private val includePrivacyMenuOptionBinding get() = _includePrivacyMenuOptionBinding!!

    private var _includeLeaveChannelMenuOptionBinging: IncludeMenuOptionBinding? = null
    private val includeLeaveChannelMenuOptionBinging get() = _includeLeaveChannelMenuOptionBinging!!

    private val startForPickChatImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                binding.frgChatInfoSdvChatLogo.showImage(fileUri)
            }
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        inject()
        _binding = FrgamentChatInfoBinding.inflate(inflater, container, false)
        _includeMembersMenuOptionBinging = IncludeMenuOptionBinding.bind(binding.frgChatInfoIncludeMembers.root)
        _includeMediaMenuOptionBinding = IncludeMenuOptionBinding.bind(binding.frgChatInfoIncludeMedia.root)
        _includeNotificationOptionBinging = IncludeMenuOptionBinding.bind(binding.frgChatInfoIncludeNotifications.root)
        _includePrivacyMenuOptionBinding = IncludeMenuOptionBinding.bind(binding.frgChatInfoIncludePrivacy.root)
        _includeLeaveChannelMenuOptionBinging = IncludeMenuOptionBinding.bind(binding.frgChatInfoIncludeLeave.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initToolbar()
        initChatLogo()
        initScrollView()

        initMembersSettingsOption()
        initMediaSettingsOption()
        initNotificationSettingsOption()
        initPrivacySettingsOption()
        initLeaveChannelSettingsOption()
    }

    private fun initMembersSettingsOption() {
        includeMembersMenuOptionBinging.includeMenuOptionsTvTitle.text = getString(R.string.members)
        includeMembersMenuOptionBinging.includeMenuOptionTvContentInfo.isVisible = true
    }

    private fun initMediaSettingsOption() {
        includeMediaMenuOptionBinding.includeMenuOptionsTvTitle.text = getString(R.string.media)
    }

    private fun initNotificationSettingsOption() {
        includeNotificationOptionBinging.includeMenuOptionsTvTitle.text = getString(R.string.notifications)
    }

    private fun initPrivacySettingsOption() {
        includePrivacyMenuOptionBinding.includeMenuOptionsTvTitle.text = getString(R.string.privacy)
        includePrivacyMenuOptionBinding.includeMenuOptionIvIconAction.isVisible = false
    }

    private fun initLeaveChannelSettingsOption() {
        includeLeaveChannelMenuOptionBinging.includeMenuOptionsTvTitle.text = getString(R.string.leave_channel)
    }

    private fun initChatLogo() {
        binding.frgChatInfoSdvChatLogo.setDebounceClickListener {
            ImagePicker.with(this).createIntent {
                startForPickChatImageResult.launch(it)
            }
        }
    }

    private fun initScrollView() {
        binding.frgChatInfoNsvScroll.setOnScrollChangeListener { _: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            binding.frgChatInfoCtToolbar.centerArea?.isVisible = scrollY > TITLE_VISIBLE_OFFSET
        }
    }

    private fun initToolbar() {
        val iconBack = ImageView(requireContext()).apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_left_24))
            addCircleRipple()
        }
        iconBack.setDebounceClickListener {

        }
        binding.frgChatInfoCtToolbar.replaceLeftArea(iconBack)

        val toolbarTitle = TextView(
            requireContext(),
            null, 0, R.style.TextView_Main
        ).apply {
            isVisible = false
        }
        binding.frgChatInfoCtToolbar.replaceCenterArea(toolbarTitle)

        val iconEdit = ImageView(requireContext()).apply {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_edit_24))
            addCircleRipple()
        }
        iconEdit.setDebounceClickListener {

        }
        binding.frgChatInfoCtToolbar.replaceRightArea(iconEdit)
    }

    private fun showChatInfo(chat: ChatInfo) {
        binding.frgChatInfoCtToolbar.rightArea?.isVisible = chat.isAdmin
        (binding.frgChatInfoCtToolbar.centerArea as TextView).text = if (chat.isTread) {
            getString(R.string.tread)
        } else {
            chat.chatTitle
        }

        binding.frgChatInfoSdvChatLogo.showImage(chat.chatLogo)
        binding.frgChatInfoTvChatName.text = chat.chatTitle
        binding.frgChatInfoTvChatDescription.text = if ( chat.chatDescription == null) {
            getString(R.string.add_description)
        } else {
            chat.chatDescription
        }

        includeMembersMenuOptionBinging.includeMenuOptionTvContentInfo.text = chat.members.toString()
        includePrivacyMenuOptionBinding.includeMenuOptionTvContentInfo.text = if (chat.isPrivate) {
            getString(R.string.private_channel)
        } else {
            getString(R.string.public_channel)
        }
    }

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .chatInfoComponentFactory()
            .create(this)
            .inject(this)
    }
}
