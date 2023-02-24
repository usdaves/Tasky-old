package io.usdaves.tasky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import io.usdaves.auth.signin.SignInFragment
import io.usdaves.core.ActivityContentSetter
import io.usdaves.tasky.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var contentSetter: ActivityContentSetter

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    contentSetter.setContentView(this, binding.root)

    if (savedInstanceState == null) {
      supportFragmentManager.commit {
        add(R.id.main_container, SignInFragment())
      }
    }
  }
}
