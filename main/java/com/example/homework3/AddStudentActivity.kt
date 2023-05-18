package com.example.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.homework3.databinding.ActivityAddStudentBinding
import com.example.homework3.model.Student
import com.google.firebase.database.FirebaseDatabase

class AddStudentActivity : AppCompatActivity() {
    private val rootNode =
        FirebaseDatabase.getInstance().reference.child("school").child("students")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        setClickListeners()
    }

    private fun setClickListeners() {
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val surnameEditText = findViewById<EditText>(R.id.surnameEditText)
        val privateNumberEditText = findViewById<EditText>(R.id.privateNumberEditText)
        val profilePictureEditText = findViewById<EditText>(R.id.profilePictureEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val addButton = findViewById<Button>(R.id.addButton)

        addButton.setOnClickListener {

                if (nameEditText.text?.isNotEmpty() == true
                    && surnameEditText.text?.isNotEmpty() == true
                    && privateNumberEditText.text?.isNotEmpty() == true
                    && profilePictureEditText.text?.isNotEmpty() == true
                    && emailEditText.text?.isNotEmpty() == true
                ) {
                    if (!emailEditText.text!!.contains("@")) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Invalid email",
                            Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }

                    if (privateNumberEditText.text!!.length != 13) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Invalid personal number",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }

                    val student = Student(
                        nameEditText.text.toString(),
                        surnameEditText.text.toString(),
                        privateNumberEditText.text.toString(),
                        profilePictureEditText.text.toString(),
                        emailEditText.text.toString()
                    )
                    rootNode.child(student.personalNumber).setValue(student)
                    finish()
                } else {
                    Toast.makeText(this@AddStudentActivity,
                        "Fill all fields",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }
}
