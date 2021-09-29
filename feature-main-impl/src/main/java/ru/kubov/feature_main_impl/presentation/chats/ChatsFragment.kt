package ru.kubov.feature_main_impl.presentation.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kubov.feature_main_impl.databinding.FragmentChatsBinding
import ru.kubov.feature_main_impl.databinding.IncludeBaseToolbarBinding
import ru.kubov.feature_main_impl.databinding.IncludeMainToolbarBinding
import ru.kubov.feature_main_impl.di.module.MainFeatureComponent
import ru.kubov.feature_main_impl.di.module.MainFeatureComponentHolder

// TODO: 29.09.2021
class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private var _include: IncludeMainToolbarBinding? = null
    private val include get() = _include!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        _include = IncludeMainToolbarBinding.bind(binding.frgChatsIncludeToolbar.root)
        return binding.root
    }



    private fun inject() {
        (MainFeatureComponentHolder.get() as MainFeatureComponent)
            .chatsComponentFactory()
            .create(this)
            .inject(this)
    }
}