package com.dicoding.utaya.ui.Bottom.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.utaya.databinding.FragmentHistoryBinding
import com.dicoding.utaya.ui.ViewModelFactory

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var listHistoryAdapter: ListHistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory(requireContext())
        historyViewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)

        listHistoryAdapter = ListHistoryAdapter(emptyList())
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listHistoryAdapter
        }

        historyViewModel.listHistory.observe(viewLifecycleOwner) { history ->
            listHistoryAdapter.setHistoryList(history)
            binding.pbHistory.visibility = View.GONE
        }

        historyViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbHistory.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        historyViewModel.fetchHistory()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
