package com.yakson.btprojectandroid.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yakson.btprojectandroid.model.UserModel
import java.util.ArrayList

class DBUsersHelper(context: Context?) :
    SQLiteOpenHelper(context, "users.dataBase", null, 1) {

    override fun onCreate(dataBase: SQLiteDatabase?) {
        val createTableStatement =
            "CREATE TABLE USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "USERNAME TEXT," +
                    "EMAIL TEXT," +
                    "PHONE TEXT," +
                    "PASSWORD TEXT);"
        dataBase!!.execSQL(createTableStatement)
    }

    override fun onUpgrade(
        dataBase: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }


    fun addNewUser(userModel: UserModel): Boolean {
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("USERNAME", userModel.userNameSurname)
        contentValues.put("EMAIL", userModel.userEmail)
        contentValues.put("PHONE", userModel.userPhoneNumber)
        contentValues.put("PASSWORD", userModel.userPassword)
        val insert = dataBase.insert("USERS", null, contentValues)
        dataBase.close()
        return insert != -1L
    }

    fun checkUsersFromDb(): ArrayList<UserModel>? {
        val users: ArrayList<UserModel> = ArrayList()
        val query = "SELECT * FROM USERS;"
        val dataBase = this.readableDatabase
        val cursor = dataBase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val userId = cursor.getInt(0)
                val userName = cursor.getString(1)
                val userEmail = cursor.getString(2)
                val userPhone = cursor.getString(3)
                val userPassword = cursor.getString(4)
                val user = UserModel(userId, userName, userEmail, userPhone, userPassword)
                users.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        dataBase.close()
        return users
    }
}