package io.usdaves.tasky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import io.usdaves.auth.signin.SignInFragment
import io.usdaves.tasky.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    if (savedInstanceState == null) {
      supportFragmentManager.commit {
        add(R.id.main_container, SignInFragment())
      }
    }
  }
}
