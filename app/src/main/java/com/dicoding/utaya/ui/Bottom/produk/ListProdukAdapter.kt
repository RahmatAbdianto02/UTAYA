package com.dicoding.utaya.ui.Bottom.produk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.utaya.R

class ListProdukAdapter(private val listProduk: ArrayList<Produk>) : RecyclerView.Adapter<ListProdukAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ListViewHolder(view)
    }

//    fun setProdukList(produk: List<RecommendationsItem>) {
//        listProduk.clear()
//        listProduk.addAll(produk)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (merk, link, artikel, foto) = listProduk[position]
        holder.ivFoto.setImageResource(foto)
        holder.tvMerk.text = merk
//        holder.tvHarga.text = harga
        holder.tvLink.text = link
        holder.tvArtikel.text = artikel
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFoto: ImageView = itemView.findViewById(R.id.iv_card)
        val tvMerk: TextView = itemView.findViewById(R.id.tv_title_one)
//        val tvHarga: TextView = itemView.findViewById(R.id.tv_title_two)
        val tvLink: TextView = itemView.findViewById(R.id.tv_title_three)
        val tvArtikel: TextView = itemView.findViewById(R.id.tv_title_four)
    }
}
