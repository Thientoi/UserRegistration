package com.example.userregistration.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.userregistration.R
import com.example.userregistration.databinding.ActivityRegisterBinding
import com.example.userregistration.validator.FormValidator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClearErrorOnType()
        binding.btnRegister.setOnClickListener { onRegisterClicked() }
    }

    /** Người dùng gõ lại thì lỗi đỏ biến mất ngay. */
    private fun setupClearErrorOnType() {
        clearErrorOnType(binding.tilFullName, binding.etFullName)
        clearErrorOnType(binding.tilEmail, binding.etEmail)
        clearErrorOnType(binding.tilPhone, binding.etPhone)
        clearErrorOnType(binding.tilPassword, binding.etPassword)
        clearErrorOnType(binding.tilConfirmPassword, binding.etConfirmPassword)
    }

    private fun clearErrorOnType(til: TextInputLayout, et: TextInputEditText) {
        et.doAfterTextChanged { if (til.error != null) til.error = null }
    }

    private fun onRegisterClicked() {
        val errorCount = validateForm()
        if (errorCount > 0) {
            // FAIL → Snackbar báo lỗi
            Snackbar.make(
                binding.root,
                getString(R.string.snackbar_fail, errorCount),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        // SUCCESS → sẽ chuyển sang Home ở commit 6
    }

    /** Kiểm tra tất cả trường, trả về số lỗi. Focus vào ô lỗi đầu tiên. */
    private fun validateForm(): Int {
        var errorCount = 0
        var firstErrorView: View? = null

        fun fail(til: TextInputLayout, field: View, @StringRes msg: Int) {
            til.error = getString(msg)
            errorCount++
            if (firstErrorView == null) firstErrorView = field
        }

        val name = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val pass = binding.etPassword.text.toString()
        val confirm = binding.etConfirmPassword.text.toString()

        if (!FormValidator.isValidName(name))
            fail(binding.tilFullName, binding.etFullName, R.string.err_name)
        if (!FormValidator.isValidEmail(email))
            fail(binding.tilEmail, binding.etEmail, R.string.err_email)
        if (!FormValidator.isValidPhone(phone))
            fail(binding.tilPhone, binding.etPhone, R.string.err_phone)
        if (!FormValidator.isValidPassword(pass))
            fail(binding.tilPassword, binding.etPassword, R.string.err_password)
        if (!FormValidator.isValidConfirm(pass, confirm))
            fail(binding.tilConfirmPassword, binding.etConfirmPassword, R.string.err_confirm)

        firstErrorView?.requestFocus()
        return errorCount
    }
}