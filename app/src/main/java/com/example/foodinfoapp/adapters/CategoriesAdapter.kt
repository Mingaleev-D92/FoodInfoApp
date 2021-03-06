package com.example.foodinfoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodinfoapp.databinding.CategoryItemsBinding
import com.example.foodinfoapp.models.Category

/**
 * @author Mingaleev D. 16.07.2022
 **/
class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()

    // прослушиватель кликов при нажатии на категории(элемент)
    var onItemClick : ((Category)->Unit)? = null

    fun setCategoryList(categoriesList:List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category> /* = java.util.ArrayList<com.example.foodapp.pojo.Category> */
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}