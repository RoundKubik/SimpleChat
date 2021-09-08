package ru.kubov.feature_main_impl.presentation.edit_profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.core_utils.extensions.showImage
import ru.kubov.feature_main_impl.R
import ru.kubov.feature_main_impl.databinding.FragmentEditProfileBinding
import ru.kubov.feature_main_impl.databinding.IncludeBaseToolbarBinding
import ru.kubov.feature_main_impl.di.module.MainFeatureComponent
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder
import javax.inject.Inject

/**
 * Class provides editing of profile
 */
class EditProfileFragment : Fragment() {

    @Inject
    lateinit var viewModel: EditProfileViewModel

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var _includeBaseToolbarBinding: IncludeBaseToolbarBinding? = null
    private val includeBaseToolbarBinding get() = _includeBaseToolbarBinding!!

    private val startForPickProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                binding.frgEditProfileIvAvatar.showImage(fileUri)
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inject()
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        _includeBaseToolbarBinding = IncludeBaseToolbarBinding.bind(binding.frgEditProfileLayoutToolbar.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.frgEditProfileIvAvatar.setDebounceClickListener {
            ImagePicker.with(this)
                .createIntent {
                    startForPickProfileImageResult.launch(it)
                }
        }

        includeBaseToolbarBinding.includeBaseToolbarTvTitle.text = getString(R.string.edit_profile)
        includeBaseToolbarBinding.includeBaseToolbarIvIconBack.setDebounceClickListener {
            viewModel.back()
        }
    }

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .editProfileComponentFactory()
            .create(this)
            .inject(this)
    }
}