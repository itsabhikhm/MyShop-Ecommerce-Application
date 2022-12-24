package dev.abhikhm.store.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.abhikhm.store.R
import dev.abhikhm.store.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAddressBinding
    private lateinit var preferences :SharedPreferences
    private lateinit var builder:AlertDialog
    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost= intent.getStringExtra("totalCost")!!
        loadUserInfo()
        binding.proceed.setOnClickListener {

            builder = AlertDialog.Builder(this)
                .setTitle("Loading....")
                .setMessage("Please Wait")
                .setCancelable(false)
                .create()
            builder.show()

            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userPincode.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.userVillage.text.toString(),

            )
        }
    }

    private fun validateData(number: String, name: String, pinCode: String, city: String, state: String, village: String) {
        if(number.isEmpty()||state.isEmpty()||name.isEmpty()||pinCode.isEmpty()||city.isEmpty()||village.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

        }else
        {
            storeData(pinCode,city,state,village)
        }

    }

    private fun storeData(pinCode: String, city: String, state: String, village: String) {
        val map = hashMapOf<String,Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {

                builder.dismiss()
                val b = Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)


            }.addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.userPincode.setText(it.getString("pinCode"))
                binding.userState.setText(it.getString("state"))
            }.addOnFailureListener {

            }
    }
}