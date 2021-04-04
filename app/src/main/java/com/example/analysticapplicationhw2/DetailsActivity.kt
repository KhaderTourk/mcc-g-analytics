package com.example.analysticapplicationhw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class DetailsActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        firstTime = (System.currentTimeMillis())/1000
    }

    var firstTime:Long ?= null

    lateinit var img: CircleImageView
    lateinit var tv_name: TextView
    var control:Control ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.hide()

        control = Control(this)
        img = findViewById(R.id.img_p_details)
        tv_name = findViewById(R.id.tv_p_name)


        val image = intent.getIntExtra("P_Image",R.drawable.ic_launcher_background)
        val name = intent.getStringExtra("P_Name")
        if (name != null){
            img.setImageResource(image)
            tv_name.setText(name)
        }
    }

    override fun onPause() {
        super.onPause()
        control!!.allWork("btn_food", "food_button", ProductsActivity(), firstTime!!)
    }
}