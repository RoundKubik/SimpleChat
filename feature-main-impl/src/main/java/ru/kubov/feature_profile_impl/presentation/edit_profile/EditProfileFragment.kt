package ru.kubov.feature_profile_impl.presentation.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kubov.feature_profile_impl.databinding.FragmentEditProfileBinding
import ru.kubov.feature_profile_impl.databinding.IncludeBaseToolbarBinding
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponent
import ru.kubov.feature_profile_impl.di.module.MainFeatureComponentHolder
import javax.inject.Inject

class EditProfileFragment : Fragment() {

    @Inject
    lateinit var viewModel: EditProfileViewModel

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var _includeBaseToolbarBinding: IncludeBaseToolbarBinding? = null
    private val includeBaseToolbarBinding get() = _includeBaseToolbarBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inject()
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        _includeBaseToolbarBinding = IncludeBaseToolbarBinding.bind(binding.frgEditProfileLayoutToolbar.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .editProfileComponentFactory()
            .create(this)
            .inject(this)
    }
}