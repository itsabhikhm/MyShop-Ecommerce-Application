package dev.abhikhm.storeadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.abhikhm.storeadmin.databinding.AllOrderItemLayoutBinding
import dev.abhikhm.storeadmin.model.AllOrderModel

class AllOrderAdapter(val list: ArrayList<AllOrderModel>,val context: Context)
    :RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>(){
    inner class AllOrderViewHolder (val binding : AllOrderItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
           AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = "Price : ₹"+list[position].price
        holder.binding.orderId.text = "Order Id : "+list[position].orderId
        holder.binding.userId.text = "Customer Id : "+list[position].userId


        val id = list[position].productId.toString() // Getting product image from product id
        val Uname = list[position].userId.toString()   //getting userName from user Id

        Firebase.firestore.collection("products").document(id).get().addOnSuccessListener{
            val obj = it.get("productCoverImg") as String
            Glide.with(context).load(obj).into(holder.binding.productImage)
        }

        Firebase.firestore.collection("users").document(Uname).get().addOnSuccessListener{
            val obj = it.get("userName") as String
            holder.binding.CName.text = "Customer Name : "+obj
        }


        holder.binding.cancleButton.setOnClickListener {

            //holder.binding.proceedButton.text = "Canceled"
            holder.binding.proceedButton.isEnabled = false
            updateStatus("Canceled",list[position].orderId!!)
        }

        when(list[position].status){
            "Ordered" -> {

                holder.binding.proceedButton.text = "Dispatched ?"

                holder.binding.proceedButton.setOnClickListener {
                    updateStatus("Dispatched",list[position].orderId!!)
                }
            }

            "Dispatched" -> {
                holder.binding.proceedButton.text = "Delivered ?"

                holder.binding.proceedButton.setOnClickListener {
                    updateStatus("Delivered",list[position].orderId!!)
                }
            }
            "Delivered" -> {
                holder.binding.cancleButton.visibility = GONE
                holder.binding.proceedButton.text = "Already Delivered"

            }
            "Canceled" -> {
                holder.binding.proceedButton.isEnabled = false
            }
        }
    }

    fun updateStatus(str:String,doc:String){
        val data = hashMapOf<String,Any>()
        data["status"] = str
        Firebase.firestore.collection("allOrders")
            .document(doc).update(data).addOnSuccessListener {
                Toast.makeText(context, "Status updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}