package com.example.userregistration.model

import java.io.Serializable

data class User(
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String,
    val gender: String
) : Serializable