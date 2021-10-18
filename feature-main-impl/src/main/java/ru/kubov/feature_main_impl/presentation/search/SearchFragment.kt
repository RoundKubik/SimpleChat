package ru.kubov.feature_main_impl.presentation.search

import android.app.ActionBar
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kubov.core_utils.domain.models.Chat
import ru.kubov.feature_main_impl.databinding.FragmentSearchBinding
import com.kubov.core_ui.presentation.chats.adapter.ChatsAdapter
import com.kubov.core_ui.presentation.view.search.SearchView
import com.kubov.core_ui.presentation.view.toolbar.CompositeToolbar
import ru.kubov.core_utils.extensions.dpToPx

/**
 * Class provides search logic
 * in current time it will be used in search realisation of chats
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var chatsAdapter: ChatsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initToolbar()
    }

    private fun initViews() {
        initChatRecycler()
    }

    private fun initToolbar() {

        val newSearchView = SearchView(requireContext())
        binding.frgSearchStToolbarSearch.replaceLeftArea(newSearchView)
        val searchView2 = SearchView(requireContext())
        binding.frgSearchStToolbarSearch.replaceCenterArea(searchView2)
        val searchView3 = SearchView(requireContext())
        binding.frgSearchStToolbarSearch.replaceRightArea(searchView3)
        /*binding.frgSearchStToolbarSearch.setCenterArea(
            newSearchView,
            FrameLayout.LayoutParams(requireContext().dpToPx(100), ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER_VERTICAL
            })*/


    }

    private fun initChatRecycler() {
        chatsAdapter = ChatsAdapter()
        chatsAdapter.submitList(
            listOf(
                Chat(
                    id = 1,
                    imageLogo = "https://i.picsum.photos/id/993/200/300.jpg?grayscale&hmac=3y-6Ta1y7ytC4ol-HKWiAJKC6diWns1UrHe9rT9UnTQ",
                    chatDescription = null,
                    chatTitle = "Simple chat",
                    chatShortInfo = "Чат 3 устаника"
                ),
                Chat(
                    id = 2,
                    imageLogo = "https://i.picsum.photos/id/993/200/300.jpg?grayscale&hmac=3y-6Ta1y7ytC4ol-HKWiAJKC6diWns1UrHe9rT9UnTQ",
                    chatDescription = null,
                    chatTitle = "Simple chat 2",
                    chatShortInfo = "Чат 5 устаника"
                )
            )
        )
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.frgSearchRvChats.layoutManager = layoutManager
        binding.frgSearchRvChats.addItemDecoration(
            DividerItemDecoration(
                binding.frgSearchRvChats.context, layoutManager.orientation
            ).apply {

            }
        )
        binding.frgSearchRvChats.adapter = chatsAdapter
    }
}