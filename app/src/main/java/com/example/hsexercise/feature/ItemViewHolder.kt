package com.example.hsexercise.feature

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.request.CachePolicy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.hsexercise.databinding.HolderItemRowBinding

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewBinding: HolderItemRowBinding = HolderItemRowBinding.bind(itemView)

    fun bind(row: ItemRow) {
        viewBinding.nameTextView.text = row.authorName
        viewBinding.heightTextView.text = "Height: ${row.yDimen}"
        viewBinding.widthTextView.text = "Width: ${row.xDimen}"

        Glide.with(itemView.context)
            .load(row.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewBinding.imageView)


    }

}
