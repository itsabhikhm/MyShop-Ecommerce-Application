package dev.abhikhm.store.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import dev.abhikhm.store.R
import dev.abhikhm.store.activity.AddressActivity
import dev.abhikhm.store.activity.ProductDetailActivity
import dev.abhikhm.store.adapter.CartAdapter
import dev.abhikhm.store.databinding.FragmentCartBinding
import dev.abhikhm.store.databinding.FragmentHomeBinding
import dev.abhikhm.store.roomdb.AppDatabase
import dev.abhikhm.store.roomdb.ProductModel


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list:ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preferences = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()){ it ->
            binding.cartRecycler.adapter = CartAdapter(requireContext(),it)

            list.clear()
            for(data in it){
                list.add(data.productId)
            }
            totalCost(it)
        }

        return binding.root

    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for(item in data!!){
            total+=item.productSp!!.toInt()
        }
        binding.textView12.text = "Total item in cart is : ${data.size}"
        binding.textView13.text = "Total Amount : $total"

        if(data.size == 0) {
            binding.root.findViewById<ImageView>(R.id.emptyCart).visibility = View.VISIBLE
            binding.root.findViewById<TextView>(R.id.emptyCartText).visibility = View.VISIBLE
            binding.root.findViewById<CardView>(R.id.materialCardView2).visibility = View.GONE
        }
        else
        {
        binding.root.findViewById<ImageView>(R.id.emptyCart).visibility = View.GONE
        binding.root.findViewById<TextView>(R.id.emptyCartText).visibility = View.GONE
        binding.root.findViewById<CardView>(R.id.materialCardView2).visibility = View.VISIBLE
        binding.checkout.setOnClickListener {


            val intent = Intent(context, AddressActivity::class.java)
                val b = Bundle()
                b.putStringArrayList("productIds", list)
                b.putString("totalCost", total.toString())
                intent.putExtras(b)
                startActivity(intent)

        }
        }
    }


}