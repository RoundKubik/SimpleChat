package ru.kubov.feature_profile_impl.presentation.settings_data_and_storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kubov.core_utils.extensions.setDebounceClickListener
import ru.kubov.feature_profile_impl.R
import ru.kubov.feature_profile_impl.databinding.FragmentSettingsDataAndStorageBinding
import ru.kubov.feature_profile_impl.databinding.IncludeBaseToolbarBinding
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponent
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder
import javax.inject.Inject


/**
 * Class implement presentation of settings of cached data. Currently provides clear image cache
 */
class SettingsDataAndStorageFragment : Fragment() {

    @Inject
    lateinit var viewModel: SettingsDataAndStorageViewModel

    private var _binding: FragmentSettingsDataAndStorageBinding? = null
    private val binding get() = _binding!!

    private var _includeBaseToolbarBinding: IncludeBaseToolbarBinding? = null
    private val includeBaseToolbarBinding get() = _includeBaseToolbarBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inject()
        _binding = FragmentSettingsDataAndStorageBinding.inflate(inflater, container, false)
        _includeBaseToolbarBinding = IncludeBaseToolbarBinding.bind(binding.frgSettingsDataAndStorageLayoutToolbar.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        // TODO: 07.09.2021 provide toast or bottom sheet to inform that user currently clear data
        binding.frgSettingsDataAndStorageTvClearData.setDebounceClickListener {
            clearImageData()
        }
    }

    // TODO: 07.09.2021 add logic to clear image data
    private fun clearImageData() {

    }

    private fun initToolbar() {
        includeBaseToolbarBinding.includeBaseToolbarTvTitle.text = getString(R.string.settings_storage_and_data)
        includeBaseToolbarBinding.includeBaseToolbarIvIconBack.setDebounceClickListener {
            viewModel.back()
        }
    }

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .settingsDataAndStorageComponentFactory()
            .create(this)
            .inject(this)
    }
}