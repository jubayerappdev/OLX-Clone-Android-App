package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.post

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creativeitinstitute.olxcloneanroidapp.databinding.ItemImageBinding

@Suppress("DEPRECATION")
class ProductImageAdapter() : RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {
    private var images = mutableListOf<Uri>()
    fun setImages(newImages: List<Uri>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    fun addImage(uri: Uri) {
        images.add(uri)
        notifyItemInserted(images.size - 1)
    }

    fun removeImage(position: Int) {
        if (position in images.indices) {
            images.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductImageViewHolder {
        val view = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductImageViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ProductImageViewHolder,
        position: Int
    ) {
        holder.bind(images[position])

    }

    override fun getItemCount(): Int {
        return images.size

    }

    inner class ProductImageViewHolder(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            Glide.with(itemView.context)
                .load(uri)
                .centerCrop()
                .into(binding.imageView)

            binding.btnRemove.setOnClickListener {
                removeImage(adapterPosition)
            }
        }
    }
    fun getImages(): List<Uri> = images.toList()
}