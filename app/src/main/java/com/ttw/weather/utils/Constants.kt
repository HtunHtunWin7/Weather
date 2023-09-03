package com.ttw.weather.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Constants {
    const val API_KEY = "682852b995364b24aad171628233108"
    const val BASE_URL = "https://api.weatherapi.com/v1/"
    const val CURRENT_LOCATION = "current_location"
}

@SuppressLint("NewApi")
fun getDate(): String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return  LocalDateTime.now().format(formatter)
}

fun setSharePreference(context: Context,key: String, value: String){
    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()
    editor.putString(key,value)
    editor.commit()
}
fun getSharePreference(context: Context,key: String): String{
    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    return sharedPreference.getString(key,"Yangon").toString()
}

fun showAlertDialog(context: Context, title: String, message: String) {
    val alertDialog = AlertDialog.Builder(context)
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setPositiveButton("OK") { _, _ -> }
    alertDialog.show()
}

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun snkBar(view: View, message: String) {
    //Snackbar(view)
    val snackBar = Snackbar.make(
        view, message,
        Snackbar.LENGTH_LONG
    ).setAction("Action", null)
    snackBar.setActionTextColor(Color.BLUE)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.LTGRAY)
    val textView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(Color.RED)
    snackBar.duration = 6000
    snackBar.show()
}

