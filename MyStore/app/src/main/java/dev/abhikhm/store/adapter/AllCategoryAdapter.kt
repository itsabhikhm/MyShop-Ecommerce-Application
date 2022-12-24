package dev.abhikhm.store.adapter

import dev.abhikhm.store.model.CategoryModel
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.abhikhm.store.R
import dev.abhikhm.store.activity.CategoryActivity
import dev.abhikhm.store.databinding.LayoutAllCategoryItemBinding


class AllCategoryAdapter (var context: Context, val list: ArrayList<CategoryModel>)
    : RecyclerView.Adapter<AllCategoryAdapter.CategoryViewHolder>()
{
    inner class CategoryViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var binding = LayoutAllCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_all_category_item,parent,false))
    }

    override fun onBindViewHolder(holder: AllCategoryAdapter.CategoryViewHolder, position: Int) {
        // Glide is used Here
        holder.binding.textView.text = list[position].cat
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)

        holder.itemView.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat",list[position].cat)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
