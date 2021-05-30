package com.yakson.btprojectandroid.ui.questions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.helper.DBQuestionsHelper
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.ui.MainActivity
import com.yakson.btprojectandroid.utility.toEditable
import com.yakson.btprojectandroid.utility.toast
import com.yakson.btprojectandroid.utility.userID

class EditQuestionActivity : AppCompatActivity() {

    private var questionId: Int? = null
    private var questionModel: QuestionsModel? = null

    private var entryQuestionEditText: EditText? = null
    private var optAEditText: EditText? = null
    private var optBEditText: EditText? = null
    private var optCEditText: EditText? = null
    private var optDEditText: EditText? = null
    private var createAndEditQuestionTitleTextView: TextView? = null
    private var saveQuestionButton: Button? = null
    private var optionsRadioButtons: RadioGroup? = null
    private var questionAnswer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_and_edit_question)
        initView()
    }

    private fun initView() {
        questionId = intent.getIntExtra("questionId", 0)
        entryQuestionEditText = findViewById(R.id.entryQuestionEditText)
        createAndEditQuestionTitleTextView = findViewById(R.id.createAndEditQuestionTitleTextView)
        optAEditText = findViewById(R.id.optAEditText)
        optBEditText = findViewById(R.id.optBEditText)
        optCEditText = findViewById(R.id.optCEditText)
        optDEditText = findViewById(R.id.optDEditText)
        saveQuestionButton = findViewById(R.id.saveQuestionButton)
        optionsRadioButtons = findViewById(R.id.optionsRadioButtons)

        saveQuestionButton?.text = getString(R.string.edit_question)
        createAndEditQuestionTitleTextView?.text = getString(R.string.edit)
        optionsRadioButtons?.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId) as RadioButton
            questionAnswer = radioButton.text.toString()
        }
        val dbQuestions = DBQuestionsHelper(this@EditQuestionActivity)
        questionModel = dbQuestions.getQuestionWithId(questionId!!)

        entryQuestionEditText?.text = questionModel?.question?.toEditable()
        optAEditText?.text = questionModel?.optA?.toEditable()
        optBEditText?.text = questionModel?.optB?.toEditable()
        optCEditText?.text = questionModel?.optC?.toEditable()
        optDEditText?.text = questionModel?.optD?.toEditable()
        questionAnswer = questionModel?.answer

    }

    fun addQuestionActivityButtonClicks(view: View?) {
        when (view?.id) {
            R.id.saveQuestionButton -> {
                if (checkQuestionAndOptions()) {
                    val dbQuestions = DBQuestionsHelper(this@EditQuestionActivity)
                    val question = QuestionsModel(
                        -1, userID, entryQuestionEditText?.text.toString().trim(),
                        optAEditText?.text.toString().trim(),
                        optBEditText?.text.toString().trim(),
                        optCEditText?.text.toString().trim(),
                        optDEditText?.text.toString().trim(),
                        questionAnswer
                    )
                    questionId?.let {
                        questionModel?.let { it1 ->
                            dbQuestions.editQuestion(
                                it,
                                it1
                            )
                        }
                    }
                    val intent = Intent(this@EditQuestionActivity, MainActivity::class.java)
                    startActivity(intent)
                    toast("Soru başarılı bir şekilde düzeltildi.")
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