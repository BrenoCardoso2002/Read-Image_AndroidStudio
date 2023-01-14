package com.example.readimageinfirebasestorage

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.readimageinfirebasestorage.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSearchImage.setOnClickListener {


            // progress bar:
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Buscando imagem...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val imageName = binding.txtImageName.text.toString()
            val storage = FirebaseStorage.getInstance().reference.child("images/$imageName")

            val localFile = File.createTempFile("tempImage", "jpg")
            storage.getFile(localFile).addOnSuccessListener {
                if(progressDialog.isShowing)
                    progressDialog.dismiss()

                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.imgImageSelected.setImageBitmap(bitmap)
            }.addOnFailureListener{
                if(progressDialog.isShowing)
                    progressDialog.dismiss()
                binding.imgImageSelected.setImageResource(android.R.color.transparent)
                Toast.makeText(this, "Erro ao obter imagem!!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}