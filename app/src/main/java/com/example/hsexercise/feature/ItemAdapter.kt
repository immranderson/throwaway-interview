package com.example.hsexercise.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hsexercise.databinding.HolderItemRowBinding
import java.util.ArrayList

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private val items = ArrayList<ItemRow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =  HolderItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(newItems: List<ItemRow>) { //with more time I would configure DiffUtils
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
