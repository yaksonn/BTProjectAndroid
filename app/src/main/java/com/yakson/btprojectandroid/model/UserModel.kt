package com.yakson.btprojectandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("userId") @Expose var userId: Int = 0,
    @SerializedName("userNameSurname") @Expose var userNameSurname: String,
    @SerializedName("userEmail") @Expose var userEmail: String,
    @SerializedName("userPhoneNumber") @Expose var userPhoneNumber: String,
    @SerializedName("userPassword") @Expose var userPassword: String
)