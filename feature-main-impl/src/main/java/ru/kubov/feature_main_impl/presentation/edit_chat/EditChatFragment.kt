package ru.kubov.feature_main_impl.presentation.edit_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kubov.feature_main_impl.databinding.FragmentEditChatBinding
import javax.inject.Inject

class EditChatFragment : Fragment() {

    companion object {
        fun makeBundle() = bundleOf()
    }

    @Inject
    lateinit var viewModel: EditChatViewModel

    private var _binding: FragmentEditChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditChatBinding.inflate(inflater, container, false)
        return binding.root
    }
}