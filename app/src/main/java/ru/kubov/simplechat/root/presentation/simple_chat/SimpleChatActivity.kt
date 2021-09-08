package ru.kubov.simplechat.root.presentation.simple_chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.kubov.simplechat.R
import ru.kubov.simplechat.databinding.ActivitySimpleChatBinding
import ru.kubov.simplechat.di.root.RootComponent
import ru.kubov.simplechat.di.root.RootComponentHolder
import ru.kubov.simplechat.root.navigation.Navigator
import javax.inject.Inject

/**
 * Class implements single activity in application
 */
class SimpleChatActivity : AppCompatActivity() {

    companion object {
        private val TAG = SimpleChatActivity::class.simpleName
    }

    /**
     * App entity [Navigator] implements navigation between screens
     */
    @Inject
    lateinit var navigatior: Navigator

    /**
     * Activity view model [SimpleChatViewModel]
     */
    @Inject
    lateinit var viewModel: SimpleChatViewModel

    /**
     * Keeps host fragment container
     */
    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_simple_chat__fcv_nav_host) as NavHostFragment
        navHostFragment.navController
    }

    private var _binding: ActivitySimpleChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        _binding = ActivitySimpleChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        navigatior.attach(this, navController)
    }

    override fun onPause() {
        super.onPause()
        navigatior.detach()
        if (isFinishing) {
            RootComponentHolder.reset()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun inject() {
        (RootComponentHolder.get() as RootComponent)
            .simpleChatActivityComponentFactory()
            .create(this)
            .inject(this)
    }
}