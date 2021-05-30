package com.yakson.btprojectandroid.ui.questions

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.helper.DBQuestionsHelper
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.ui.adapter.AdapterExamQuestions

class QuestionListActivity : AppCompatActivity() {

    private var questionRecyclerView: RecyclerView? = null
    private var emptyStateTextView: TextView? = null
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
        emptyStateTextView = findViewById(R.id.emptyStateTextView)
        val db = DBQuestionsHelper(this@QuestionListActivity)
        questionArrayList = db.getQuestions()
        fillRecycler(questionArrayList)
        if (questionArrayList?.size == 0) {
            emptyStateTextView?.visibility = View.VISIBLE
            questionRecyclerView?.visibility = View.GONE
        } else {
            emptyStateTextView?.visibility = View.GONE
            questionRecyclerView?.visibility = View.VISIBLE
        }
    }

    private fun fillRecycler(questionArray: ArrayList<QuestionsModel>?) {
        adapterExamQuestions =
            questionArray?.let { AdapterExamQuestions(it, this@QuestionListActivity, false) }
        questionRecyclerView?.adapter = adapterExamQuestions
        adapterExamQuestions?.notifyDataSetChanged()
    }
}