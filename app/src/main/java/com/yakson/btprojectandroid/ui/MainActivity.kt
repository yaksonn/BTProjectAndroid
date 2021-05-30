package com.yakson.btprojectandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.ui.prelogin.LoginActivity
import com.yakson.btprojectandroid.ui.questions.AddQuestionActivity
import com.yakson.btprojectandroid.ui.questions.QuestionListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun mainActivityButtonClicks(view: View?) {
        var intent: Intent? = null
        when (view?.id) {
            R.id.newQuestionButton -> {
                intent = Intent(this@MainActivity, AddQuestionActivity::class.java)
            }
            R.id.getQuestionsButton -> {
                intent = Intent(this@MainActivity, QuestionListActivity::class.java)
            }
            R.id.createExamButton -> {
                intent = Intent(this@MainActivity, MainActivity::class.java)
            }
            R.id.examSettingsButton -> {
                intent = Intent(this@MainActivity, MainActivity::class.java)
            }
            R.id.logOutButton -> {
                intent = Intent(this@MainActivity, LoginActivity::class.java)
            }
        }
        startActivity(intent)
    }
}