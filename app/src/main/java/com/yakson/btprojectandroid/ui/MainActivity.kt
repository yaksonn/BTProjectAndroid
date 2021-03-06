package com.yakson.btprojectandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.yakson.btprojectandroid.BuildConfig
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.ui.exam.CreateExamActivity
import com.yakson.btprojectandroid.ui.exam.ExamSettings
import com.yakson.btprojectandroid.ui.prelogin.LoginActivity
import com.yakson.btprojectandroid.ui.questions.AddQuestionActivity
import com.yakson.btprojectandroid.ui.questions.QuestionListActivity
import com.yakson.btprojectandroid.utility.userNAME

class MainActivity : AppCompatActivity() {

    private var welcomeUserTextView: TextView? = null
    private var versionInfoTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        welcomeUserTextView = findViewById(R.id.welcomeUserTextView)
        versionInfoTextView = findViewById(R.id.versionInfoTextView)
        welcomeUserTextView?.text = getString(R.string.welcome) + " " + userNAME
        versionInfoTextView?.text = "Version Code - " + BuildConfig.VERSION_NAME

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
                intent = Intent(this@MainActivity, CreateExamActivity::class.java)
            }
            R.id.examSettingsButton -> {
                intent = Intent(this@MainActivity, ExamSettings::class.java)
            }
            R.id.logOutButton -> {
                intent = Intent(this@MainActivity, LoginActivity::class.java)
            }
        }
        startActivity(intent)
    }
}