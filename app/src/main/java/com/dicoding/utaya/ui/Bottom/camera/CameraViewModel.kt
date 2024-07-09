package com.dicoding.utaya.ui.Bottom.camera

import androidx.lifecycle.ViewModel
import com.dicoding.utaya.data.DataRepository
import com.dicoding.utaya.data.Result
import com.dicoding.utaya.data.response.camera.ResponsePredict
import kotlinx.coroutines.flow.Flow
import java.io.File

class CameraViewModel(private val repository: DataRepository) : ViewModel() {

    fun uploadImg(file: File): Flow<Result<ResponsePredict>> {
        return repository.uploadImg(file)
    }
}
