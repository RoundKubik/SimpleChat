package ru.kubov.feature_main_impl.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kubov.feature_main_impl.databinding.FragmentChatBinding
import ru.kubov.feature_main_impl.databinding.IncludeBaseToolbarBinding

class ChatFragment : Fragment() {

    companion object {
        // TODO: 10.09.2021 add methods to make bundle for new instance
    }

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var _includeChatToolbarBinding: IncludeBaseToolbarBinding? = null
    private val includeChatToolbarBinding get() = _includeChatToolbarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        _includeChatToolbarBinding = IncludeBaseToolbarBinding.bind()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showUserProfileDialog(userId: Int) {

    }


    // TODO: 10.09.2021 open image picker fragment or open picker
    fun openGetImageFragment() {

    }

    // TODO: 10.09.2021 open image from message to full screen 50/50 
    fun openMessageImage() {

    }
}