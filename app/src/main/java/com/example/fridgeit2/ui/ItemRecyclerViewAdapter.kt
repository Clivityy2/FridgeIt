package com.example.fridgeit2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fridgeit2.R
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.databinding.ItemPreviewBinding

class ItemRecyclerViewAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemPreviewBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_preview,parent,false)
        return ItemViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}

class ItemViewHolder(val binding: ItemPreviewBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        binding.tvItemExpiry.text = item.itemExpiryDate
        binding.tvitemName.text = item.itemName
    }
}