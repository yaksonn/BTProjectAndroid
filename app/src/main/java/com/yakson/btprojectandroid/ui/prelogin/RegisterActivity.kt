package com.yakson.btprojectandroid.ui.prelogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.model.UserModel
import com.yakson.btprojectandroid.helper.DBUsersHelper
import com.yakson.btprojectandroid.utility.toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yakson.btprojectandroid.utility.userNAME
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private var nameTextInputLayout: TextInputLayout? = null
    private var phoneTextInputLayout: TextInputLayout? = null
    private var emailTextInputLayout: TextInputLayout? = null
    private var passwordTextInputLayout: TextInputLayout? = null
    private var passwordSecondTextInputLayout: TextInputLayout? = null

    private var nameRegisterEditText: TextInputEditText? = null
    private var phoneRegisterEditText: TextInputEditText? = null
    private var emailRegisterEditText: TextInputEditText? = null
    private var passwordRegisterEditText: TextInputEditText? = null
    private var passwordSecondRegisterEditText: TextInputEditText? = null

    private var dbUsers: DBUsersHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        nameTextInputLayout = findViewById(R.id.nameTextInputLayout)
        phoneTextInputLayout = findViewById(R.id.phoneTextInputLayout)
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        passwordSecondTextInputLayout = findViewById(R.id.passwordSecondTextInputLayout)

        nameRegisterEditText = findViewById(R.id.nameRegisterEditText)
        phoneRegisterEditText = findViewById(R.id.phoneRegisterEditText)
        emailRegisterEditText = findViewById(R.id.emailRegisterEditText)
        passwordRegisterEditText = findViewById(R.id.passwordRegisterEditText)
        passwordSecondRegisterEditText = findViewById(R.id.passwordSecondRegisterEditText)

    }


    fun registerActivityButtonClicks(view: View?) {
        when (view?.id) {
            R.id.registerButton -> {
                if (checkField()) {
                    if (checkDB()) {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                        toast("Kullanıcı kaydı başarılı.Şimdi giriş yapabilirsin.")

                    }
                }
            }
            R.id.backButtonImageView -> {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }

    private fun clearErrorText() {
        nameTextInputLayout?.error = ""
        phoneTextInputLayout?.error = ""
        emailTextInputLayout?.error = ""
        passwordTextInputLayout?.error = ""
        passwordSecondTextInputLayout?.error = ""
    }

    private fun checkField(): Boolean {
        clearErrorText()
        var result: Boolean = true

        if (nameRegisterEditText?.text.toString().isEmpty()) {
            toast(getString(R.string.signup_error_name_surname))
            result = false
        }
        if (phoneRegisterEditText?.text.toString().isEmpty()) {
            toast(getString(R.string.signup_error_phone))
            result = false
        }
        if (emailRegisterEditText?.text.toString().isEmpty()) {
            toast(getString(R.string.signup_error_email))
            result = false
        }
        if (passwordRegisterEditText?.text.toString() != passwordSecondRegisterEditText?.text.toString()) {
            toast(getString(R.string.signup_error_password_repeat))
            result = false
        }
        return result
    }

    private fun checkDB(): Boolean {
        var result: Boolean = true
        dbUsers =
            DBUsersHelper(this@RegisterActivity)
        val users: ArrayList<UserModel>? = dbUsers!!.checkUsersFromDb()
        if (!users.isNullOrEmpty()) {
            users.forEach { userModel ->
                if (userModel.userEmail == emailRegisterEditText?.text.toString()
                ) {
                    toast("Bu kullanıcı adı kayıtlı, lütfen başka bir kullanıcı adını deneyiniz.")
                    result = false
                }
            }
        }

        val userNameSurname: String = nameRegisterEditText?.text.toString()
        val userEmail: String = emailRegisterEditText?.text.toString()
        val userPhoneNumber: String = phoneRegisterEditText?.text.toString()
        val userPassword: String = passwordRegisterEditText?.text.toString()

        val newUser = UserModel(-1, userNameSurname, userEmail, userPhoneNumber, userPassword)

        dbUsers?.addNewUser(newUser)
        userNAME = newUser.userNameSurname
        dbUsers?.checkUsersFromDb()

        return result
    }
}