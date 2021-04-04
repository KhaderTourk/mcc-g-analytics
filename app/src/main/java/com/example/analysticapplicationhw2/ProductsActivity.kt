package com.example.analysticapplicationhw2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProductsActivity : AppCompatActivity(),ProductAdapter.onClick {
    override fun onResume() {
        super.onResume()
        firstTime = (System.currentTimeMillis())/1000
    }

    var firstTime:Long ?= null
    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog
    lateinit var adapter:ProductAdapter
    lateinit var recycle: RecyclerView

    override fun onClickItem(position: Int) {
        val intent = control!!.allWork("cardView",
            Control.productList!![position].name, DetailsActivity(), firstTime!!)
        intent.putExtra("P_Name",Control.productList!![position].name)
        intent.putExtra("P_Image",Control.productList!![position].img)
        startActivity(intent)
    }
    var control:Control ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        supportActionBar?.hide()
        control = Control(this)

        db = FirebaseFirestore.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("loading....")
        progressDialog.setCancelable(false)
        progressDialog.show()
      //  getProductsFromDB(Control.category!!)

       recycle = findViewById(R.id.recycle)

        adapter = ProductAdapter(this,Control.productList!!,this)
        recycle.adapter = adapter
        recycle.layoutManager = GridLayoutManager(this, 2)

    }
    private fun getProductsFromDB(collectionName :String){

        db.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val name = document.getString("name").toString()
                    val imageName = document.getString("imgName").toString()
                    val imgBitmap = control!!.getImageFromFB(imageName)
                    Control.productList!!.add(
                        Product(
                            name,imgBitmap!!
                        )
                    )
                    Log.e("TAG", "${document.getString("name").toString()} ")
                    Log.e("TAG", "${document.id} => ${document.getString("name")}")
                }
                progressDialog.dismiss()
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", exception.message.toString())
            }
    }
}