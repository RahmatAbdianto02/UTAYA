package com.dicoding.utaya.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.utaya.BottomActivity
import com.dicoding.utaya.data.Result
import com.dicoding.utaya.data.api.CookieInterceptor
import com.dicoding.utaya.data.response.login.ResponseLogin
import com.dicoding.utaya.data.utils.Preference
import com.dicoding.utaya.databinding.ActivityLoginBinding
import com.dicoding.utaya.ui.ViewModelFactory
import com.dicoding.utaya.ui.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur tampilan edge-to-edge hanya di bagian atas layar (status bar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                // Mengatur warna teks status bar menjadi hitam
                setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
            // Mengatur warna status bar menjadi putih
            @Suppress("DEPRECATION")
            window.statusBarColor = android.graphics.Color.WHITE
        }

        if (Preference.getToken(this) != null) {
            // Pengguna sudah login, navigasikan ke BottomActivity
            navigateToBottomActivity()
        }

        binding.apply {
            tvToRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }

        binding.btLogin.setOnClickListener {
            val username = binding.usernameInputField.text.toString()
            val password = binding.passwordInputField.text.toString()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            binding.pbLogin.visibility = View.VISIBLE

            loginViewModel.login(username, password).observe(this) { result ->
                binding.pbLogin.visibility = View.GONE
                if (result != null) {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLogin.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            Log.d("LoginActivity", "Login result: ${result.data}")
                            processLogin(result.data)
                        }
                        is Result.Error -> {
                            Log.e("LoginActivity", "Login error: ${result.error}")
                            Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        val isFromSignUp: Boolean = intent.getBooleanExtra("is_from_sign_up", false)
        if (isFromSignUp) {
            handleOnBackPressed()
        }
    }

    private fun navigateToBottomActivity() {
        startActivity(Intent(this, BottomActivity::class.java))
        finish()
    }

    private fun processLogin(data: ResponseLogin) {
        Log.d("LoginActivity", "Processing login with data: $data")
        val dataLogin = data.dataLogin
        if (dataLogin != null) {
            Log.d("LoginActivity", "Login successful, saving token and navigating to BottomActivity")
            Preference.saveToken(dataLogin.token, this)
            Preference.saveUsername(dataLogin.username, this)
            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_LONG).show()

            // Save cookies
            CoroutineScope(Dispatchers.IO).launch {
                val cookies = CookieInterceptor.getCookies(this@LoginActivity)
                if (cookies != null) {
                    Log.d("LoginActivity", "Cookies to be saved: $cookies")
                    saveCookiesToPreferences(cookies.toList())
                } else {
                    Log.e("LoginActivity", "No cookies found")
                }
            }

            startActivity(Intent(this, BottomActivity::class.java))
            finish()
        } else {
            Log.e("LoginActivity", "Login failed: dataLogin is null")
            Toast.makeText(this, "Login failed: ${data.message}", Toast.LENGTH_LONG).show()
        }
    }



    private fun saveCookiesToPreferences() {
        val cookies = CookieInterceptor.getCookies(this)
        if (cookies != null) {
            val sharedPreferences = getSharedPreferences("PREF_COOKIES", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putStringSet("COOKIE_KEY", cookies)
            editor.apply()
        }
    }

    private fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun saveCookiesToPreferences(cookies: List<String>) {
        val sharedPreferences = getSharedPreferences("PREF_COOKIES", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("COOKIE_KEY", cookies.toSet())
        editor.apply()
    }

    private fun getCookiesFromPreferences(): Set<String>? {
        val sharedPreferences = getSharedPreferences("PREF_COOKIES", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("COOKIE_KEY", null)
    }

}
