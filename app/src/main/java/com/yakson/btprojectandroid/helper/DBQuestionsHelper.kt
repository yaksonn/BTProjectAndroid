package com.yakson.btprojectandroid.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.utility.userID
import java.util.ArrayList

class DBQuestionsHelper(context: Context?) :
    SQLiteOpenHelper(context, "examQuestions.dataBase", null, 1) {

    override fun onCreate(dataBase: SQLiteDatabase?) {
        val createTableStatement = "CREATE TABLE QUESTIONS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "QUESTION TEXT," +
                "A TEXT," +
                "B TEXT," +
                "C TEXT," +
                "D TEXT," +
                "ANSWER TEXT," +
                "userId INTEGER);"
        dataBase!!.execSQL(createTableStatement)
    }

    override fun onUpgrade(dataBase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("Not yet implemented")
    }

    fun addNewQuestion(questionsModel: QuestionsModel): Boolean {
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("QUESTION", questionsModel.question)
        contentValues.put("A", questionsModel.optA)
        contentValues.put("B", questionsModel.optB)
        contentValues.put("C", questionsModel.optC)
        contentValues.put("D", questionsModel.optD)
        contentValues.put("ANSWER", questionsModel.answer)
        contentValues.put("userId", questionsModel.userId)
        val insert = dataBase.insert("QUESTIONS", null, contentValues)
        dataBase.close()
        return insert != -1L
    }
    
    
    fun editQuestion(questionId: Int, question: QuestionsModel) {
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("QUESTION", question.question)
        contentValues.put("A", question.optA)
        contentValues.put("B", question.optB)
        contentValues.put("C", question.optC)
        contentValues.put("D", question.optD)
        contentValues.put("ANSWER", question.answer)
        contentValues.put("userId", question.userId)
        dataBase.update("questions", contentValues, "ID=$questionId", null)
        dataBase.close()
    }
    
    fun deleteQuestion(questionId: Int) {
        val dataBase = this.writableDatabase
        dataBase.delete("QUESTIONS", "id = $questionId", null)
    }

    fun getQuestions(): ArrayList<QuestionsModel>? {
        val questionsModel: ArrayList<QuestionsModel> = ArrayList<QuestionsModel>()
        val query = "SELECT * FROM QUESTIONS WHERE USERID = $userID;"
        val dataBase = this.readableDatabase
        val cursor = dataBase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val questionId = cursor.getInt(0)
                val questionText = cursor.getString(1)
                val optA = cursor.getString(2)
                val optB = cursor.getString(3)
                val optC = cursor.getString(4)
                val optD = cursor.getString(5)
                val questionAnswer = cursor.getString(6)
                val userId = cursor.getInt(7)
                val question = QuestionsModel(
                    questionId,
                    userId,
                    questionText,
                    optA,
                    optB,
                    optC,
                    optD,
                    questionAnswer
                )
                questionsModel.add(question)
            } while (cursor.moveToNext())
        }
        cursor.close()
        dataBase.close()
        return questionsModel
    }

    @SuppressLint("Recycle")
    fun getQuestionWithId(questionId: Int): QuestionsModel? {
        val questionModel: QuestionsModel
        val dataBase = this.readableDatabase
        val query = "SELECT * FROM QUESTIONS WHERE ID = $questionId;"
        val cursor = dataBase.rawQuery(query, null)
        cursor?.moveToFirst()
        val questionText = cursor!!.getString(1)
        val optA = cursor.getString(2)
        val optB = cursor.getString(3)
        val optC = cursor.getString(4)
        val optD = cursor.getString(5)
        val questionAnswer = cursor.getString(6)
        val userId = cursor.getInt(7)
        questionModel = QuestionsModel(
            questionId,
            userId,
            questionText,
            optA,
            optB,
            optC,
            optD,
            questionAnswer
        )
        return questionModel
    }

    


}