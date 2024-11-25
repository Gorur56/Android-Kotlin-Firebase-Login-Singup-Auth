package com.example.loginsingupauth.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginsingupauth.R
import com.example.loginsingupauth.databinding.ActivitySingupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class SingupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonSignup.setOnClickListener{
            val email = binding.EdittextSignupEmail.text.toString()
            val password = binding.EdittextSignupPassword.text.toString()
            val confirmPassword = binding.EdittextSignupConfirm.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if (password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Snackbar.make(binding.root, it.exception.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Snackbar.make(binding.root, "Password does not matched", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(binding.root, "Fields cannot be empty", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.textviewLoginRedirectText.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}