package com.example.userregistration.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.example.userregistration.R
import com.example.userregistration.databinding.ActivityHomeBinding
import com.example.userregistration.model.User

class HomeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = IntentCompat.getSerializableExtra(intent, EXTRA_USER, User::class.java)
        if (user == null) {          // phòng khi mở Home mà không có dữ liệu
            finish()
            return
        }
        showUser(user)

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun showUser(user: User) = with(binding) {
        tvWelcome.text = getString(R.string.home_welcome, user.fullName)
        tvName.text = getString(R.string.home_name, user.fullName)
        tvEmail.text = getString(R.string.home_email, user.email)
        tvPhone.text = getString(R.string.home_phone, user.phone)
        tvGender.text = getString(R.string.home_gender, user.gender)
        tvPassword.text = getString(R.string.home_password, "•".repeat(user.password.length))
    }
}