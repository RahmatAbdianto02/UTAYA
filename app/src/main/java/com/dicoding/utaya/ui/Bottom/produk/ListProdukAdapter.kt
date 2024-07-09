package com.dicoding.utaya.ui.Bottom.produk

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.utaya.R
import com.dicoding.utaya.data.response.produk.RecommendationsItem

class ListProdukAdapter(private val listProduk: ArrayList<RecommendationsItem>) : RecyclerView.Adapter<ListProdukAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ListViewHolder(view)
    }

    fun setProdukList(produk: List<RecommendationsItem>) {
        listProduk.clear()
        listProduk.addAll(produk)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val produk = listProduk[position]
        holder.bind(produk)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFoto: ImageView = itemView.findViewById(R.id.iv_card)
        val tvMerk: TextView = itemView.findViewById(R.id.tv_title_one)
//        val tvHarga: TextView = itemView.findViewById(R.id.tv_title_two)
        val tvLink: TextView = itemView.findViewById(R.id.tv_title_three)
        val tvArtikel: TextView = itemView.findViewById(R.id.tv_title_four)

        fun bind(produk: RecommendationsItem) {
            // Set the product image
            Glide.with(itemView.context)
                .load(produk.urlImage)
                .into(ivFoto)

            // Set the product name, limit to 15 words, and add "..."
            val productName = produk.productName
            val words = productName?.split(" ")
            if (words != null && words.size > 15) {
                 val limitedName = words.take(15).joinToString(" ") + "..."
                tvMerk.text = limitedName
            } else {
                tvMerk.text = productName
            }

            // Set Link text and click listener
            tvLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(produk.urlProduct))
                itemView.context.startActivity(intent)
            }

            // Set Artikel text and click listener
            tvArtikel.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(produk.urlArticle))
                itemView.context.startActivity(intent)
            }
        }
    }
}
