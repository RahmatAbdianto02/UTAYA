package com.dicoding.utaya.ui.Bottom.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.utaya.R
import com.dicoding.utaya.data.response.history.HistorysItem

class ListHistoryAdapter(private var listHistory: List<HistorysItem>) : RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = listHistory[position]
        Glide.with(holder.itemView.context).load(history.urlImage).into(holder.ivFoto)
        holder.tvSkin.text = history.skinType
        holder.tvPercentage.text = "${history.skinTypePercentage}%"
    }

    fun setHistoryList(newListHistory: List<HistorysItem>) {
        listHistory = newListHistory
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFoto: ImageView = itemView.findViewById(R.id.iv_history)
        val tvSkin: TextView = itemView.findViewById(R.id.tv_result)
        val tvPercentage: TextView = itemView.findViewById(R.id.tv_percentage)
    }
}
