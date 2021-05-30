package com.yakson.btprojectandroid.ui.prelogin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.model.UserModel
import com.yakson.btprojectandroid.helper.DBUsers
import com.yakson.btprojectandroid.ui.MainActivity
import com.yakson.btprojectandroid.utility.toast
import com.yakson.btprojectandroid.utility.userID
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var userNameOrPassTextInputLayout: TextInputLayout? = null
    private var passwordTextInputLayout: TextInputLayout? = null
    private var userNameEditText: TextInputEditText? = null
    private var passwordEditText: TextInputEditText? = null
    private var loginAttempt: Int? = 0
    private var userModel: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        userNameOrPassTextInputLayout = findViewById(R.id.userNameOrPassTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        userNameEditText = findViewById(R.id.userNameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
    }


    fun loginActivityButtonClicks(view: View?) {
        when (view?.id) {
            R.id.loginButton -> {
                if (checkField()) {
                    val db = DBUsers(this@LoginActivity)
                    val users: ArrayList<UserModel>? = db.checkUsersFromDb()
                    users?.forEach { userModel ->
                        if (userModel.userEmail.equals(userNameEditText?.text.toString().trim())
                            && userModel.userPassword == passwordEditText?.text.toString()
                        ) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            userID = userModel.userId
                            startActivity(intent)
                            return
                        }
                    }
                    loginAttempt = loginAttempt?.plus(1)

                    if (loginAttempt!! >= 3) {
                        toast("3 defa hatalı giriş yapıldı")
                        finish()
                    }

                }
            }
            R.id.registerButton -> {

                val intentRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    private fun clearErrorText() {
        userNameOrPassTextInputLayout?.error = ""
        passwordTextInputLayout?.error = ""
    }

    private fun checkField(): Boolean {
        clearErrorText()
        var result = true
        if (userNameEditText?.text.toString().isEmpty()) {
            userNameOrPassTextInputLayout?.error = getString(R.string.login_error_email)
            result = false
        }
        if (passwordEditText?.text.toString().isEmpty()) {
            passwordTextInputLayout?.error = "getString(R.string.signup_error_password)"
            result = false
        }
        return result
    }
}