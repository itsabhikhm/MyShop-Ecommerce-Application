package dev.abhikhm.store.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getCategory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.abhikhm.store.R
import dev.abhikhm.store.activity.AddressActivity
import dev.abhikhm.store.activity.AllCategoryActivity
import dev.abhikhm.store.adapter.ProductAdapter
import dev.abhikhm.store.databinding.FragmentHomeBinding
import dev.abhikhm.store.model.AddProductModel
import dev.abhikhm.store.model.CategoryModel
import dev.abhikhm.store.roomdb.ProductDao
import dev.abhikhm.store.roomdb.ProductModel
import dev.abhikhm.storeadmin.adapter.CategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentHomeBinding.inflate(layoutInflater)
       val preferences = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
       if(preferences.getBoolean("isCart",false))
           findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

       getSliderImage()
       getCategory()
       getProducts()

       binding.allCategory.setOnClickListener {
           val intent = Intent(context, AllCategoryActivity::class.java)
           startActivity(intent)
       }
       //binding.root.findViewById<Button>(R.id.button2).setOnClickListener {
          // WRITE CODE HERE TO ADD TO CART FROM HOME SCREEN
       //}

       return binding.root
    }
    

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener{
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }
    }

    private fun getProducts() {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
               binding.productRecycler.adapter = ProductAdapter(requireContext()!!,list)
            }
    }

    private fun getCategory() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext()!!,list)
            }
    }


}