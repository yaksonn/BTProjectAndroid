package com.yakson.btprojectandroid.ui.questions

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.helper.DBQuestionsHelper
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.ui.MainActivity
import com.yakson.btprojectandroid.utility.toast
import com.yakson.btprojectandroid.utility.userID

class AddQuestionActivity : AppCompatActivity() {

    private var entryQuestionEditText: EditText? = null
    private var optAEditText: EditText? = null
    private var optBEditText: EditText? = null
    private var optCEditText: EditText? = null
    private var optDEditText: EditText? = null
    private var optionsRadioButtons: RadioGroup? = null
    private var questionAnswer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_edit_question)
        initViews()
    }

    private fun initViews() {
        entryQuestionEditText = findViewById(R.id.entryQuestionEditText)
        optAEditText = findViewById(R.id.optAEditText)
        optBEditText = findViewById(R.id.optBEditText)
        optCEditText = findViewById(R.id.optCEditText)
        optDEditText = findViewById(R.id.optDEditText)
        optionsRadioButtons = findViewById(R.id.optionsRadioButtons)

        optionsRadioButtons?.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId) as RadioButton
            questionAnswer = radioButton.text.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun addQuestionActivityButtonClicks(view: View?) {
        when (view?.id) {
            R.id.saveQuestionButton -> {

                if (checkQuestionAndOptions()){
                    val dbQuestions = DBQuestionsHelper(this@AddQuestionActivity)
                    val question = QuestionsModel(
                        -1, userID, entryQuestionEditText?.text.toString().trim(),
                        optAEditText?.text.toString().trim(),
                        optBEditText?.text.toString().trim(),
                        optCEditText?.text.toString().trim(),
                        optDEditText?.text.toString().trim(),
                        questionAnswer
                    )
                    dbQuestions.addNewQuestion(question)
                    val intent = Intent(this@AddQuestionActivity, MainActivity::class.java)
                    startActivity(intent)
                    toast("Soru başarılı bir şekilde eklendi.")
                }

            }
            R.id.backButtonImageView -> {
                onBackPressed()
                finish()
            }
        }
    }

    private fun checkQuestionAndOptions(): Boolean {
        var result = true
        val questions: String = entryQuestionEditText?.text.toString()
        val optA: String = optAEditText?.text.toString()
        val optB: String = optBEditText?.text.toString()
        val optC: String = optCEditText?.text.toString()
        val optD: String = optDEditText?.text.toString()

        if (questions.isEmpty() || optA.isEmpty() || optB.isEmpty() ||
            optC.isEmpty() || optD.isEmpty() || questionAnswer!!.isEmpty()
        ) {
            toast(getString(R.string.blank_field_error))
            result = false
        }

        return result
    }
}