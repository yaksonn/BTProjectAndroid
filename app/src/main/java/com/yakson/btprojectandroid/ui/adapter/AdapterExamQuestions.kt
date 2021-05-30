package com.yakson.btprojectandroid.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.helper.DBQuestions
import com.yakson.btprojectandroid.model.QuestionsModel
import com.yakson.btprojectandroid.ui.questions.EditQuestionActivity
import com.yakson.btprojectandroid.utility.negativeButton
import com.yakson.btprojectandroid.utility.positiveButton
import com.yakson.btprojectandroid.utility.showAlertDialog
import java.util.*

class AdapterExamQuestions constructor(
    private val questionsArrayList: ArrayList<QuestionsModel>,
    private val context: Context
) : RecyclerView.Adapter<AdapterExamQuestions.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_question_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questionsArrayList.size
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: AdapterExamQuestions.ViewHolder, position: Int) {
        val item = questionsArrayList[position]
        item.let { questionItem ->
            holder.question?.text = questionItem.question
            holder.optA?.text = questionItem.optA
            holder.optB?.text = questionItem.optB
            holder.optC?.text = questionItem.optC
            holder.optD?.text = questionItem.optD
            holder.answer?.text = questionItem.answer

            holder.deleteQuestion?.setOnClickListener {
                context.showAlertDialog {
                    setMessage(context.getString(R.string.dialog_delete_message))
                    positiveButton(text = context.getString(R.string.delete)) {
                        val dbQuestions = DBQuestions(context)
                        questionsArrayList.removeAt(position)
                        notifyItemRemoved(position)
                        dbQuestions.deleteQuestion(item.questionId)
                    }
                    negativeButton(text = context.getString(R.string.cancel))
                }
            }

            holder.editQuestion?.setOnClickListener {
                val intent = Intent(context, EditQuestionActivity::class.java)
                intent.putExtra("questionId", item.questionId)
                context.startActivity(intent)
            }
        }

    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var question: TextView? = null
        var optA: TextView? = null
        var optB: TextView? = null
        var optC: TextView? = null
        var optD: TextView? = null
        var answer: TextView? = null
        var editQuestion: ImageView? = null
        var deleteQuestion: ImageView? = null

        init {
            question = itemView.findViewById(R.id.questionTextView)
            optA = itemView.findViewById(R.id.optATextView)
            optB = itemView.findViewById(R.id.optBTextView)
            optC = itemView.findViewById(R.id.optCTextView)
            optD = itemView.findViewById(R.id.optDTextView)
            answer = itemView.findViewById(R.id.answerText)
            editQuestion = itemView.findViewById(R.id.editQuestionImageView)
            deleteQuestion = itemView.findViewById(R.id.deleteQuestionsImageView)
        }
    }
}