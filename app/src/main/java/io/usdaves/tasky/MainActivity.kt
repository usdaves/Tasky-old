package io.usdaves.tasky

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import io.usdaves.core.ActivityContentSetter
import io.usdaves.core.util.collectLatest
import io.usdaves.tasky.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var contentSetter: ActivityContentSetter

  private val viewModel: MainViewModel by viewModels()

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    contentSetter.setContentView(this, binding.root)
    navController = findNavController()

    collectLatest(viewModel.navDirection) { navDirection ->
      navController.navigate(navDirection)
    }
  }

  private fun findNavController(): NavController {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    return (navHostFragment as? NavHostFragment)?.navController
      ?: error("NavHostFragment have not found")
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  private fun NavController.navigate(navDirection: NavDirection) {
    val graph = navInflater.inflate(navDirection.graphId)
    navDirection.startDestination?.let { graph.setStartDestination(it) }
    this.graph = graph
  }
}
