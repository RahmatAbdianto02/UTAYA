package com.dicoding.utaya.ui.Bottom.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.utaya.data.Result
import com.dicoding.utaya.data.response.camera.ResponsePredict
import com.dicoding.utaya.data.utils.Preference
import com.dicoding.utaya.databinding.FragmentCameraBinding
import com.dicoding.utaya.ui.Bottom.camera.UploadUtils.reduceFileImage
import com.dicoding.utaya.ui.ViewModelFactory
import kotlinx.coroutines.launch

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraViewModel: CameraViewModel

    private var imageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                dispatchTakePictureIntent()
            } else {
                toast("Permission denied")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraViewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(CameraViewModel::class.java)
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cameraButton.setOnClickListener { checkCameraPermission() }
        binding.galleryButton.setOnClickListener { dispatchSelectPictureIntent() }
        binding.uploadButton.setOnClickListener { uploadImg() }

        return root
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                dispatchTakePictureIntent()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                toast("Camera permission is required to take pictures")
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        imageUri = UploadUtils.getImageUri(requireContext())
        launchCamera.launch(imageUri)
    }

    private fun dispatchSelectPictureIntent() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            prevImg()
        } else {
            imageUri = null
        }
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            prevImg()
        } else {
            Log.d("Photo Picker", "No Media Selected")
        }
    }

    private fun prevImg() {
        imageUri?.let {
            Log.d("Image URI", "showImg: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImg() {
        if (imageUri == null) {
            toast("Pilih atau ambil gambar terlebih dahulu")
            return
        }

        imageUri?.let { uri ->
            val fileImage = UploadUtils.uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImg: ${fileImage.path}")
            val token = Preference.getToken(requireContext())

            if (token.isNullOrEmpty()) {
                toast("Token tidak tersedia. Harap login terlebih dahulu.")
                return
            } else {
                Log.d("Token Ada", token)
            }

            lifecycleScope.launch {
                cameraViewModel.uploadImg(fileImage).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            loading(true)
                        }
                        is Result.Success -> {
                            handleUploadSuccess(result.data)
                            loading(false)
                        }
                        is Result.Error -> {
                            toast(result.error)
                            loading(false)
                        }
                    }
                }
            }
        }
    }

    private fun handleUploadSuccess(response: ResponsePredict) {
        showToast("Upload success: ${response.skinType}")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun loading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
