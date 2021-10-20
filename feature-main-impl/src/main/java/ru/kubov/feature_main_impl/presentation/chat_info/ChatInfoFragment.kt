package ru.kubov.feature_main_impl.presentation.chat_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.kubov.feature_main_impl.R
import ru.kubov.feature_main_impl.databinding.FrgamentChatInfoBinding
import ru.kubov.feature_main_impl.databinding.IncludeMenuOptionBinding
import ru.kubov.feature_main_impl.di.module.MainFeatureComponent
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder
import javax.inject.Inject

class ChatInfoFragment : Fragment() {

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
        initMembersSettingsOption()
        initMediaSettingsOption()
        initNotificationSettingsOption()
        initPrivacySettingsOption()
        initLeaveChannelSettingsOption()
    }

    private fun initMembersSettingsOption() {
        includeMembersMenuOptionBinging.includeMenuOptionsTvTitle.text = getString(R.string.members)
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


    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .chatInfoComponentFactory()
            .create(this)
            .inject(this)
    }
}
