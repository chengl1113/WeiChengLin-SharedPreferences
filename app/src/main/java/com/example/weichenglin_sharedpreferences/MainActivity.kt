package com.example.weichenglin_sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlin.properties.Delegates
import kotlin.random.Random

const val TAG = "MAIN ACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private var index by Delegates.notNull<Int>()
    private val images = arrayOf(R.drawable.pika_1, R.drawable.pika_2, R.drawable.pika_3, R.drawable.pika_4, R.drawable.pika_5,)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            changeImage()
        }
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        Log.d(TAG, "loading text")
        val defaultText = R.string.saved_text_key.toString()
        editText.text = Editable.Factory.getInstance().newEditable(sharedPreferences.getString(getString(R.string.saved_text_key), defaultText))

        Log.d(TAG, "loading image ${sharedPreferences.getInt("saved_image_key", 4)}")
        val savedImage = images[sharedPreferences.getInt("saved_image_key", 4)]
        imageView.setImageDrawable(resources.getDrawable(savedImage))
    }

    override fun onDestroy() {
        Log.d(TAG, "preferences saved....")
        with (sharedPreferences.edit()) {
            putString(getString(R.string.saved_text_key), editText.text.toString())
            apply()
        }


        super.onDestroy()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeImage() {
        index = Random.nextInt(images.size)
        val chosenImage: Drawable = resources.getDrawable(images[index], null)
        imageView.setImageDrawable(chosenImage)

        with (sharedPreferences.edit()) {
            putInt(getString(R.string.saved_image_key), index)
            apply()
        }
    }
}