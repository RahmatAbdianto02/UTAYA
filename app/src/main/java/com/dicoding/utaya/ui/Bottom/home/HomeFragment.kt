package com.dicoding.utaya.ui.Bottom.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.utaya.R
import com.dicoding.utaya.databinding.FragmentHomeBinding
import com.dicoding.utaya.ui.Bottom.produk.ListProdukAdapter
import com.dicoding.utaya.ui.Bottom.produk.Produk
import com.dicoding.utaya.ui.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvProduk: RecyclerView
    private val list = ArrayList<Produk>()
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
        list.addAll(getListProduk())
        showRecyclerList()
        return root
    }

    private fun getListProduk(): ArrayList<Produk> {
        val dataMerk = resources.getStringArray(R.array.data_merk)
//        val dataHarga = resources.getStringArray(R.array.data_harga)
        val dataLink = resources.getStringArray(R.array.data_link)
        val dataArtikel = resources.getStringArray(R.array.data_article)
        val dataFoto = resources.obtainTypedArray(R.array.data_foto)
        val listHero = ArrayList<Produk>()
        for (i in dataMerk.indices) {
            val hero = Produk(dataMerk[i], dataLink[i], dataArtikel[i], dataFoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        return listHero

    }

    private fun showRecyclerList() {
        rvProduk.layoutManager = LinearLayoutManager(requireContext())
        val listProdukAdapter = ListProdukAdapter(list)
        rvProduk.adapter = listProdukAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
