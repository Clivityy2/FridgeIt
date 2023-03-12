package com.example.fridgeit2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fridgeit2.R
import com.example.fridgeit2.data.Item
import com.example.fridgeit2.databinding.ItemPreviewBinding

class ItemRecyclerViewAdapter(
    private val onDeleteCallback: (item: Item) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    var items: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPreviewBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_preview, parent, false)
        return ItemViewHolder(binding, onDeleteCallback)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}

class ItemViewHolder(
    private val binding: ItemPreviewBinding,
    private val onDeleteCallback: (item: Item) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item, position: Int) {
        binding.tvItemExpiry.text = item.itemExpiryDate.toString()
        binding.tvitemName.text = item.itemName
        binding.ivBinIcon.setOnClickListener {
            onDeleteCallback(item)
        }
    }
}
