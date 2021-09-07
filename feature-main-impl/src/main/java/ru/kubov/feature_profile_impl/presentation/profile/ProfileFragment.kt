package ru.kubov.feature_profile_impl.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kubov.feature_profile_impl.R
import ru.kubov.feature_profile_impl.databinding.FragmentProfileBinding
import ru.kubov.feature_profile_impl.databinding.IncludeProfileMenuOptionBinding
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponent
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder
import javax.inject.Inject


/**
 * Class implements ui of user profile
 */
class ProfileFragment : Fragment() {

    companion object {
        // TODO: 07.09.2021 make bundle for navigation
        fun makeBundle() = bundleOf()
    }

    @Inject
    lateinit var viewModel: ProfileViewModel

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var _includeEditProfileBinding: IncludeProfileMenuOptionBinding? = null
    private val includeEditProfileBinding get() = _includeEditProfileBinding!!

    private var _includeThemeBinding: IncludeProfileMenuOptionBinding? = null
    private val includeThemeBinding get() = _includeThemeBinding!!

    private var _includeDataAndStorageBinding: IncludeProfileMenuOptionBinding? = null
    private val includeDataAndStorageBinding get() = _includeDataAndStorageBinding!!

    private var _includeLogoutBinding: IncludeProfileMenuOptionBinding? = null
    private val includeLogoutBinding get() = _includeLogoutBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        inject()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        _includeEditProfileBinding = IncludeProfileMenuOptionBinding.bind(binding.frgProfileIncludeEditProfile.root)
        _includeThemeBinding = IncludeProfileMenuOptionBinding.bind(binding.frgProfileIncludeTheme.root)
        _includeDataAndStorageBinding =
            IncludeProfileMenuOptionBinding.bind(binding.frgProfileIncludeDataAndStorage.root)
        _includeLogoutBinding = IncludeProfileMenuOptionBinding.bind(binding.frgProfileIncludeLogout.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenuOptions()
        subscribe()
    }

    private fun initMenuOptions() {
        includeEditProfileBinding.includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_edit_24)
        includeEditProfileBinding.includeProfileMenuOptionsTvTitle.text = getString(R.string.edit_profile)

        includeThemeBinding.includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_choice_theme_24)
        includeThemeBinding.includeProfileMenuOptionsTvTitle.text = getString(R.string.theme)

        includeDataAndStorageBinding.includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_data_24)
        includeDataAndStorageBinding.includeProfileMenuOptionsTvTitle.text = getString(R.string.data_and_storage)

        includeLogoutBinding.includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_door_24)
        includeLogoutBinding.includeProfileMenuOptionsTvTitle.text = getString(R.string.exit_from_profile)
    }

    private fun subscribe() {
        /*viewModel.profile.observe(this.viewLifecycleOwner) {

        }*/
    }

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .profileComponentFactory()
            .create(this)
            .inject(this)
    }
}