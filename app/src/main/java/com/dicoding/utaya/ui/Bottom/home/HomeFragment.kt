package com.dicoding.utaya.ui.Bottom.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.utaya.data.response.produk.RecommendationsItem
import com.dicoding.utaya.databinding.FragmentHomeBinding
import com.dicoding.utaya.ui.Bottom.produk.ListProdukAdapter
import com.dicoding.utaya.ui.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvProduk: RecyclerView
    private val list = ArrayList<RecommendationsItem>()
    private lateinit var listProdukAdapter: ListProdukAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory(requireContext())
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        rvProduk = binding.rvProduk
        rvProduk.setHasFixedSize(true)
        rvProduk.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter
        listProdukAdapter = ListProdukAdapter(list)
        rvProduk.adapter = listProdukAdapter

        homeViewModel.listProduk.observe(viewLifecycleOwner) { produk ->
            listProdukAdapter.setProdukList(produk)
            binding.progressBar.visibility = View.GONE
        }

        homeViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        homeViewModel.fetchProduk()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
