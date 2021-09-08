package ru.kubov.feature_main_impl.presentation.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kubov.core_utils.databinding.DialogDoubleChoiseBinding
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.presentation.view.SheetDialog
import ru.kubov.feature_main_impl.R
import com.github.dhaval2404.imagepicker.ImagePicker
import ru.kubov.core_utils.extensions.showImage
import ru.kubov.feature_main_api.navigation.MainRouter
import ru.kubov.feature_main_impl.databinding.FragmentProfileBinding
import ru.kubov.feature_main_impl.databinding.IncludeProfileMenuOptionBinding
import ru.kubov.feature_main_impl.di.module.MainFeatureComponent
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder
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
    lateinit var navigator: MainRouter

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

    private val logoutDialog by lazy {

        val contentViewBinding = DialogDoubleChoiseBinding.inflate(LayoutInflater.from(requireContext()))
        contentViewBinding.frgDialogDoubleChoiceTvTitle.text = getString(R.string.exit_from_profile)
        contentViewBinding.frgDialogDoubleChoiceTvOk.text = getString(R.string.exit)
        contentViewBinding.frgDialogDoubleChoiceTvOk.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.error_text_color
            )
        )

        val dialog = SheetDialog(
            requireContext(),
            contentViewBinding.root
        )
        contentViewBinding.frgDialogDoubleChoiceTvOk.setDebounceClickListener {
            onLogout()
            dialog.animatedDismiss()
        }
        contentViewBinding.frgDialogDoubleChoiceTvCancel.setDebounceClickListener {
            dialog.animatedDismiss()
        }
        return@lazy dialog
    }

    private val settingsThemeDialog by lazy {

        val contentViewBinding = DialogDoubleChoiseBinding.inflate(LayoutInflater.from(requireContext()))
        contentViewBinding.frgDialogDoubleChoiceTvTitle.text = getString(R.string.theme)
        contentViewBinding.frgDialogDoubleChoiceTvOk.text = getString(R.string.light_theme)
        contentViewBinding.frgDialogDoubleChoiceTvCancel.text = getString(R.string.dark_theme)

        val dialog = SheetDialog(
            requireContext(),
            contentViewBinding.root
        )
        contentViewBinding.frgDialogDoubleChoiceTvOk.setDebounceClickListener {
            setLightTheme()
            dialog.animatedDismiss()
        }
        contentViewBinding.frgDialogDoubleChoiceTvCancel.setDebounceClickListener {
            setDarkTheme()
            dialog.animatedDismiss()
        }
        return@lazy dialog
    }

    private val startForPickProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                binding.frgProfileIvAvatar.showImage(fileUri)
            }
        }

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
        initViews()
    }

    private fun initMenuOptions() {
        with(includeEditProfileBinding) {
            includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_edit_24)
            includeProfileMenuOptionsTvTitle.text = getString(R.string.edit_profile)

            root.setDebounceClickListener {

            }
        }

        with(includeThemeBinding) {
            includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_choice_theme_24)
            includeProfileMenuOptionsTvTitle.text = getString(R.string.theme)

            root.setDebounceClickListener {
                settingsThemeDialog.show()
            }
        }

        with(includeDataAndStorageBinding) {
            includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_data_24)
            includeProfileMenuOptionsTvTitle.text = getString(R.string.data_and_storage)

            root.setDebounceClickListener {

            }
        }

        with(includeLogoutBinding) {
            includeProfileMenuOptionsIvIcon.setImageResource(R.drawable.ic_door_24)
            includeProfileMenuOptionsTvTitle.text = getString(R.string.exit_from_profile)

            root.setDebounceClickListener {
                logoutDialog.show()
            }
        }
    }

    private fun initViews() {
        binding.frgProfileIvAvatar.setDebounceClickListener {
            ImagePicker.with(this)
                .createIntent {
                    startForPickProfileImageResult.launch(it)
                }
        }
    }

    private fun subscribe() {
        /*viewModel.profile.observe(this.viewLifecycleOwner) {

        }*/
    }

    // TODO: 08.09.2021 implement logout logic 
    private fun onLogout() {

    }

    // TODO: 08.09.2021 implement light theme settings 
    private fun setLightTheme() {

    }

    // TODO: 08.09.2021 implement dark theme settings
    private fun setDarkTheme() {

    }

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .profileComponentFactory()
            .create(this)
            .inject(this)
    }
}