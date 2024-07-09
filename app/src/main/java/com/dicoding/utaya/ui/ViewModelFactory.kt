package com.dicoding.utaya.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.utaya.data.di.Injection
import com.dicoding.utaya.ui.Bottom.camera.CameraViewModel
import com.dicoding.utaya.ui.Bottom.history.HistoryViewModel
import com.dicoding.utaya.ui.Bottom.home.HomeViewModel
import com.dicoding.utaya.ui.changePassword.ChangePwViewModel
import com.dicoding.utaya.ui.login.LoginViewModel
import com.dicoding.utaya.ui.register.RegisterViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = Injection.provideRepository(context)
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChangePwViewModel::class.java) -> {
                ChangePwViewModel(context, repository) as T  // Pass context and repository
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
