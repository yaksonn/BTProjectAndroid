package com.yakson.btprojectandroid.ui.exam

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.helper.DBQuestionsHelper
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.ui.adapter.AdapterExamQuestions
import com.yakson.btprojectandroid.utility.toast
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CreateExamActivity : AppCompatActivity() {

    private var createExamRecyclerView: RecyclerView? = null
    private var emptyStateTextView: TextView? = null
    private var adapterExamQuestions: AdapterExamQuestions? = null
    private var questionArrayList: ArrayList<QuestionsModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_exam)
        initView()
    }

    private fun initView() {
        createExamRecyclerView = findViewById(R.id.createExamRecyclerView)
        emptyStateTextView = findViewById(R.id.emptyStateTextView)
        val dataBase = DBQuestionsHelper(this@CreateExamActivity)
        questionArrayList = dataBase.getQuestions()
        fillRecycler(questionArrayList)
        if (questionArrayList?.size == 0) {
            emptyStateTextView?.visibility = View.VISIBLE
            createExamRecyclerView?.visibility = View.GONE
        } else {
            emptyStateTextView?.visibility = View.GONE
            createExamRecyclerView?.visibility = View.VISIBLE
        }
    }

    fun createExamActivityButtonClicks(view: View?) {
        when (view?.id) {
            R.id.backButtonImageView -> {
                onBackPressed()
                finish()
            }
            R.id.createExamButton -> {

            }
        }
    }

    private fun fillRecycler(questionArray: ArrayList<QuestionsModel>?) {
        adapterExamQuestions =
            questionArray?.let { AdapterExamQuestions(it, this@CreateExamActivity, true) }
        createExamRecyclerView?.adapter = adapterExamQuestions
        adapterExamQuestions?.notifyDataSetChanged()
    }

}
