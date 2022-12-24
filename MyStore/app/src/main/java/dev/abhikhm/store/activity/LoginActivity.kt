package dev.abhikhm.store.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.BulletSpan
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dev.abhikhm.store.R
import dev.abhikhm.store.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button5.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        binding.button3.setOnClickListener {
            if(binding.userNumber.text!!.isEmpty())
                Toast.makeText(this, "Please provide number", Toast.LENGTH_SHORT).show()
            else
                sendOtp(binding.userNumber.text.toString())
        }
    }
    private lateinit var builder:AlertDialog

    private fun sendOtp(number: String) {

        builder = AlertDialog.Builder(this)
            .setTitle("Loading....")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }
        override fun onVerificationFailed(e: FirebaseException) {

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken)
        {
            val intent = Intent(this@LoginActivity,OTPActivity::class.java)
            builder.dismiss()
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number",binding.userNumber.text.toString())
            startActivity(intent)
        }
    }
}