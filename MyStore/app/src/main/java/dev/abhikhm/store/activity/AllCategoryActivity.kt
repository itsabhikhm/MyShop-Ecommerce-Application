package dev.abhikhm.store.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.abhikhm.store.R
import dev.abhikhm.store.adapter.AllCategoryAdapter
import dev.abhikhm.store.model.CategoryModel
import dev.abhikhm.storeadmin.adapter.CategoryAdapter

class AllCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_category)


        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                findViewById<RecyclerView>(R.id.allcategoryRecycler).adapter = AllCategoryAdapter(this,list)

            }
    }


}