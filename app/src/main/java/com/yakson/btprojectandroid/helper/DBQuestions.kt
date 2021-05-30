package com.yakson.btprojectandroid.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.utility.userID
import java.util.ArrayList

class DBQuestions(context: Context?) :
    SQLiteOpenHelper(context, "examQuestions.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement = "CREATE TABLE QUESTIONS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "QUESTION TEXT," +
                "A TEXT," +
                "B TEXT," +
                "C TEXT," +
                "D TEXT," +
                "ANSWER TEXT," +
                "userId INTEGER);"
        db!!.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("Not yet implemented")
    }

    fun addNewQuestion(questionsModel: QuestionsModel): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("QUESTION", questionsModel.question)
        cv.put("A", questionsModel.optA)
        cv.put("B", questionsModel.optB)
        cv.put("C", questionsModel.optC)
        cv.put("D", questionsModel.optD)
        cv.put("ANSWER", questionsModel.answer)
        cv.put("userId", questionsModel.userId)
        val insert = db.insert("QUESTIONS", null, cv)
        db.close()
        return insert != -1L
    }

    fun deleteQuestion(questionId: Int) {
        val db = this.writableDatabase
        db.delete("QUESTIONS", "id = $questionId", null)
    }

    fun getQuestions(): ArrayList<QuestionsModel>? {
        val questions: ArrayList<QuestionsModel> = ArrayList<QuestionsModel>()
        val query = "SELECT * FROM QUESTIONS WHERE USERID = $userID;"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val questionId = cursor.getInt(0)
                val questionText = cursor.getString(1)
                val a = cursor.getString(2)
                val b = cursor.getString(3)
                val c = cursor.getString(4)
                val d = cursor.getString(5)
                val answer = cursor.getString(6)
                val userId = cursor.getInt(7)
                val question = QuestionsModel(
                    questionId,
                    userId,
                    questionText,
                    a,
                    b,
                    c,
                    d,
                    answer
                )
                questions.add(question)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return questions
    }

    @SuppressLint("Recycle")
    fun getAQuestionWID(questionId: Int): QuestionsModel? {
        val question: QuestionsModel
        val db = this.readableDatabase
        val query = "SELECT * FROM QUESTIONS WHERE ID = $questionId;"
        val cursor = db.rawQuery(query, null)
        cursor?.moveToFirst()
        val questionText = cursor!!.getString(1)
        val a = cursor.getString(2)
        val b = cursor.getString(3)
        val c = cursor.getString(4)
        val d = cursor.getString(5)
        val answer = cursor.getString(6)
        val userId = cursor.getInt(7)
        question = QuestionsModel(
            questionId,
            userId,
            questionText,
            a,
            b,
            c,
            d,
            answer
        )
        return question
    }

    fun editQuestion(questionId: Int, question: QuestionsModel) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("QUESTION", question.question)
        cv.put("A", question.optA)
        cv.put("B", question.optB)
        cv.put("C", question.optC)
        cv.put("D", question.optD)
        cv.put("ANSWER", question.answer)
        cv.put("userId", question.userId)
        db.update("questions", cv, "ID=$questionId", null)
        db.close()
    }


}