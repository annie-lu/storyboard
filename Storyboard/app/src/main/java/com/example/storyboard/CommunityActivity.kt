package com.example.storyboard

import android.annotation.SuppressLint
import android.app.ActionBar
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout



class CommunityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        createLayout()
    }

    @SuppressLint("ResourceType")
    fun createLayout() {
        val layout = findViewById(R.id.linearLayout) as LinearLayout

        //set the properties for button
        for (x in 0..10) {
            val btnTag = Button(this)

            btnTag.setLayoutParams(ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            ))

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10,10,10,10)
            btnTag.layoutParams = params


            btnTag.setText("Button")
            btnTag.id = x
            btnTag.setBackgroundResource(R.drawable.dashboardbutton)

            //add button to the layout
            layout.addView(btnTag)
        }
    }
}
