package com.dicoding.utaya.ui.Bottom.camera.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.utaya.data.response.camera.RecommendationsItem
import com.dicoding.utaya.data.response.camera.ResponsePredict
import com.dicoding.utaya.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val responsePredict: ResponsePredict? = intent.getParcelableExtra("RESPONSE_PREDICT")
        responsePredict?.let { response ->
            binding.tipeSkin.text = "Tipe Kulit: ${response.skinType}"
            binding.skinPercentage.text = "Persentase: ${response.skinTypePercentage}%"

            setupRecyclerView(response.recommendations)
        }
    }

    private fun setupRecyclerView(recommendations: List<RecommendationsItem>) {
        adapter = ResultAdapter(recommendations)
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        binding.rvResult.adapter = adapter
    }
}
