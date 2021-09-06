package ru.kubov.simplechat.root.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import ru.kubov.feature_profile_impl.presentation.profile.ProfileFragment
import ru.kubov.simplechat.R
import ru.kubov.simplechat.databinding.FragmentMainBinding
import ru.kubov.simplechat.di.root.RootComponent
import ru.kubov.simplechat.di.root.RootComponentHolder
import javax.inject.Inject

/**
 * Host fragment that keeps tabs and bottom navigation
 *
 * Fragment tabs:
 * @see [ProfileFragment]
 */
class MainFragment : Fragment() {

    companion object {
        private val TAG = MainFragment::class.simpleName
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    /**
     * Attached navigation controller
     */
    private var navController: NavController? = null

    /**
     * Property keeps logic to implementation back navigation by clicking system button back
     */
    private val backCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            isEnabled = navController!!.navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        requireActivity().onBackPressedDispatcher.addCallback(backCallback)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        backCallback.isEnabled = false
    }

    /**
     * Check start tab
     */
    private fun isAtHomeTab(destination: NavDestination) =
        destination.id == navController!!.graph.startDestination

    private fun initViews() {
        binding.frgMainBnvBottomNavigationView.itemIconTintList = null
        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(R.id.frg_main__fcv_bottom_nav_host) as NavHostFragment
        navController = nestedNavHostFragment.navController

        binding.frgMainBnvBottomNavigationView.setupWithNavController(navController!!)
        binding.frgMainBnvBottomNavigationView.setOnItemSelectedListener { item ->
            onNavDestinationSelected(item, navController!!)
        }
        requireActivity().onBackPressedDispatcher.addCallback(backCallback)
        navController!!.addOnDestinationChangedListener { _, destination, _ ->
            backCallback.isEnabled = !isAtHomeTab(destination)
        }
    }

    private fun inject() {
        (RootComponentHolder.get() as RootComponent)
            .mainFragmentComponentFactory()
            .create(requireActivity())
            .inject(this)
    }
}