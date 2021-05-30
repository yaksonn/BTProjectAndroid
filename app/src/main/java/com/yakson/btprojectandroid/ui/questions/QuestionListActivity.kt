package com.yakson.btprojectandroid.ui.questions

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.helper.DBQuestions
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.ui.adapter.AdapterExamQuestions

class QuestionListActivity : AppCompatActivity() {

    private var questionRecyclerView: RecyclerView? = null
    private var adapterExamQuestions: AdapterExamQuestions? = null
    private var questionArrayList: ArrayList<QuestionsModel>? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun initView() {
        questionRecyclerView = findViewById(R.id.questionRecyclerView)
        val db = DBQuestions(this@QuestionListActivity)
        questionArrayList = db.getQuestions()
        fillRecycler(questionArrayList)
    }

    private fun fillRecycler(questionArray: ArrayList<QuestionsModel>?) {
        adapterExamQuestions = questionArray?.let { AdapterExamQuestions(it, this@QuestionListActivity) }
        questionRecyclerView?.adapter = adapterExamQuestions
        adapterExamQuestions?.notifyDataSetChanged()
    }
}