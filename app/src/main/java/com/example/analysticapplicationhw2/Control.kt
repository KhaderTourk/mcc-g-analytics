package com.example.analysticapplicationhw2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException

class Control(private val context: Context ) {
    lateinit var progressDialog: ProgressDialog

     fun allWork( itemId : String , itemName : String, nextPage: Context  , firstTime:Long): Intent {
        val time = ((System.currentTimeMillis())/1000) - firstTime
        clickEvent(itemId,itemName)
         screenTrack(context.toString(),context.toString())
        Toast.makeText(context, "Success $time ", Toast.LENGTH_LONG).show()
        val intent = Intent(context , nextPage::class.java)
        return intent
    }
    private fun clickEvent(id: String, name: String ): Bundle {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button")
        return bundle
    }
    private fun screenTrack(screenName:String,screenClass:String): Bundle {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
        return bundle
    }

     fun getImageFromFB(imageName:String): Bitmap? {
        val storage = FirebaseStorage.getInstance().getReference().child("HW(2)/$imageName")
        var bitmap:Bitmap ?= null
            val localFile = File.createTempFile(imageName,"jpg")
            Log.e("TAG localFile", "$localFile ")
            storage.getFile(localFile).addOnSuccessListener {
                Log.e("TAG absolutePath", "${localFile.absolutePath} ")
                bitmap = BitmapFactory.decodeFile(localFile.absolutePath)

            }.addOnFailureListener{
                Log.e("TAG absolutePath", "localFile.absolutePath ")
                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
            }
       return bitmap
    }

     fun getProductsFromDB(collectionName :String ){
         progressDialog = ProgressDialog(context)
         progressDialog.setMessage("loading....")
         progressDialog.setCancelable(false)
         progressDialog.show()
         val db:FirebaseFirestore = FirebaseFirestore.getInstance()
         Toast.makeText(context, "fail ", Toast.LENGTH_LONG).show()
        db.collection(collectionName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                Toast.makeText(context, "fail $querySnapshot", Toast.LENGTH_SHORT).show()
                Log.e("TAG : querySnapshot", "$querySnapshot ")
                for (document in querySnapshot) {
                    Log.e("TAG : document", "$document ")
                    val name = document.getString("name").toString()
                    val imageName = document.getString("imgName").toString()
                    val imgBitmap = getImageFromFB(imageName)
                    val handler = Handler()
                    handler.postDelayed(Runnable { // Do something after 5s = 5000ms
                      Log.e("TAG : imgBitmap", "$imgBitmap ")
                    }, 5000)

                    productList!!.add(
                        Product(
                            name,imgBitmap!!
                        )
                    )
                    Log.e("TAG", "${document.getString("name").toString()} ")
                    Log.e("TAG", "${document.id} => ${document.getString("name")}")
                }
                progressDialog.dismiss()
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", exception.message.toString())
            }
    }

    companion object {
        var category : String ?= null
        var productList:ArrayList<Product> ?=null
    }
}