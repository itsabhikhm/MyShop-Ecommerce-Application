package dev.abhikhm.storeadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dev.abhikhm.storeadmin.R
import dev.abhikhm.storeadmin.adapter.AllOrderAdapter
import dev.abhikhm.storeadmin.model.AllOrderModel

class AllOrderActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<AllOrderModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_order)
        list = ArrayList()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        Firebase.firestore.collection("allOrders").get().addOnSuccessListener {
            list.clear()
            for(doc in it){
                val data = doc.toObject(AllOrderModel::class.java)
                list.add(data!!)
            }
            recyclerView.adapter = AllOrderAdapter(list,this)
        }
    }

}