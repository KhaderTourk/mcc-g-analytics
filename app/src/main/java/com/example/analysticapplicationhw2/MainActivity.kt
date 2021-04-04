package com.example.analysticapplicationhw2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        firstTime = (System.currentTimeMillis())/1000
    }

    var firstTime:Long ?= null
    lateinit var db: FirebaseFirestore

    lateinit var btn_food: Button
    lateinit var btn_clothes: Button
    lateinit var btn_electronic: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()

        btn_food = findViewById(R.id.btn_food)
        btn_clothes = findViewById(R.id.btn_clothes)
        btn_electronic = findViewById(R.id.btn_electronic)

        var intent:Intent
        val control = Control(this)
        btn_food.setOnClickListener {
            Control.category = "food"
            intent = control.allWork("btn_food", "food_button", ProductsActivity(), firstTime!!)
            control.getProductsFromDB("food")
            val handler = Handler()
            handler.postDelayed(Runnable { // Do something after 5s = 5000ms
                startActivity(intent)
            }, 5000)

        }
        btn_clothes.setOnClickListener {
            Control.category = "clothes"
            intent = control.allWork(
                "btn_clothes",
                "clothes_button",
                ProductsActivity(),
                firstTime!!
            )
            control.getProductsFromDB(Control.category!!)
            val handler = Handler()
            handler.postDelayed(Runnable { // Do something after 5s = 5000ms
                startActivity(intent)
            }, 5000)
        }
        btn_electronic.setOnClickListener {
            Control.category = "electronic"
            intent = control.allWork(
                "btn_electronic",
                "electronic_button",
                ProductsActivity(),
                firstTime!!
            )
            control.getProductsFromDB(Control.category!!)
            val handler = Handler()
            handler.postDelayed(Runnable { // Do something after 5s = 5000ms
                startActivity(intent)
            }, 5000)
        }

    }

}