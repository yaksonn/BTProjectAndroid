package com.yakson.btprojectandroid.ui.exam

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yakson.btprojectandroid.R
import com.yakson.btprojectandroid.utility.toEditable
import com.yakson.btprojectandroid.utility.toast

class ExamSettings : AppCompatActivity() {

    private var examTime: EditText? = null
    private var examQuestionPoints: EditText? = null
    private var selectDifficultlySeekBar: SeekBar? = null
    private var difficultlyValueTextView: TextView? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_settings)
        initView()
    }

    private fun initView() {
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        examTime = findViewById(R.id.examTime)
        examQuestionPoints = findViewById(R.id.examQuestionPoints)
        selectDifficultlySeekBar = findViewById(R.id.selectDifficultlySeekBar)
        difficultlyValueTextView = findViewById(R.id.difficultlyValueTextView)

        examTime?.text = sharedPreferences?.getString("examTime", "40")?.toEditable()
        examQuestionPoints?.text =
            sharedPreferences?.getString("examQuestionPoints", "10")?.toEditable()
        difficultlyValueTextView?.text =
            sharedPreferences?.getInt("difficultlyValueTextView", 1).toString().toEditable()

        selectDifficultlySeekBar?.progress = difficultlyValueTextView?.text.toString().toInt()
        selectDifficultlySeekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progressChangedValue = 0
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress
                difficultlyValueTextView?.text = progressChangedValue.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun examSettingsActivityButtonClicks(view: View?) {
        when (view?.id) {
            R.id.backButtonImageView -> {
                onBackPressed()
                finish()
            }
            R.id.saveExamSettingsButton -> {
                saveSettingsToPreferences()
                onBackPressed()
                finish()
            }
        }
    }

    private fun saveSettingsToPreferences() {
        val editor = sharedPreferences?.edit()
        editor?.putString("examTime", examTime?.text.toString())
        editor?.putString("examQuestionPoints", examQuestionPoints?.text.toString())
        editor?.putInt(
            "difficultlyValueTextView",
            Integer.valueOf(difficultlyValueTextView?.text.toString())
        )
        editor?.apply()
        toast("Sınav ayarı başarılı şekilde kaydedildi.")
    }
}