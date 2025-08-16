package com.creativeitinstitute.olxcloneanroidapp.views.dashboard.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creativeitinstitute.olxcloneanroidapp.R
import com.creativeitinstitute.olxcloneanroidapp.data.models.Product
import com.creativeitinstitute.olxcloneanroidapp.databinding.ItemImageBinding
import com.creativeitinstitute.olxcloneanroidapp.databinding.ItemProductBinding

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(){

    private var products = emptyList<Product>()

    fun setProducts(newProducts: List<Product>){
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.tvTitle.text = product.title
            binding.tvDescription.text = product.description
            binding.tvPrice.text = "$${product.price}"
            binding.tvLocation.text = product.location
            binding.tvCategory.text = product.category

            if (product.imageLink.isNotEmpty()){
                Glide.with(itemView.context)
                    .load(product.imageLink[0])
                    .centerCrop()
                    .placeholder(R.drawable.ic_add_image_24)
                    .into(binding.imageView)
            }
        }
    }
}