package com.example.homework3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.ActivitySchoolInfoBinding
import com.example.homework3.model.School
import com.example.homework3.model.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SchoolInfoActivity : AppCompatActivity() {
    private val rootNode = FirebaseDatabase.getInstance().reference.child("school")
    private val adapter = StudentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_info)
        val studentsRecyclerView = findViewById<RecyclerView>(R.id.studentsRecyclerView)
        studentsRecyclerView.adapter = adapter
        observeSchool()
        setClickListeners()
    }

    private fun observeSchool() {
        rootNode.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").getValue(String::class.java) ?: ""
                val students = snapshot.child("students").children
                    .map { it.getValue(Student::class.java)!! }.toList()

                setUpSchool(School(name, students))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SchoolInfoActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setClickListeners() {
        val addStudentButton = findViewById<Button>(R.id.addStudentButton)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpSchool(school: School) {
        val schoolNameTextView = findViewById<TextView>(R.id.schoolNameTextView)
        schoolNameTextView.text = school.name
        adapter.submitStudentsList(school.students)
    }

}
