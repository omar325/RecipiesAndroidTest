package com.example.recipiestest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipiestest.R
import com.example.recipiestest.infrastructure.models.Meal

class RecipesAdapter(
    val onItemClickAction: (itemSelected: Meal) -> Unit
): RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    val itemList = AsyncListDiffer<Meal>(
        this,
        object: DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean =
                oldItem.isSameAs(newItem)

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean =
                oldItem.hasSameContentAs(newItem)
        })

    fun submitList(data: List<Meal>) =
        itemList.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recipe_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bind(itemList.currentList[position], onItemClickAction)

    override fun getItemCount(): Int =
        itemList.currentList.size

    class RecipeViewHolder(
        private val itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Meal, onItemClickAction: (itemSelected: Meal) -> Unit) {
            (itemView.findViewById(R.id.recipe_name) as TextView).text = item.strMeal
            (itemView.findViewById(R.id.recipe_category) as TextView).text = item.strCategory
            val imageView = itemView.findViewById(R.id.recipe_image) as ImageView
            Glide.with(itemView)
                .load(item.strMealThumb)
                .centerCrop()
                .into(imageView)
            itemView.setOnClickListener {
                onItemClickAction(item)
            }
        }
    }
}