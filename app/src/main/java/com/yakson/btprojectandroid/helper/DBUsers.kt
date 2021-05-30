package com.yakson.btprojectandroid.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yakson.btprojectandroid.model.UserModel
import java.util.ArrayList

class DBUsers(context: Context?) :
    SQLiteOpenHelper(context, "users.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement =
            "CREATE TABLE USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "USERNAME TEXT," +
                    "EMAIL TEXT," +
                    "PHONE TEXT," +
                    "PASSWORD TEXT);"
        db!!.execSQL(createTableStatement)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }


    fun addNewUser(userModel: UserModel): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("USERNAME", userModel.userNameSurname)
        cv.put("EMAIL", userModel.userEmail)
        cv.put("PHONE", userModel.userPhoneNumber)
        cv.put("PASSWORD", userModel.userPassword)
        val insert = db.insert("USERS", null, cv)
        db.close()
        return insert != -1L
    }

    fun checkUsersFromDb(): ArrayList<UserModel>? {
        val users: ArrayList<UserModel> = ArrayList()
        val query = "SELECT * FROM USERS;"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
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
        db.close()
        return users
    }
}