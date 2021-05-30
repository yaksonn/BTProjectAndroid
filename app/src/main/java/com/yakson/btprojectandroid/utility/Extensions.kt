package com.yakson.btprojectandroid.utility

import android.content.Context
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun Context.showAlertDialog(cancelable: Boolean = false, cancelableTouchOutside: Boolean = false, builderFunction: AlertDialog.Builder.() -> Any) {
    val builder = AlertDialog.Builder(this)
    builder.builderFunction()
    val dialog = builder.create()
    dialog.setCancelable(cancelable)
    dialog.setCanceledOnTouchOutside(cancelableTouchOutside)
    dialog.show()
}

fun AlertDialog.Builder.positiveButton(text: String = "OK", handleClick: (i: Int) -> Unit = {}) {
    this.setPositiveButton(text) { _, i -> handleClick(i) }
}

fun AlertDialog.Builder.negativeButton(text: String = "CANCEL", handleClick: (i: Int) -> Unit = {}) {
    this.setNegativeButton(text) { _, i -> handleClick(i) }
}