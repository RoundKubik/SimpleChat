package ru.kubov.simplechat.root.presentation.simple_chat

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHost
import ru.kubov.simplechat.R
import ru.kubov.simplechat.databinding.ActivitySimpleChatBinding
import ru.kubov.simplechat.root.navigation.Navigator
import javax.inject.Inject

class SimpleChatActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatior: Navigator
    @Inject
    lateinit var viewModel: SimpleChatViewModel

    private var _binding: ActivitySimpleChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = ActivitySimpleChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHost
        val navController = navHostFragment.navController
        navigatior.attach(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        navigatior.detach()
    }

    private fun inject(){
        // TODO: 02.09.2021 implement injection method
        // TODO: 02.09.2021 get SimpleChatActivityComponentFactory and inject
    }
}